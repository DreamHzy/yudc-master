package com.bm.fquser.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkuVO implements Serializable {

    private static final long serialVersionUID = 6457261945829470666L;

    @ApiModelProperty(value = "skuId", required = true)
    private Long skuId;
    @ApiModelProperty(value = "价格", required = true)
    private BigDecimal price;
    @ApiModelProperty(value = "原价")
    private BigDecimal oriPrice;

    @ApiModelProperty(value = "库存(-1表示无穷)", required = true)
    private Integer stocks;

    @ApiModelProperty(value = "sku名称", required = true)
    private String skuName;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "销售属性组合字符串,格式是p1:v1;p2:v2", required = true)
    private String properties;
}
