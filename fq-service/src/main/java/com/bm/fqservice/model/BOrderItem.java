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
 * 订单项
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BOrderItem implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 订单项ID
     */
    @TableId(value = "order_item_id", type = IdType.AUTO)
    private Long orderItemId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 订单order_number
     */
    private String orderNumber;

    /**
     * 产品ID
     */
    private Long prodId;

    /**
     * 产品SkuID
     */
    private Long skuId;

    /**
     * 购物车产品个数
     */
    private Integer prodCount;

    /**
     * 产品名称
     */
    private String prodName;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 产品主图片路径
     */
    private String pic;

    /**
     * 产品价格
     */
    private BigDecimal price;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 商品总金额
     */
    private BigDecimal productTotalAmount;

    /**
     * 购物时间
     */
    private Date recTime;

    /**
     * 评论状态： 0 未评价  1 已评价
     */
    private Integer commSts;

    /**
     * 加入购物车时间
     */
    private Date basketDate;


}
