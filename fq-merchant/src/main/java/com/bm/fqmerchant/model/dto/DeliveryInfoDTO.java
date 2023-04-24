package com.bm.fqmerchant.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryInfoDTO implements Serializable {

    @ApiModelProperty("订单编号")
    private String orderNumber;

    @ApiModelProperty("快递单号")
    private String dvyFlowId;

    @ApiModelProperty("快递公司")
    private String dvyId;

}
