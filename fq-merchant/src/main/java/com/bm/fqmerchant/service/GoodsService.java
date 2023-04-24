package com.bm.fqmerchant.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fqmerchant.model.dto.ProductAddDTO;
import com.bm.fqmerchant.model.dto.ProductEditDTO;
import com.bm.fqmerchant.model.vo.GoodDetailVO;
import com.bm.fqmerchant.model.vo.GoodListVO;
import com.bm.fqmerchant.model.vo.OrderListVO;

public interface GoodsService {
    ApiRes goodsAdd(ProductAddDTO productAddDTO, JeeUserDetails currentUser);

    ApiRes goodsEdit(ProductEditDTO productEditDTO, JeeUserDetails currentUser);

    ApiRes<GoodListVO> goodList(IPage iPage, JSONObject paramJSON, JeeUserDetails currentUser);

    ApiRes goodStatus(String goodsCode, String status);

    ApiRes<GoodDetailVO> goodDetail(String goodsCode);
}
