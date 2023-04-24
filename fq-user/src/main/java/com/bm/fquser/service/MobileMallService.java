package com.bm.fquser.service;

import com.bm.fqcore.comment.PageWrap;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fquser.model.dto.*;
import com.bm.fquser.model.vo.ProductVO;

import java.util.List;

public interface MobileMallService {

    ApiRes<HomeDTO> home(PageWrap<String> pageWrap);

    ApiRes<ProductVO> details(String prodId);

    ApiRes<AddressDTO> shippingAddress(PageWrap<String> pageWrap, AppUserDetails jeeUserDetails);

    ApiRes maintainShippingAddress(MaintainDTO maintainDTO, AppUserDetails jeeUserDetails);

    ApiRes<QueryShippingDTO> queryShippingAddress(Integer addrId);

    ApiRes<OrderDTO> myOrder(PageWrap<List<String>> pageWrap, AppUserDetails jeeUserDetails);

    ApiRes<WaitReceiptDTO> waitReceipt(String orderNumber);

    ApiRes confirmReceipt(String orderNumber);

    ApiRes cancelOrder(String orderNumber);

    ApiRes deleteAddress(long addrId);

    ApiRes money(String skuId, String num);


    ApiRes confirmOrder(AppUserDetails jeeUserDetails,List<ConfirmOrderDTO> confirmOrderDTO);

    ApiRes pay(AppUserDetails jeeUserDetails, PayDTO payDTO);
}
