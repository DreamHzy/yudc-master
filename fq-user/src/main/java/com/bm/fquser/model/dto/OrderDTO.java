package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.PipedReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO implements Serializable {

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("订购流水号")
    private String orderNumber;

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("订单商品总数")
    private Integer productNums;

    @ApiModelProperty("订单商品总价")
    private BigDecimal total;

    @ApiModelProperty("商品信息")
    private List<OrderAndSkuDTO> andSkuDTOList;

}
