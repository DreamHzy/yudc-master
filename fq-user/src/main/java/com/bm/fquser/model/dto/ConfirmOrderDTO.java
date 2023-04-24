package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConfirmOrderDTO {
    @ApiModelProperty("skuId")
    private String skuId;
    @ApiModelProperty("数量")
    private String num;
}
