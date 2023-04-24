package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SmsLogin {

    @ApiModelProperty("验证码，测试环境默认111111")
    private String code;

    @ApiModelProperty("手机号")
    private String phone;
}
