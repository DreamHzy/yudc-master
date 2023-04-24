package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@Data
public class MaintainDTO implements Serializable {

    @ApiModelProperty("要修改的地址的id")
    private Long addrId;

    @ApiModelProperty("姓名")
    private String receiver;

    @ApiModelProperty("联系电话")
    private String mobile;

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

    @ApiModelProperty("详细地址")
    private String addr;

    @ApiModelProperty("是否默认 0否 1是")
    private Integer commonAddr;


}
