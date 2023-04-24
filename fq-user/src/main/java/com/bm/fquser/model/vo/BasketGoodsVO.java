package com.bm.fquser.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasketGoodsVO {

    @ApiModelProperty(value = "prodId", required = true)
    private String prodId;

    @ApiModelProperty(value = "skuID", required = true)
    private String skuId;

    @ApiModelProperty(value = "图片", required = true)
    private String pic;

    @ApiModelProperty(value = "商品名称", required = true)
    private String prdName;

    @ApiModelProperty(value = "规格名称", required = true)
    private String properties;

    @ApiModelProperty(value = "商品价格", required = true)
    private String price;

    @ApiModelProperty(value = "数量", required = true)
    private String count;

}
