package com.bm.fqmerchant.service.impl;

import cn.hutool.core.util.IdUtil;
import com.bm.fqcore.cache.ITokenService;
import com.bm.fqcore.cache.RedisUtil;
import com.bm.fqcore.constants.CS;
import com.bm.fqcore.exception.BizException;
import com.bm.fqcore.exception.JeepayAuthenticationException;
import com.bm.fqcore.jwt.JWTPayload;
import com.bm.fqcore.jwt.JWTUtils;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fqcore.utils.StringKit;
import com.bm.fqmerchant.config.SystemYmlConfig;
import com.bm.fqmerchant.mapper.AuthServiceMapper;
import com.bm.fqmerchant.service.AuthService;
import com.bm.fqservice.model.*;
import com.bm.fqservice.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Resource
    IBSysUserAuthService ibSysUserAuthService;
    @Resource
    IBMchInfoService ibMchInfoService;

    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private SystemYmlConfig systemYmlConfig;

    @Autowired
    private IBSysUserService sysUserService;
    @Autowired
    private IBSysRoleService sysRoleService;
    @Autowired
    private IBSysRoleEntRelaService sysRoleEntRelaService;
    @Autowired
    private AuthServiceMapper authServiceMapper;
    @Autowired
    private IBSysUserRoleRelaService sysUserRoleRelaService;
    @Autowired
    IBSysEntitlementService ibSysEntitlementService;
    @Autowired
    IBSysUserService ibSysUserService;

    @Override
    /** 查询当前用户密码是否正确 */
    public boolean validateCurrentUserPwd(String pwdRaw) {

        //根据当前用户ID + 认证方式为 登录用户名的方式 查询一条记录
        BSysUserAuth auth = ibSysUserAuthService.getOne(BSysUserAuth.gw()
                .eq(BSysUserAuth::getUserId, JeeUserDetails.getCurrentUserDetails().getSysUser().getSysUserId())
                .eq(BSysUserAuth::getIdentityType, CS.AUTH_TYPE.LOGIN_USER_NAME)
        );
        if (auth != null && new BCryptPasswordEncoder().matches(pwdRaw, auth.getCredential())) {
            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public void resetAuthInfo(Long resetUserId, String authLoginUserName, String telphone, String newPwd, String sysType) {
        //更改密码
        if (StringUtils.isNotEmpty(newPwd)) {
            //根据当前用户ID 查询出用户的所有认证记录
            List<BSysUserAuth> authList = ibSysUserAuthService.list(BSysUserAuth.gw().eq(BSysUserAuth::getSysType, sysType).eq(BSysUserAuth::getUserId, resetUserId));
            for (BSysUserAuth auth : authList) {
                if (StringUtils.isEmpty(auth.getSalt())) { //可能为其他登录方式， 不存在salt
                    continue;
                }
                BSysUserAuth updateRecord = new BSysUserAuth();
                updateRecord.setAuthId(auth.getAuthId());
                updateRecord.setCredential(new BCryptPasswordEncoder().encode(newPwd));
                ibSysUserAuthService.updateById(updateRecord);
            }
        }
    }


    /**
     * 认证
     **/
    public String auth(String username, String password) {

        //1. 生成spring-security usernamePassword类型对象
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        //spring-security 自动认证过程；
        // 1. 进入 JeeUserDetailsServiceImpl.loadUserByUsername 获取用户基本信息；
        //2. SS根据UserDetails接口验证是否用户可用；
        //3. 最后返回loadUserByUsername 封装的对象信息；
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(upToken);
        } catch (JeepayAuthenticationException jex) {
            throw jex.getBizException() == null ? new BizException(jex.getMessage()) : jex.getBizException();
        } catch (BadCredentialsException e) {
            throw new BizException("用户名/密码错误！");
        } catch (AuthenticationException e) {
            log.error("AuthenticationException:", e);
            throw new BizException("认证服务出现异常， 请重试或联系系统管理员！");
        }
        JeeUserDetails jeeUserDetails = (JeeUserDetails) authentication.getPrincipal();

        //验证通过后 再查询用户角色和权限信息集合

        BSysUser sysUser = jeeUserDetails.getSysUser();

        //非超级管理员 && 不包含左侧菜单 进行错误提示
        if (sysUser.getIsAdmin() != CS.YES && authServiceMapper.userHasLeftMenu(sysUser.getSysUserId(), CS.SYS_TYPE.MCH) <= 0) {
            throw new BizException("当前用户未分配任何菜单权限，请联系管理员进行分配后再登录！");
        }

        // 查询当前用户的商户信息
        BMchInfo mchInfo = ibMchInfoService.getOne(BMchInfo.gw().eq(BMchInfo::getMchNo, sysUser.getBelongInfoId()));
        if (mchInfo != null) {
            // 判断当前商户状态是否可用
            if (mchInfo.getState() == CS.NO) {
                throw new BizException("当前商户状态不可用！");
            }
        }
        // 放置权限集合
        jeeUserDetails.setAuthorities(getUserAuthority(sysUser));

        //生成token
        String cacheKey = CS.getCacheKeyToken(sysUser.getSysUserId() + "", IdUtil.fastUUID());

        //生成iToken 并放置到缓存
        ITokenService.processTokenCache(jeeUserDetails, cacheKey); //处理token 缓存信息

        //将信息放置到Spring-security context中
        UsernamePasswordAuthenticationToken authenticationRest = new UsernamePasswordAuthenticationToken(jeeUserDetails, null, jeeUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationRest);

        //返回JWTToken
        return JWTUtils.generateToken(new JWTPayload(jeeUserDetails), systemYmlConfig.getJwtSecret());
    }


    public List<SimpleGrantedAuthority> getUserAuthority(BSysUser sysUser) {

        //用户拥有的角色集合  需要以ROLE_ 开头,  用户拥有的权限集合
        List<String> roleList = findListByUser(sysUser.getSysUserId());
        List<String> entList = selectEntIdsByUserId(sysUser.getSysUserId(), sysUser.getIsAdmin(), sysUser.getSysType());

        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();
        roleList.stream().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));
        entList.stream().forEach(ent -> grantedAuthorities.add(new SimpleGrantedAuthority(ent)));
        return grantedAuthorities;
    }


    /**
     * 根据用户查询全部角色集合
     **/
    public List<String> findListByUser(Long sysUserId) {
        List<String> result = new ArrayList<>();
        sysUserRoleRelaService.list(
                BSysUserRoleRela.gw().eq(BSysUserRoleRela::getUserId, sysUserId)
        ).stream().forEach(r -> result.add(r.getRoleId()));

        return result;
    }

    /**
     * 根据人查询出所有权限ID集合
     */
    public List<String> selectEntIdsByUserId(Long userId, Byte isAdmin, String sysType) {
        if (isAdmin == CS.YES) {
            List<String> result = new ArrayList<>();
            ibSysEntitlementService.list(BSysEntitlement.gw().select(BSysEntitlement::getEntId).eq(BSysEntitlement::getSysType, sysType).eq(BSysEntitlement::getState, CS.PUB_USABLE)
            ).stream().forEach(r -> result.add(r.getEntId()));

            return result;

        } else {
            return authServiceMapper.selectEntIdsByUserId(userId, sysType);
        }
    }

    /**
     * 重置 角色 - 权限 关联关系
     **/
    @Transactional
    public void resetRela(String roleId, List<String> entIdList) {

        //1. 删除
        sysRoleEntRelaService.remove(BSysRoleEntRela.gw().eq(BSysRoleEntRela::getRoleId, roleId));

        //2. 插入
        for (String entId : entIdList) {
            BSysRoleEntRela r = new BSysRoleEntRela();
            r.setRoleId(roleId);
            r.setEntId(entId);
            sysRoleEntRelaService.save(r);
        }

    }


    /**
     * 根据用户ID 更新缓存中的权限集合， 使得分配实时生效
     **/
    public void refAuthentication(List<Long> sysUserIdList) {

        if (sysUserIdList == null || sysUserIdList.isEmpty()) {
            return;
        }

        Map<Long, BSysUser> sysUserMap = new HashMap<>();

        // 查询 sysUserId 和 state
        sysUserService.list(
                BSysUser.gw()
                        .select(BSysUser::getSysUserId, BSysUser::getState)
                        .in(BSysUser::getSysUserId, sysUserIdList)
        ).stream().forEach(item -> sysUserMap.put(item.getSysUserId(), item));

        for (Long sysUserId : sysUserIdList) {

            Collection<String> cacheKeyList = RedisUtil.keys(CS.getCacheKeyToken(sysUserId + "", "*"));
            if (cacheKeyList == null || cacheKeyList.isEmpty()) {
                continue;
            }

            for (String cacheKey : cacheKeyList) {

                //用户不存在 || 已禁用 需要删除Redis
                if (sysUserMap.get(sysUserId) == null || sysUserMap.get(sysUserId).getState() == CS.PUB_DISABLE) {
                    RedisUtil.del(cacheKey);
                    continue;
                }

                JeeUserDetails jwtBaseUser = RedisUtil.getObject(cacheKey, JeeUserDetails.class);
                if (jwtBaseUser == null) {
                    continue;
                }

                // 重新放置sysUser对象
                jwtBaseUser.setSysUser(sysUserService.getById(sysUserId));

                //查询放置权限数据
                jwtBaseUser.setAuthorities(getUserAuthority(jwtBaseUser.getSysUser()));

                //保存token  失效时间不变
                RedisUtil.set(cacheKey, jwtBaseUser);
            }
        }
    }


    /**
     * 添加系统用户
     **/
    @Transactional
    public void addSysUser(BSysUser sysUser, String sysType) {

        //判断获取到选择的角色集合
//        String roleIdListStr = sysUser.extv().getString("roleIdListStr");
//        if(StringKit.isEmpty(roleIdListStr)) throw new BizException("请选择角色信息！");
//
//        List<String> roleIdList = JSONArray.parseArray(roleIdListStr, String.class);
//        if(roleIdList.isEmpty()) throw new BizException("请选择角色信息！");

        // 判断数据来源
        if (StringUtils.isEmpty(sysUser.getLoginUsername())) {
            throw new BizException("登录用户名不能为空！");
        }
        if (StringUtils.isEmpty(sysUser.getRealname())) {
            throw new BizException("姓名不能为空！");
        }
        if (StringUtils.isEmpty(sysUser.getTelphone())) {
            throw new BizException("手机号不能为空！");
        }
        if (sysUser.getSex() == null) {
            throw new BizException("性别不能为空！");
        }

        //登录用户名不可重复
        if (ibSysUserService.count(BSysUser.gw().eq(BSysUser::getSysType, sysType).eq(BSysUser::getLoginUsername, sysUser.getLoginUsername())) > 0) {
            throw new BizException("登录用户名已存在！");
        }
        //手机号不可重复
        if (ibSysUserService.count(BSysUser.gw().eq(BSysUser::getSysType, sysType).eq(BSysUser::getTelphone, sysUser.getTelphone())) > 0) {
            throw new BizException("手机号已存在！");
        }
        //员工号不可重复
        if (ibSysUserService.count(BSysUser.gw().eq(BSysUser::getSysType, sysType).eq(BSysUser::getUserNo, sysUser.getUserNo())) > 0) {
            throw new BizException("员工号已存在！");
        }

        //女  默认头像
        if (sysUser.getSex() != null && CS.SEX_FEMALE == sysUser.getSex()) {
            sysUser.setAvatarUrl("https://jeequan.oss-cn-beijing.aliyuncs.com/jeepay/img/defava_f.png");
        } else {
            sysUser.setAvatarUrl("https://jeequan.oss-cn-beijing.aliyuncs.com/jeepay/img/defava_m.png");
        }

        //1. 插入用户主表
        sysUser.setSysType(sysType); // 系统类型
        ibSysUserService.save(sysUser);

        Long sysUserId = sysUser.getSysUserId();

        //添加到 user_auth表
        String authPwd = CS.DEFAULT_PWD;

        addUserAuthDefault(sysUserId, sysUser.getLoginUsername(), sysUser.getTelphone(), authPwd, sysType);

        //3. 添加用户角色信息
        //saveUserRole(sysUser.getSysUserId(), new ArrayList<>());

    }

    @Override
    /** 根据用户ID 删除用户缓存信息  **/
    public void delAuthentication(List<Long> sysUserIdList) {
        if (sysUserIdList == null || sysUserIdList.isEmpty()) {
            return;
        }
        for (Long sysUserId : sysUserIdList) {
            Collection<String> cacheKeyList = RedisUtil.keys(CS.getCacheKeyToken(sysUserId + "", "*"));
            if (cacheKeyList == null || cacheKeyList.isEmpty()) {
                continue;
            }
            for (String cacheKey : cacheKeyList) {
                RedisUtil.del(cacheKey);
            }
        }
    }


    //修改用户信息
    @Transactional
    public void updateSysUser(BSysUser sysUser) {

        Long sysUserId = sysUser.getSysUserId();
        BSysUser dbRecord = ibSysUserService.getById(sysUserId);

        if (dbRecord == null) {
            throw new BizException("该用户不存在");
        }

        //修改了手机号， 需要修改auth表信息
        if (!dbRecord.getTelphone().equals(sysUser.getTelphone())) {

            if (ibSysUserService.count(BSysUser.gw().eq(BSysUser::getSysType, dbRecord.getSysType()).eq(BSysUser::getTelphone, sysUser.getTelphone())) > 0) {
                throw new BizException("该手机号已关联其他用户！");
            }

            resetAuthInfo(sysUserId, null, sysUser.getTelphone(), null, dbRecord.getSysType());
        }

        //修改了手机号， 需要修改auth表信息
        if (!dbRecord.getLoginUsername().equals(sysUser.getLoginUsername())) {

            if (ibSysUserService.count(BSysUser.gw().eq(BSysUser::getSysType, dbRecord.getSysType()).eq(BSysUser::getLoginUsername, sysUser.getLoginUsername())) > 0) {
                throw new BizException("该登录用户名已关联其他用户！");
            }

            resetAuthInfo(sysUserId, sysUser.getLoginUsername(), null, null, dbRecord.getSysType());
        }

        //修改用户主表
        ibSysUserService.updateById(sysUser);
    }

    /**
     * 添加用户认证表
     **/
    @Transactional
    public void addUserAuthDefault(Long userId, String loginUserName, String telPhone, String pwdRaw, String sysType) {

        String salt = StringKit.getUUID(6); //6位随机数
        String userPwd = new BCryptPasswordEncoder().encode(pwdRaw);

        /** 用户名登录方式 */
        BSysUserAuth record = new BSysUserAuth();
        record.setUserId(userId);
        record.setCredential(userPwd);
        record.setSalt(salt);
        record.setSysType(sysType);
        record.setIdentityType(CS.AUTH_TYPE.LOGIN_USER_NAME);
        record.setIdentifier(loginUserName);
        ibSysUserAuthService.save(record);

        /** 手机号登录方式 */
        record = new BSysUserAuth();
        record.setUserId(userId);
        record.setCredential(userPwd);
        record.setSalt(salt);
        record.setSysType(sysType);
        record.setIdentityType(CS.AUTH_TYPE.TELPHONE);
        record.setIdentifier(telPhone);
        ibSysUserAuthService.save(record);
    }


    @Transactional
    public void removeRole(String roleId) {
        if (sysUserRoleRelaService.count(BSysUserRoleRela.gw().eq(BSysUserRoleRela::getRoleId, roleId)) > 0) {
            throw new BizException("当前角色已分配到用户， 不可删除！");
        }
        //删除当前表
        sysUserRoleRelaService.removeById(roleId);
        //删除关联表
        sysUserRoleRelaService.remove(BSysUserRoleRela.gw().eq(BSysUserRoleRela::getRoleId, roleId));
    }

    /**
     * 删除用户
     **/
    @Transactional
    public void removeUser(BSysUser sysUser, String sysType) {
        // 1.删除用户登录信息
        ibSysUserAuthService.remove(BSysUserAuth.gw()
                .eq(BSysUserAuth::getSysType, sysType)
                .in(BSysUserAuth::getUserId, sysUser.getSysUserId())
        );
        // 2.删除用户角色信息
        sysUserRoleRelaService.removeById(sysUser.getSysUserId());
        // 3.删除用户信息
        ibSysUserService.removeById(sysUser.getSysUserId());
    }


    /**
     * 分配用户角色
     **/
    @Transactional
    public void saveUserRole(Long userId, List<String> roleIdList) {

        //删除用户之前的 角色信息
        sysUserRoleRelaService.remove(BSysUserRoleRela.gw().eq(BSysUserRoleRela::getUserId, userId));
        for (String roleId : roleIdList) {
            BSysUserRoleRela addRecord = new BSysUserRoleRela();
            addRecord.setUserId(userId);
            addRecord.setRoleId(roleId);
            sysUserRoleRelaService.save(addRecord);
        }
    }


}
