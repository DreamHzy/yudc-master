package com.bm.fqservice.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BUser implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户邮箱
     */
    private String userMail;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 手机号码
     */
    private String userMobile;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 注册时间
     */
    private Date userRegtime;

    /**
     * 注册IP
     */
    private String userRegip;

    /**
     * 最后登录时间
     */
    private Date userLasttime;

    /**
     * 最后登录IP
     */
    private String userLastip;

    /**
     * 备注
     */
    private String userMemo;

    /**
     * M(男) or F(女)
     */
    private String sex;

    /**
     * 例如：2009-11-27
     */
    private String birthDate;

    /**
     * 头像图片路径
     */
    private String pic;

    /**
     * 状态 1 正常 0 无效
     */
    private Integer state;

    /**
     * 用户积分
     */
    private Integer score;


    //gw
    public static final LambdaQueryWrapper<BUser> gw() {
        return new LambdaQueryWrapper<>();
    }
}
