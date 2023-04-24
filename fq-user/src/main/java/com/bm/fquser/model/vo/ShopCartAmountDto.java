package com.bm.fquser.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopCartAmountDto {


    @ApiModelProperty("合计")
    private Double finalMoney;

    @ApiModelProperty("商品数量")
    private Integer count;
}
