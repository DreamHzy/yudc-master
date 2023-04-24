package com.bm.fquser.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class HomeDTO implements Serializable {

    @ApiModelProperty("商品id")
    private String prodId;
    @ApiModelProperty("商品名称")
    private String prodName;
    @ApiModelProperty("商品销量")
    private String soldNum;
    @ApiModelProperty("商品主图")
    private String pic;
    @ApiModelProperty("商品价格")
    private String price;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(String soldNum) {
        this.soldNum = soldNum;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeDTO homeDTO = (HomeDTO) o;
        return Objects.equals(prodId, homeDTO.prodId) && Objects.equals(prodName, homeDTO.prodName) && Objects.equals(soldNum, homeDTO.soldNum) && Objects.equals(pic, homeDTO.pic) && Objects.equals(price, homeDTO.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodId, prodName, soldNum, pic, price);
    }
}
