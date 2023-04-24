package com.bm.fqmerchant.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderInfoDTO implements Serializable {

    @ApiModelProperty("订单编号")
    private String orderNumber;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("下单人")
    private String userName;

    @ApiModelProperty("状态")
    private String prodStatus;

    @ApiModelProperty("收货人")
    private String receiver;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("快递公司")
    private String dvyName;

    @ApiModelProperty("快递单号")
    private String dvyNumber;

    @ApiModelProperty("订单金额")
    private String actualTotal;

    @ApiModelProperty("下单时间")
    private String createdAt;


}
