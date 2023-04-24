package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangeShopCartDTO {


    @ApiModelProperty(value = "商品ID", required = true)
    private String prodId;

    @ApiModelProperty(value = "skuId", required = true)
    private String skuId;

    @ApiModelProperty(value = "商品个数", required = true)
    private Integer count;

}
