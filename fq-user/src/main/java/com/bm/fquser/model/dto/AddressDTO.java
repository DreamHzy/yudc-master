package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO implements Serializable {

    @ApiModelProperty("收货地址id")
    private String addrId;

    @ApiModelProperty("状态(1启动 0禁用)")
    private String status;

    @ApiModelProperty("收货人姓名")
    private String receiver;

    @ApiModelProperty("收货电话")
    private String mobile;

    @ApiModelProperty("收货人地址")
    private String addr;

    @ApiModelProperty("默认地址")
    private String commonAddr;


    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDTO that = (AddressDTO) o;
        return Objects.equals(addrId, that.addrId) && Objects.equals(status, that.status) && Objects.equals(receiver, that.receiver) && Objects.equals(mobile, that.mobile) && Objects.equals(addr, that.addr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addrId, status, receiver, mobile, addr);
    }
}
