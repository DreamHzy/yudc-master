package com.bm.fqmerchant.model.vo;

import com.bm.fqmerchant.model.dto.SkuDTO;
import com.bm.fqmerchant.model.dto.SkuEditDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GoodDetailVO {
    private String goodCode;

    private String ossUrl;

    @ApiModelProperty("商品名称")
    private String prodName;

    @ApiModelProperty("商品图片相对地址")
    private String pic;


    @ApiModelProperty("商品内容")
    private String content;

    @ApiModelProperty("商品规格列表")
    private List<SkuDetailDTO> skuList;

}
