package com.bm.fqcore.model;


import lombok.Data;

import java.io.Serializable;

/*
 * 系统应用配置项定义Bean
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/6/8 16:35
 */
@Data
public class DBApplicationConfig implements Serializable {

    /** 运营系统地址 **/
    private String mgrSiteUrl;

    /** 商户系统地址 **/
    private String mchSiteUrl;


    /** oss公共读文件地址 **/
    private String ossPublicSiteUrl;



}
