package com.bm.fquser.mapper;

import com.bm.fqservice.model.BBasket;
import com.bm.fquser.model.vo.BasketGoodsVO;
import com.bm.fquser.model.vo.ShopCartAmountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BasketMapper {
    List<BasketGoodsVO> basketGoodsVOS(@Param("userId") Long userId, @Param("url") String url);

    ShopCartAmountDto getTotalPay(@Param("skuIds")List<String> skuIds,@Param("userId") Long userId);
}
