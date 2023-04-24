package com.bm.fqservice.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 单品SKU表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BSku implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 单品ID
     */
    @TableId(value = "sku_id", type = IdType.AUTO)
    private Long skuId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 销售属性组合字符串 格式是p1:v1;p2:v2
     */
    private String properties;

    /**
     * 原价
     */
    private BigDecimal costPrice;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商品在付款减库存的状态下，该sku上未付款的订单数量
     */
    private Integer stocks;

    /**
     * 实际库存
     */
    private Integer actualStocks;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 记录时间
     */
    private Date recTime;

    /**
     * 商家编码
     */
    private String partyCode;

    /**
     * 商品条形码
     */
    private String modelId;

    /**
     * sku图片
     */
    private String pic;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 商品重量
     */
    private Double weight;

    /**
     * 商品体积
     */
    private Double volume;

    /**
     * 0 禁用 1 启用
     */
    private Byte status;

    /**
     * 0 正常 1 已被删除
     */
    private Byte isDelete;



    //gw
    public static final LambdaQueryWrapper<BSku> gw() {
        return new LambdaQueryWrapper<>();
    }

}
