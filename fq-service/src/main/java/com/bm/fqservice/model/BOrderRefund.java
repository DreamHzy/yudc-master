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
 * 订单退款记录表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BOrderRefund implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 记录ID
     */
    @TableId(value = "refund_id", type = IdType.AUTO)
    private Long refundId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 支付结算单据ID
     */
    private Long settlementId;

    /**
     * 订单流水号
     */
    private String orderNumber;

    /**
     * 订单总金额
     */
    private Double orderAmount;

    /**
     * 订单项ID 全部退款是0
     */
    private Long orderItemId;

    /**
     * 退款编号
     */
    private String refundSn;

    /**
     * 订单支付流水号
     */
    private String flowTradeNo;

    /**
     * 第三方退款单号(微信/支付宝/银联退款单号)
     */
    private String outRefundNo;

    /**
     * 订单支付方式 1 微信支付 2 支付宝 3银联分期支付
     */
    private Integer payType;

    /**
     * 订单支付名称
     */
    private String payTypeName;

    /**
     * 买家ID
     */
    private String userId;

    /**
     * 退货数量
     */
    private Integer goodsNum;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 申请类型:1,仅退款,2退款退货
     */
    private Integer applyType;

    /**
     * 处理状态:1为待审核,2为同意,3为不同意
     */
    private Integer refundSts;

    /**
     * 处理退款状态: 0:退款处理中 1:退款成功 -1:退款失败
     */
    private Integer returnMoneySts;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 卖家处理时间
     */
    private Date handelTime;

    /**
     * 退款时间
     */
    private Date refundTime;

    /**
     * 文件凭证json
     */
    private String photoFiles;

    /**
     * 申请原因
     */
    private String buyerMsg;

    /**
     * 卖家备注
     */
    private String sellerMsg;

    /**
     * 物流公司名称
     */
    private String expressName;

    /**
     * 物流单号
     */
    private String expressNo;

    /**
     * 发货时间
     */
    private Date shipTime;

    /**
     * 收货时间
     */
    private Date receiveTime;

    /**
     * 收货备注
     */
    private String receiveMessage;


}
