package com.bm.fqmerchant.model.vo;

import com.bm.fqmerchant.model.dto.SpecificationDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderListVO implements Serializable {

    @ApiModelProperty("订单编号")
    private String orderNumber;

    @ApiModelProperty("订单金额")
    private String actualTotal;

    @ApiModelProperty("订单状态")
    private String prodStatus;

    @ApiModelProperty("下单人")
    private String userName;

    @ApiModelProperty("下单时间")
    private String createdAt;

    @ApiModelProperty("状态描述")
    private String prodStatusDesc;
}
