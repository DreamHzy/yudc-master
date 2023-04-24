package com.bm.fqmerchant.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpecificationDTO implements Serializable {

    @ApiModelProperty("规格名称")
    private String properties;

    @ApiModelProperty("图片地址")
    private String image;

    @ApiModelProperty("商品名称")
    private String prodName;

    @ApiModelProperty("订单编号")
    private String orderNumber;

    @ApiModelProperty("单价")
    private String price;

    @ApiModelProperty("数量")
    private String prodCount;

    @ApiModelProperty("总额")
    private String productTotalAmount;

    @ApiModelProperty("联系电话")
    private String phone;

}
