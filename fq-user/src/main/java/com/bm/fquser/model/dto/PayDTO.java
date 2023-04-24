package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayDTO {

    private List<ConfirmOrderDTO> confirmOrderDTOList;

    @ApiModelProperty("收货地址id")
    private String addrId;
}
