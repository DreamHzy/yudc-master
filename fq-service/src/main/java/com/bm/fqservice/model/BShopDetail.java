package com.bm.fqservice.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 门店详情
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BShopDetail implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 店铺id
     */
    @TableId(value = "shop_id", type = IdType.AUTO)
    private Long shopId;

    /**
     * 店铺名称(数字、中文，英文(可混合，不可有特殊字符)，可修改)、不唯一
     */
    private String shopName;

    /**
     * 商户编号
     */
    private String mchNo;

    /**
     * 店铺类型
     */
    private Byte shopType;

    /**
     * 店铺简介(可修改)
     */
    private String intro;

    /**
     * 店铺公告(可修改)
     */
    private String shopNotice;

    /**
     * 店铺行业(餐饮、生鲜果蔬、鲜花等)
     */
    private Byte shopIndustry;

    /**
     * 店长
     */
    private String shopOwner;

    /**
     * 店铺绑定的手机(登录账号：唯一)
     */
    private String mobile;

    /**
     * 店铺联系电话
     */
    private String tel;

    /**
     * 店铺所在纬度(可修改)
     */
    private String shopLat;

    /**
     * 店铺所在经度(可修改)
     */
    private String shopLng;

    /**
     * 店铺详细地址
     */
    private String shopAddress;

    /**
     * 店铺所在省份（描述）
     */
    private String province;

    /**
     * 店铺所在城市（描述）
     */
    private String city;

    /**
     * 店铺所在区域（描述）
     */
    private String area;

    /**
     * 店铺省市区代码，用于回显
     */
    private String pcaCode;

    /**
     * 店铺logo(可修改)
     */
    private String shopLogo;

    /**
     * 店铺相册
     */
    private String shopPhotos;

    /**
     * 每天营业时间段(可修改)
     */
    private String openTime;

    /**
     * 店铺状态(-1:未开通 0: 停业中 1:营业中)，可修改
     */
    private Byte shopStatus;

    /**
     * 0:商家承担运费; 1:买家承担运费
     */
    private Byte transportType;

    /**
     * 固定运费
     */
    private BigDecimal fixedFreight;

    /**
     * 满X包邮
     */
    private BigDecimal fullFreeShipping;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 分销开关(0:开启 1:关闭)
     */
    private Byte isDistribution;


}
