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
 * 订单支付表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BOrderSettlement implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 支付结算单据ID
     */
    @TableId(value = "settlement_id", type = IdType.AUTO)
    private Long settlementId;

    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 外部订单流水号
     */
    private String bizPayNo;

    /**
     * order表中的订单号
     */
    private String orderNumber;

    /**
     * 支付方式 1 微信支付 2 支付宝 3 银联分期支付
     */
    private Integer payType;

    /**
     * 支付方式名称
     */
    private String payTypeName;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 是否清算 0:否 1:是
     */
    private Integer isClearing;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 清算时间
     */
    private Date clearingTime;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 支付状态 1 待支付 2已支付 3支付失败  4 发生退款(部分退款) 5全部退款
     */
    private Integer payStatus;


}
