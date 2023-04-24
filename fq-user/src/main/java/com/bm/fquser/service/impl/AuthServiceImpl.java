package com.bm.fquser.service.impl;

import cn.hutool.core.util.IdUtil;
import com.bm.fqcore.cache.ITokenService;
import com.bm.fqcore.cache.RedisUtil;
import com.bm.fqcore.constants.CS;
import com.bm.fqcore.exception.BizException;
import com.bm.fqcore.exception.JeepayAuthenticationException;
import com.bm.fqcore.jwt.AppJWTPayload;
import com.bm.fqcore.jwt.JWTPayload;
import com.bm.fqcore.jwt.JWTUtils;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqservice.model.BUser;
import com.bm.fquser.config.SystemYmlConfig;
import com.bm.fquser.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 认证Service
 *
 * @modify zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private SystemYmlConfig systemYmlConfig;

    @Override
    public String auth(String code, String phone) {

        String cacheCode = RedisUtil.getString(CS.getCacheKeysSmsCode(phone));
        if (StringUtils.isEmpty(cacheCode) || !cacheCode.equalsIgnoreCase(code)) {
            throw new BizException("短信验证码有误！");
        }
        // 删除短信验证码缓存数据
        RedisUtil.del(CS.getCacheKeysSmsCode(phone));
        //1. 生成spring-security usernamePassword类型对象
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(phone, "jeepay666");
        //spring-security 自动认证过程；
        // 1. 进入 JeeUserDetailsServiceImpl.loadUserByUsername 获取用户基本信息；
        //2. SS根据UserDetails接口验证是否用户可用；
        //3. 最后返回loadUserByUsername 封装的对象信息；
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(upToken);
        } catch (JeepayAuthenticationException jex) {
            throw jex.getBizException() == null ? new BizException(jex.getMessage()) : jex.getBizException();
//        } catch (BadCredentialsException e) {
//            throw new BizException("用户不存在！");
        } catch (AuthenticationException e) {
            log.error("AuthenticationException:", e);
            throw new BizException("认证服务出现异常， 请重试或联系系统管理员！");
        }
        AppUserDetails jeeUserDetails = (AppUserDetails) authentication.getPrincipal();

        //查询用户信息
        //验证通过后 再查询用户角色和权限信息集合
        BUser bUser = jeeUserDetails.getBUser();
        //生成token
        String cacheKey = CS.getCacheKeyToken(bUser.getUserId() + "", IdUtil.fastUUID());
        //生成iToken 并放置到缓存
        ITokenService.appProcessTokenCache(jeeUserDetails, cacheKey); //处理token 缓存信息
        //将信息放置到Spring-security context中
        UsernamePasswordAuthenticationToken authenticationRest = new UsernamePasswordAuthenticationToken(jeeUserDetails, null, jeeUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationRest);

        //返回JWTToken
        return JWTUtils.generateAppToken(new AppJWTPayload(jeeUserDetails), systemYmlConfig.getJwtSecret());
    }
}
