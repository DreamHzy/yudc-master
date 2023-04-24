package com.bm.fqcore.model.security;

import com.bm.fqservice.model.BUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Data
public class AppUserDetails implements UserDetails {



    /**
     * 系统用户信息
     **/
    private BUser bUser;

    /** 密码 **/
    private String credential;

    /**
     * 缓存标志
     **/
    private String cacheKey;

    /**
     * 登录IP
     **/
    private String loginIp;



    /** 角色+权限 集合   （角色必须以： ROLE_ 开头） **/
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public AppUserDetails() {

    }

    public AppUserDetails(BUser bUser, String credential) {
        this.setCredential(credential);
        this.setBUser(bUser);
        //做一些初始化操作
    }

    /** spring-security 需要验证的密码 **/
    @Override
    public String getPassword() {
        return getCredential();
    }

    /** spring-security 登录名 **/
    @Override
    public String getUsername() {
        return getBUser().getUserId() + "";
    }

    /** 账户是否过期 **/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** 账户是否已解锁 **/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** 密码是否过期 **/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** 账户是否开启 **/
    @Override
    public boolean isEnabled() {
        return true;
    }

    /** 获取权限集合 **/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public static JeeUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        try {
            return (JeeUserDetails) authentication.getPrincipal();
        }catch (Exception e) {
            return null;
        }
    }
}