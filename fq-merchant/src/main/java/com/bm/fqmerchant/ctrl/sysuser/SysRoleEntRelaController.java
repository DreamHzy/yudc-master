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

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bm.fqcore.constants.ApiCodeEnum;
import com.bm.fqcore.exception.BizException;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqmerchant.ctrl.CommonCtrl;
import com.bm.fqmerchant.service.AuthService;
import com.bm.fqservice.model.BSysRole;
import com.bm.fqservice.model.BSysRoleEntRela;
import com.bm.fqservice.model.BSysUserRoleRela;
import com.bm.fqservice.service.IBSysRoleEntRelaService;
import com.bm.fqservice.service.IBSysRoleService;
import com.bm.fqservice.service.IBSysUserRoleRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限管理类
 *
 * @author terrfly
 * @modify zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@RestController
@RequestMapping("api/sysRoleEntRelas")
public class SysRoleEntRelaController extends CommonCtrl {

	@Autowired private IBSysRoleEntRelaService sysRoleEntRelaService;
	@Autowired private IBSysUserRoleRelaService sysUserRoleRelaService;
	@Autowired private IBSysRoleService sysRoleService;
	@Autowired private AuthService authService;

	/** list */
	@PreAuthorize("hasAuthority( 'ENT_UR_ROLE_DIST' )")
	@RequestMapping(value="", method = RequestMethod.GET)
	public ApiRes list() {

		BSysRoleEntRela queryObject = getObject(BSysRoleEntRela.class);

		LambdaQueryWrapper<BSysRoleEntRela> condition = BSysRoleEntRela.gw();

		if(queryObject.getRoleId() != null){
			condition.eq(BSysRoleEntRela::getRoleId, queryObject.getRoleId());
		}

		IPage<BSysRoleEntRela> pages = sysRoleEntRelaService.page(getIPage(true), condition);

		return ApiRes.page(pages);
	}

	/** 重置角色权限关联信息 */
	@PreAuthorize("hasAuthority( 'ENT_UR_ROLE_DIST' )")
	@RequestMapping(value="relas/{roleId}", method = RequestMethod.POST)
	public ApiRes relas(@PathVariable("roleId") String roleId) {

		BSysRole sysRole = sysRoleService.getOne(BSysRole.gw().eq(BSysRole::getRoleId, roleId).eq(BSysRole::getBelongInfoId, getCurrentMchNo()));
		if (sysRole == null) {
            throw new BizException(ApiCodeEnum.SYS_OPERATION_FAIL_SELETE);
        }

		List<String> entIdList = JSONArray.parseArray(getValStringRequired("entIdListStr"), String.class);

		authService.resetRela(roleId, entIdList);

		List<Long> sysUserIdList = new ArrayList<>();
		sysUserRoleRelaService.list(BSysUserRoleRela.gw().eq(BSysUserRoleRela::getRoleId, roleId)).stream().forEach(item -> sysUserIdList.add(item.getUserId()));

		//查询到该角色的人员， 将redis更新
		authService.refAuthentication(sysUserIdList);

		return ApiRes.ok();
	}

}
