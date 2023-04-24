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
package com.bm.fquser.secruity;

import com.bm.fqcore.constants.CS;
import com.bm.fqcore.exception.JeepayAuthenticationException;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqservice.service.IBUserService;
import com.bm.fqservice.model.BUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/*
 * 实现UserDetailsService 接口
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/6/8 17:13
 */
@Service
public class JeeUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    IBUserService ibUserService;

    /**
     * 此函数为： authenticationManager.authenticate(upToken) 内部调用 ;
     **/
    @Override
    public UserDetails loadUserByUsername(String loginUsernameStr) throws UsernameNotFoundException {
        //用户ID
        BUser bUser = ibUserService.getOne(BUser.gw().eq(BUser::getUserMobile, loginUsernameStr));
        if (bUser == null) {
            bUser = new BUser();
            bUser.setLoginPassword("$2a$10$9.BTInzdUHQiHsuaZTey0.puYNkhxCpdGCnK/dnGm615eV/JNsNVS");
            bUser.setUserMobile(loginUsernameStr);
            bUser.setUserRegtime(new Date());
            bUser.setState(CS.PUB_USABLE);
            ibUserService.save(bUser);
        }
        if (CS.PUB_USABLE != bUser.getState()) { //状态不合法
            throw JeepayAuthenticationException.build("用户状态不可登录，请联系管理员！");
        }
        return new AppUserDetails(bUser, bUser.getLoginPassword());
    }
}
