package com.bm.fqmerchant.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkuDTO {


    @ApiModelProperty("销售属性组合字符串,格式是p1:v1;p2:v2 eg:版本:原厂延保版;颜色:金色;内存:64GB")
    private String properties;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("库存")
    private String stocks;

    @ApiModelProperty("图片")
    private String pic;

    @ApiModelProperty("0 下架 1 上架'")
    private String status;
}
