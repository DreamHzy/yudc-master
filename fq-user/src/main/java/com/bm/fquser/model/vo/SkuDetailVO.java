package com.bm.fquser.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkuDetailVO {

    @ApiModelProperty("skuId")
    private String skuId;
    @ApiModelProperty("商品名称")
    private String prdName;
    @ApiModelProperty("商品金额")
    private String prdMoney;
    @ApiModelProperty("商品数量")
    private String num;
    @ApiModelProperty("商品图片")
    private String pic;
    @ApiModelProperty("销售属性组合字符串")
    private String properties;
}
