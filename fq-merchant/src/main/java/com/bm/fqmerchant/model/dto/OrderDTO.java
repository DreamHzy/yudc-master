package com.bm.fqmerchant.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDTO implements Serializable {

    @ApiModelProperty("订单信息")
    private OrderInfoDTO orderInfoDTO;

    @ApiModelProperty("商品列表")
    private List<SpecificationDTO> specificationDTO;

}
