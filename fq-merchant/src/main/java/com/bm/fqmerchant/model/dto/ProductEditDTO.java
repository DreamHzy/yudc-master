package com.bm.fqmerchant.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductEditDTO {

    private String goodCode;

    @ApiModelProperty("商品名称")
    private String prodName;

    @ApiModelProperty("商品图片相对地址")
    private String pic;

    @ApiModelProperty("商品详情")
    private String content;


    @ApiModelProperty("商品规格列表")
    private List<SkuEditDTO> skuList;
}
