package com.bm.fqmerchant.service;

import com.bm.fqservice.model.BSysUser;

import java.util.List;

public interface AuthService {
    boolean validateCurrentUserPwd(String currentUserPwd);

    void resetAuthInfo(Long resetUserId, String authLoginUserName, String telphone, String newPwd, String sysType);

    String auth(String account, String ipassport);

    void resetRela(String roleId, List<String> entIdList);

    void refAuthentication(List<Long> sysUserIdList);

    void removeRole(String recordId);

    void addSysUser(BSysUser sysUser, String mch);

    void delAuthentication(List<Long> asList);

    void updateSysUser(BSysUser sysUser);

    void removeUser(BSysUser sysUser, String mch);

    void saveUserRole(Long sysUserId, List<String> roleIdList);
}
