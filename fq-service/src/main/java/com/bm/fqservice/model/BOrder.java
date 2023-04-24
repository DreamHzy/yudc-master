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
 * 订单表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BOrder implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 订单ID
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 产品名称,多个产品将会以逗号隔开
     */
    private String prodName;

    /**
     * 订购用户ID
     */
    private String userId;

    /**
     * 订购流水号
     */
    private String orderNumber;

    /**
     * 总值
     */
    private BigDecimal total;

    /**
     * 实际总值
     */
    private BigDecimal actualTotal;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝 3、银联分期支付
     */
    private Integer payType;

    /**
     * 订单备注
     */
    private String remarks;

    /**
     * 订单状态 1:待付款 2:待发货 3:待收货 4:待评价 5:成功 6:失败
     */
    private Integer status;

    /**
     * 配送类型
     */
    private String dvyType;

    /**
     * 物流公司ID
     */
    private Long dvyId;

    /**
     * 物流单号
     */
    private String dvyFlowId;

    /**
     * 订单运费
     */
    private BigDecimal freightAmount;

    /**
     * 用户订单地址Id
     */
    private Long addrOrderId;

    /**
     * 订单商品总数
     */
    private Integer productNums;

    /**
     * 订购时间
     */
    private Date createdAt;

    /**
     * 订单更新时间
     */
    private Date updatedAt;

    /**
     * 付款时间
     */
    private Date payTime;

    /**
     * 发货时间
     */
    private Date dvyTime;

    /**
     * 完成时间
     */
    private Date finallyTime;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 是否已经支付，1：已经支付过，0：，没有支付过
     */
    private Byte isPayed;

    /**
     * 用户订单删除状态，0：没有删除， 1：回收站， 2：永久删除
     */
    private Integer deleteStatus;

    /**
     * 0:默认,1:在处理,2:处理完成
     */
    private Integer refundSts;

    /**
     * 优惠总额
     */
    private BigDecimal reduceAmount;

    /**
     * 订单关闭原因 1-超时未支付 2-退款关闭 4-买家取消 15-已通过货到付款交易
     */
    private Byte closeType;


}
