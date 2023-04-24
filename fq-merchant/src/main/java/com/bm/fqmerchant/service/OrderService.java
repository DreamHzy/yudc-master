package com.bm.fqmerchant.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fqmerchant.model.dto.DeliveryInfoDTO;
import com.bm.fqmerchant.model.dto.OrderDTO;
import com.bm.fqmerchant.model.dto.OrderInfoDTO;
import com.bm.fqmerchant.model.vo.OrderListVO;

public interface OrderService {

    ApiRes<OrderDTO> orderInfo(String orderNumber);

    ApiRes recallOrder(String orderNumber);

    ApiRes deliveryInfo(DeliveryInfoDTO paramJSON );

    ApiRes receipt(String orderNumber);

    ApiRes<OrderListVO> orderList(String pageNumber,String pageSize , JSONObject paramJSON, JeeUserDetails currentUser);
}
