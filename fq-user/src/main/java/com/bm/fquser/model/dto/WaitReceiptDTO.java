package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WaitReceiptDTO implements Serializable {

    @ApiModelProperty("订单信息")
    List<OrderAndSkuDTO> andSkuDTOList;

    @ApiModelProperty("配送地址")
    WaitAddressDTO addressDTO;

}
