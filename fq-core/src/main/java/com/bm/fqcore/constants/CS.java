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
package com.bm.fqcore.constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author terrfly
 * @Date 2019/11/16 15:09
 * @Description Constants 常量对象
 **/
public class CS {

    //登录图形验证码缓存时间，单位：s
    public static final int VERCODE_CACHE_TIME = 60;

    /**
     * 系统类型定义
     **/
    public interface SYS_TYPE {
        String MCH = "MCH";
        String MGR = "MGR";
        String MDC_APP = "MDC_APP";
        Map<String, String> SYS_TYPE_MAP = new HashMap<>();
    }

    static {
        SYS_TYPE.SYS_TYPE_MAP.put(SYS_TYPE.MCH, "商户系统");
        SYS_TYPE.SYS_TYPE_MAP.put(SYS_TYPE.MGR, "运营平台");
        SYS_TYPE.SYS_TYPE_MAP.put(SYS_TYPE.MGR, "商户小程序");
    }

    /**
     * yes or no
     **/
    public static final byte NO = 0;
    public static final byte YES = 1;



    /** 通用 可用 / 禁用 **/
    public static final int PUB_USABLE = 1;
    public static final int PUB_DISABLE = 0;


    public static final Map<Integer, String> PUB_USABLE_MAP = new HashMap<>();

    static {
        PUB_USABLE_MAP.put(PUB_USABLE, "正常");
        PUB_USABLE_MAP.put(PUB_DISABLE, "停用");
    }


    /**
     * 性别 1- 男， 2-女
     */
    public static final byte SEX_UNKNOWN = 0;
    public static final byte SEX_MALE = 1;
    public static final byte SEX_FEMALE = 2;

    /**
     * 默认密码
     */
    public static final String DEFAULT_PWD = "bm6666";


    /**
     * 允许上传的的图片文件格式，需要与 WebSecurityConfig对应
     */
    public static final Set<String> ALLOW_UPLOAD_IMG_SUFFIX = new HashSet<>();

    static {
        ALLOW_UPLOAD_IMG_SUFFIX.add("jpg");
        ALLOW_UPLOAD_IMG_SUFFIX.add("png");
        ALLOW_UPLOAD_IMG_SUFFIX.add("jpeg");
        ALLOW_UPLOAD_IMG_SUFFIX.add("gif");
        ALLOW_UPLOAD_IMG_SUFFIX.add("mp4");
    }


    public static final long TOKEN_TIME = 60 * 60 * 2; //单位：s,  两小时


    //access_token 名称
    public static final String ACCESS_TOKEN_NAME = "iToken";

    /** ！！不同系统请放置不同的redis库 ！！ **/
    /**
     * 缓存key: 当前用户所有用户的token集合  example: TOKEN_1001_HcNheNDqHzhTIrT0lUXikm7xU5XY4Q
     */
    public static final String CACHE_KEY_TOKEN = "TOKEN_%s_%s";

    public static String getCacheKeyToken(String sysUserId, String uuid) {
        return String.format(CACHE_KEY_TOKEN, sysUserId, uuid);
    }

    /**
     * 图片验证码 缓存key
     **/
    public static final String CACHE_KEY_IMG_CODE = "img_code_%s";

    public static String getCacheKeyImgCode(String imgToken) {
        return String.format(CACHE_KEY_IMG_CODE, imgToken);
    }


    /**
     * 短信验证码 缓存key
     **/
    public static final String CACHE_KEY_SMS_CODE = "SMS_code_%s";

    public static String getCacheKeysSmsCode(String imgToken) {
        return String.format(CACHE_KEY_SMS_CODE, imgToken);
    }


    /**
     * 登录认证类型
     **/
    public interface AUTH_TYPE {

        byte LOGIN_USER_NAME = 1; //登录用户名
        byte TELPHONE = 2; //手机号
        byte EMAIL = 3; //邮箱

        byte WX_UNION_ID = 10; //微信unionId
        byte WX_MINI = 11; //微信小程序
        byte WX_MP = 12; //微信公众号

        byte QQ = 20; //QQ
    }


    //菜单类型
    public interface ENT_TYPE {

        String MENU_LEFT = "ML";  //左侧显示菜单
        String MENU_OTHER = "MO";  //其他菜单
        String PAGE_OR_BTN = "PB";  //页面 or 按钮

    }


}
