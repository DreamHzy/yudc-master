package com.bm.fqmerchant.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderListDTO implements Serializable {

    @ApiModelProperty("商品名称")
    private String prodName;

    @ApiModelProperty("商品编号")
    private String prodNumber;

    @ApiModelProperty("销售状态(1:待付款 2:待发货,待收货 3:待评价 4:成功 5:失败)")
    private Integer prodStatus;

}
