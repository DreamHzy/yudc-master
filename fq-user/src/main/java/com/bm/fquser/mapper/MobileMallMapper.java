package com.bm.fquser.mapper;

import com.bm.fqservice.model.BOrder;
import com.bm.fquser.model.dto.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface MobileMallMapper {

//    List<HomeDTO> selectHome(@Param(value = "wht") String wht, @Param(value = "url")String url);

    List<AddressDTO> selectAddress(Long sysUserId);

    boolean update(Long userId);

//    List<OrderDTO> selectOrder(@Param(value = "model") String model, @Param(value = "userId") Long userId);

    List<OrderAndSkuDTO> selectOrderAndSku(String orderNumber, String url);

    WaitAddressDTO selectWait(String orderNumber);

    boolean updateStatus(String orderNumber);

    boolean updateOrder(String orderNumber);

    List<OrderDTO> selectOrder(@Param(value = "model")  List<String> model, @Param(value = "userId")  Long userId);

    boolean updateAddress(long addrId);

    List<HomeDTO> selectHome(@Param(value = "model") String model,@Param(value = "url") String url);

//    List<OrderDTO> selectOrder(List<String> model, Long userId);
}
