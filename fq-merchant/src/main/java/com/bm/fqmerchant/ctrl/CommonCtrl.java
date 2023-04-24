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
package com.bm.fqmerchant.ctrl;

import com.bm.fqcore.constants.ApiCodeEnum;
import com.bm.fqcore.constants.CS;
import com.bm.fqcore.ctrls.AbstractCtrl;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fqmerchant.config.SystemYmlConfig;
import com.bm.fqservice.model.BSysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 通用ctrl类
 *
 * @author terrfly
 * @modify zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
public abstract class CommonCtrl extends AbstractCtrl {

    @Autowired
    protected SystemYmlConfig mainConfig;


    /** 获取当前用户ID */
    protected JeeUserDetails getCurrentUser(){

        return (JeeUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /** 获取当前商户ID **/
    protected String getCurrentMchNo() {
        return getCurrentUser().getSysUser().getBelongInfoId();
    }

    /**
     * 获取当前用户登录IP
     * @return
     */
    protected String getIp() {
        return getClientIp();
    }

    /**
     * 校验当前用户是否为超管
     * @return
     */
    protected ApiRes checkIsAdmin() {
        BSysUser sysUser = getCurrentUser().getSysUser();
        if (sysUser.getIsAdmin() != CS.YES) {
            return ApiRes.fail(ApiCodeEnum.SYS_PERMISSION_ERROR);
        }else {
            return null;
        }

    }

}
