package com.bm.fquser.service;

import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fquser.model.dto.ChangeShopCartDTO;
import com.bm.fquser.model.vo.BasketGoodsVO;
import com.bm.fquser.model.vo.ShopCartAmountDto;

import java.util.List;

public interface BasketService {
    ApiRes addItem(AppUserDetails jeeUserDetails, ChangeShopCartDTO param);

    ApiRes<BasketGoodsVO> info(AppUserDetails jeeUserDetails);

    ApiRes deleteItem(List<String> skuIds);

    ApiRes deleteAllShopCartItems(Long userId);

    ApiRes<ShopCartAmountDto> getTotalPay(AppUserDetails AppUserDetails,List<String> skuIds);
}
