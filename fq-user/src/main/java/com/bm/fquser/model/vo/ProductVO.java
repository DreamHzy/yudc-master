package com.bm.fquser.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductVO {

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = true)
    private Long prodId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String prodName;

    /**
     * 商品详情
     */
    @ApiModelProperty(value = "详细描述")
    private String content;

    /**
     * 商品主图
     */
    @ApiModelProperty(value = "商品主图", required = true)
    private String pic;



    @ApiModelProperty(value = "sku列表")
    private List<SkuVO> skuList;


}
