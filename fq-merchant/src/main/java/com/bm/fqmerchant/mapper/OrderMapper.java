package com.bm.fqmerchant.mapper;

import com.bm.fqmerchant.model.dto.DeliveryInfoDTO;
import com.bm.fqmerchant.model.dto.OrderInfoDTO;
import com.bm.fqmerchant.model.dto.SpecificationDTO;
import com.bm.fqmerchant.model.vo.OrderListVO;
import com.bm.fqservice.model.BOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

//    List<OrderListVO> selectOrderList(Long shopId, String prodNumber, String prodName, Integer prodStatus);

    OrderInfoDTO selectInfo(String orderNumber);

//    List<OrderListVO> selectOrderList(@Param(value = "shopId") Long shopId, @Param(value = "orderNumber")String orderNumber, @Param(value = "prodStatus")String prodStatus);

    boolean updateByStatus(@Param(value = "orderNumber")String orderNumber);

    boolean updateDvy(DeliveryInfoDTO infoDTO);

    boolean updateByStatusTwo(BOrder order_number);

    List<SpecificationDTO>  selectOrderDetails(String orderNumber,String url);

    List<OrderListVO> selectOrderList(@Param(value = "shopId")Long shopId,@Param(value = "orderNumber") String orderNumber, @Param(value = "prodStatus")String prodStatus, @Param(value = "a")String a,@Param(value = "b") String b,@Param(value = "userName")String userName);

//    List<OrderListVO> selectOrderList(Long shopId, String orderNumber, String prodStatus, String createdAt);
}
