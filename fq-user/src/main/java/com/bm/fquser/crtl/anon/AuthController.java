package com.bm.fquser.crtl.anon;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.bm.fqcore.cache.RedisUtil;
import com.bm.fqcore.constants.CS;
import com.bm.fqcore.exception.BizException;
import com.bm.fqcore.model.ApiRes;
import com.bm.fquser.crtl.CommonCtrl;
import com.bm.fquser.model.dto.SmsLogin;
import com.bm.fquser.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录鉴权
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@Api(tags = "登录鉴权相关接口")
@Slf4j
@RestController
@RequestMapping("/api/anon/auth")
public class AuthController extends CommonCtrl {

    @Resource
    private AuthService authService;

    /**
     * 密码登录
     **/
    @ApiOperation("密码登录")
    @RequestMapping(value = "/pwdLogin", method = RequestMethod.POST)
    public ApiRes validate(@RequestBody SmsLogin smsLogin) throws BizException {

        String code = smsLogin.getCode();  //用户名 i account, 已做base64处理
        String phone = smsLogin.getPhone();    //密码 i passport,  已做base64处理

//        String code = Base64.decodeStr(smsLogin.getCode());  //用户名 i account, 已做base64处理
//        String phone = Base64.decodeStr(smsLogin.getPhone());    //密码 i passport,  已做base64处理
        // 返回前端 accessToken
        String accessToken = authService.auth(code, phone);
        return ApiRes.ok4newJson(CS.ACCESS_TOKEN_NAME, accessToken);
    }


    /**
     * 短信验证码
     **/
    @RequestMapping(value = "/vercode", method = RequestMethod.GET)
    public ApiRes vercode(@ApiParam(name = "phone", value = "手机号", required = true) @NonNull @RequestParam("phone") String phone) throws BizException {
        //redis
//        String vercodeToken = UUID.fastUUID().toString();
        RedisUtil.setString(CS.getCacheKeysSmsCode(phone), "111111", CS.VERCODE_CACHE_TIME); //短信验证码: 1分钟
        JSONObject result = new JSONObject();
//        result.put("vercodeToken", vercodeToken);
        result.put("expireTime", CS.VERCODE_CACHE_TIME);
        return ApiRes.ok(result);
    }


}
