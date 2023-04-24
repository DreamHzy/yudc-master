package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderAndSkuDTO implements Serializable {

//    @ApiModelProperty("商品id")
//    private String prodId;
    @ApiModelProperty("商品名称")
    private String prodName;
    @ApiModelProperty("商品规格")
    private String properties;
    @ApiModelProperty("商品主图")
    private String pic;
    @ApiModelProperty("商品价格")
    private String price;
    @ApiModelProperty("商品总数")
    private String prodCount;

}
