package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WaitAddressDTO implements Serializable {

    @ApiModelProperty("快递公司")
    private String dvyName;

    @ApiModelProperty("快递单号")
    private String dvyFlowId;

    @ApiModelProperty("收货地址id")
    private String addrId;

    @ApiModelProperty("收货人姓名")
    private String receiver;

    @ApiModelProperty("收货电话")
    private String mobile;

    @ApiModelProperty("收货人地址")
    private String addr;
}
