package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryShippingDTO implements Serializable {

    @ApiModelProperty("收货地址id")
    private String addrId;

    @ApiModelProperty("收货人姓名")
    private String receiver;

    @ApiModelProperty("收货电话")
    private String mobile;

    @ApiModelProperty("收货人地址")
    private String addr;

    @ApiModelProperty("是否默认 0否 1是")
    private Integer commonAddr;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("省id")
    private Long provinceId;

    @ApiModelProperty("市id")
    private Long cityId;

    @ApiModelProperty("区id")
    private Long areaId;
}
