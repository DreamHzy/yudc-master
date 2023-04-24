package com.bm.fqmerchant.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodListVO {

    @ApiModelProperty("商品编码")
    private String goodsCode;

    @ApiModelProperty("商品图片")
    private String pic;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("库存")
    private String totalStocks;

    @ApiModelProperty("1，上架, 0下架")
    private String status;

    private String statusMsg;
}
