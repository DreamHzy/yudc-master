/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bm.fqmerchant.ctrl.sysuser;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bm.fqcore.aop.MethodLog;
import com.bm.fqcore.constants.ApiCodeEnum;
import com.bm.fqcore.constants.CS;
import com.bm.fqcore.exception.BizException;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqmerchant.ctrl.CommonCtrl;
import com.bm.fqmerchant.service.AuthService;
import com.bm.fqservice.model.BSysUser;
import com.bm.fqservice.service.IBSysUserAuthService;
import com.bm.fqservice.service.IBSysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 用户管理类
 *
 * @author terrfly
 * @modify zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@RestController
@RequestMapping("api/sysUsers")
public class SysUserController extends CommonCtrl {

	@Autowired
    IBSysUserService sysUserService;
	@Autowired
    IBSysUserAuthService sysUserAuthService;
	@Autowired private AuthService authService;


	/** list */
	@PreAuthorize("hasAuthority( 'ENT_UR_USER_LIST' )")
	@RequestMapping(value="", method = RequestMethod.GET)
	public ApiRes list() {

		BSysUser queryObject = getObject(BSysUser.class);

		LambdaQueryWrapper<BSysUser> condition = BSysUser.gw();
		condition.eq(BSysUser::getSysType, CS.SYS_TYPE.MCH);
		condition.eq(BSysUser::getBelongInfoId, getCurrentUser().getSysUser().getBelongInfoId());

		if(StringUtils.isNotEmpty(queryObject.getRealname())){
			condition.like(BSysUser::getRealname, queryObject.getRealname());
		}

		if(queryObject.getSysUserId() != null){
			condition.eq(BSysUser::getSysUserId, queryObject.getSysUserId());
		}

		IPage<BSysUser> pages = sysUserService.page(getIPage(), condition);

		return ApiRes.page(pages);
	}


	/** detail */
	@PreAuthorize("hasAuthority( 'ENT_UR_USER_EDIT' )")
	@RequestMapping(value="/{recordId}", method = RequestMethod.GET)
	public ApiRes detail(@PathVariable("recordId") Integer recordId) {

        BSysUser sysUser = sysUserService.getById(recordId);
		if (sysUser == null) {
            throw new BizException(ApiCodeEnum.SYS_OPERATION_FAIL_SELETE);
        }
		if (!sysUser.getBelongInfoId().equals(getCurrentUser().getSysUser().getBelongInfoId())) {
			throw new BizException(ApiCodeEnum.SYS_PERMISSION_ERROR);
		}
		return ApiRes.ok(sysUser);
	}

	/** add */
	@PreAuthorize("hasAuthority( 'ENT_UR_USER_ADD' )")
	@RequestMapping(value="", method = RequestMethod.POST)
	@MethodLog(remark = "添加管理员")
	public ApiRes add() {
        BSysUser sysUser = getObject(BSysUser.class);
		sysUser.setBelongInfoId(getCurrentUser().getSysUser().getBelongInfoId());
		sysUser.setIsAdmin(CS.NO);
		authService.addSysUser(sysUser, CS.SYS_TYPE.MCH);
		return ApiRes.ok();
	}


	/** 修改操作员 登录认证信息 */
	@RequestMapping(value="/modifyPwd", method = RequestMethod.PUT)
	public ApiRes authInfo() {

		Long opSysUserId = getValLongRequired("recordId");   //操作员ID

		//更改密码， 验证当前用户信息
		String currentUserPwd = getValStringRequired("originalPwd"); //当前用户登录密码
		//验证当前密码是否正确
		if(!authService.validateCurrentUserPwd(currentUserPwd)){
			throw new BizException("原密码验证失败！");
		}

		String opUserPwd = getValStringRequired("confirmPwd");

		// 验证原密码与新密码是否相同
		if (opUserPwd.equals(currentUserPwd)) {
			throw new BizException("新密码与原密码相同！");
		}
        authService.resetAuthInfo(opSysUserId, null, null, opUserPwd, CS.SYS_TYPE.MCH);
		return ApiRes.ok();
	}


	/** update */
	@PreAuthorize("hasAuthority( 'ENT_UR_USER_EDIT' )")
	@RequestMapping(value="/{recordId}", method = RequestMethod.PUT)
	@MethodLog(remark = "修改操作员信息")
	public ApiRes update(@PathVariable("recordId") Long recordId) {
		BSysUser sysUser = getObject(BSysUser.class);
		sysUser.setSysUserId(recordId);
		// 如果当前用户为非超管则用户状态为普通用户
		if (getCurrentUser().getSysUser().getIsAdmin() != CS.YES) {
            sysUser.setIsAdmin(CS.NO);
        }
        BSysUser dbRecord = sysUserService.getOne(BSysUser.gw().eq(BSysUser::getSysUserId, recordId).eq(BSysUser::getBelongInfoId, getCurrentMchNo()));
		if (dbRecord == null) {
            throw new BizException(ApiCodeEnum.SYS_OPERATION_FAIL_SELETE);
        }

		//判断是否自己禁用自己
		if(recordId.equals(getCurrentUser().getSysUser().getSysUserId()) && sysUser.getState() != null && sysUser.getState() == CS.PUB_DISABLE){
			throw new BizException("系统不允许禁用当前登陆用户！");
		}

		//判断是否重置密码
		Boolean resetPass = getReqParamJSON().getBoolean("resetPass");
		if (resetPass != null && resetPass) {
			//判断是否重置密码
			String updatePwd = getReqParamJSON().getBoolean("defaultPass") == false ? Base64.decodeStr(getValStringRequired("confirmPwd")) : CS.DEFAULT_PWD;
            authService.resetAuthInfo(sysUser.getSysUserId(), null, null, updatePwd, CS.SYS_TYPE.MCH);
			// 删除用户redis缓存信息
			authService.delAuthentication(Arrays.asList(recordId));
		}

		authService.updateSysUser(sysUser);

		//如果用户被禁用，需要更新redis数据
		if(sysUser.getState() != null && sysUser.getState() == CS.PUB_DISABLE){
			authService.refAuthentication(Arrays.asList(recordId));
		}

		return ApiRes.ok();
	}

	/** delete */
	@PreAuthorize("hasAuthority( 'ENT_UR_USER_DELETE' )")
	@RequestMapping(value="/{recordId}", method = RequestMethod.DELETE)
	@MethodLog(remark = "删除操作员信息")
	public ApiRes delete(@PathVariable("recordId") Long recordId) {
		//查询该操作员信息
		BSysUser sysUser = sysUserService.getById(recordId);
		if (sysUser == null) {
			throw new BizException("该操作员不存在！");
		}

		//判断是否自己删除自己
		if(recordId.equals(getCurrentUser().getSysUser().getSysUserId())){
			throw new BizException("系统不允许删除当前登陆用户！");
		}

		//判断是否删除商户默认超管
        BSysUser mchUserDefault = sysUserService.getOne(BSysUser.gw()
				.eq(BSysUser::getBelongInfoId, getCurrentMchNo())
				.eq(BSysUser::getSysType, CS.SYS_TYPE.MCH)
				.eq(BSysUser::getIsAdmin, CS.YES)
		);

		if (mchUserDefault.getSysUserId().equals(recordId)) {
			throw new BizException("系统不允许删除商户默认用户！");
		}

		// 删除用户
		authService.removeUser(sysUser, CS.SYS_TYPE.MCH);

		//如果用户被删除，需要更新redis数据
		authService.refAuthentication(Arrays.asList(recordId));

		return ApiRes.ok();
	}
}
