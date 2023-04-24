package com.bm.fquser.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConfirmOrdervVO {
    @ApiModelProperty("底部总金额")
    private String bottoMmoney;
    @ApiModelProperty("收货地址id")
    private String addrId;
    @ApiModelProperty("收货人姓名")
    private String receiver;
    @ApiModelProperty("收货电话")
    private String mobile;
    @ApiModelProperty("收货人地址")
    private String addr;
    private List<SkuDetailVO> skuDetailVOS;
}
