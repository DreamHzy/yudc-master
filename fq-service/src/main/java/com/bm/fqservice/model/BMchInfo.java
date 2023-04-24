package com.bm.fqservice.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户信息表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BMchInfo implements Serializable {


    //gw
    public static final LambdaQueryWrapper<BMchInfo> gw(){
        return new LambdaQueryWrapper<>();
    }

    private static final long serialVersionUID=1L;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 商户名称
     */
    private String mchName;

    /**
     * 商户简称
     */
    private String mchShortName;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactTel;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 商户状态: 0-停用, 1-正常
     */
    private Byte state;

    /**
     * 商户备注
     */
    private String remark;

    /**
     * 初始用户ID（创建商户时，允许商户登录的用户）
     */
    private Long initUserId;

    /**
     * 创建者用户ID
     */
    private Long createdUid;

    /**
     * 创建者姓名
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;


}
