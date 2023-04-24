package com.bm.fqmerchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bm.fqcomponentsoss.config.AliyunOssYmlConfig;
import com.bm.fqcore.comment.PageData;
import com.bm.fqcore.comment.PageWrap;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fqmerchant.mapper.OrderMapper;
import com.bm.fqmerchant.model.dto.*;
import com.bm.fqmerchant.model.vo.OrderListVO;
import com.bm.fqmerchant.service.OrderService;
import com.bm.fqservice.mapper.BShopDetailMapper;
import com.bm.fqservice.model.BOrder;
import com.bm.fqservice.model.BShopDetail;
import com.bm.fqservice.model.BSysUser;
import com.bm.fqservice.service.IBOrderService;
import com.bm.fqservice.service.impl.BOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.bm.fqcore.model.ApiRes.page;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;
    @Resource
    BShopDetailMapper bShopDetailMapper;
    @Resource
    BOrderService bOrderService;
    @Resource
    IBOrderService ibOrderService;
    @Resource
    AliyunOssYmlConfig aliyunOssYmlConfig;

    @Override
    public ApiRes<OrderListVO> orderList(String pageNumber,String pageSize, JSONObject paramJSON, JeeUserDetails currentUser) {
        BSysUser sysUser = currentUser.getSysUser();
        BShopDetail bShopDetail = bShopDetailMapper.selectOne(new QueryWrapper<BShopDetail>().eq("mch_no", sysUser.getBelongInfoId()));

        String orderNumber = paramJSON.getString("orderNumber");

        String  endTime = paramJSON.getString("endTime");
        String  createTime = paramJSON.getString("createTime");

        String  userName = paramJSON.getString("userName");

        String  prodStatus = paramJSON.getString("prodStatus");

        String a = "";
        String b = "";
        if (!createTime.equals("")){
            a=createTime+" "+"00:00:00";
            b=endTime+" "+"23:59:59";
        }
        PageHelper.startPage(Integer.valueOf(pageNumber), Integer.valueOf(pageSize));
        List<OrderListVO> orderListVO = orderMapper.selectOrderList(bShopDetail.getShopId(), orderNumber,prodStatus,a,b,userName);
        PageData<OrderListVO> from = PageData.from(new PageInfo<>(orderListVO));
        return ApiRes.ok(from);
    }

    @Override
    public ApiRes<OrderDTO>   orderInfo(String orderNumber) {
        BOrder order_number = bOrderService.getOne(new QueryWrapper<BOrder>().eq("order_number", orderNumber));
        if (order_number == null) {
            return ApiRes.customFail("订单不存在");
        }
        OrderDTO orderDTO = new OrderDTO();

        List<SpecificationDTO> specificationDTO = orderMapper.selectOrderDetails(orderNumber,aliyunOssYmlConfig.getUrl());
        OrderInfoDTO orderInfoDTO = orderMapper.selectInfo(orderNumber);

        orderDTO.setSpecificationDTO(specificationDTO);
        orderDTO.setOrderInfoDTO(orderInfoDTO);
        return ApiRes.ok(orderDTO);
    }

    @Override
    public ApiRes recallOrder(String orderNumber) {
        BOrder order_number = bOrderService.getOne(new QueryWrapper<BOrder>().eq("order_number", orderNumber));
        if (order_number == null) {
            return ApiRes.customFail("订单不存在");
        }
        if (!orderMapper.updateByStatus(orderNumber)) {
            return ApiRes.customFail(HttpStatus.BAD_REQUEST.toString());
        }
        return ApiRes.ok(HttpStatus.OK);
    }

    @Override
    public ApiRes deliveryInfo(DeliveryInfoDTO deliveryInfoDTO ) {
        if (deliveryInfoDTO == null) {
            return ApiRes.customFail("参数错误");
        }
        String orderNumber =deliveryInfoDTO.getOrderNumber();
        BOrder bOrder = bOrderService.getOne(new QueryWrapper<BOrder>().eq("order_number", orderNumber));
        if (bOrder == null) {
            return ApiRes.customFail("订单不存在");
        }
//        bOrder.setDvyId(Long.parseLong(deliveryInfoDTO.getDvyId()));
//        bOrder.setDvyFlowId(deliveryInfoDTO.getDvyFlowId());
        if (!orderMapper.updateDvy(deliveryInfoDTO)) {
            return ApiRes.customFail("配送信息保存失败");
        }
        return ApiRes.ok(HttpStatus.OK);
    }

    @Override
    public ApiRes receipt(String orderNumber) {
        BOrder order_number = bOrderService.getOne(new QueryWrapper<BOrder>().eq("order_number", orderNumber));
        if (order_number == null) {
            return ApiRes.customFail("订单不存在");
        }
        if (!orderMapper.updateByStatusTwo(order_number)){
            return ApiRes.customFail("保存失败");
        }
        return ApiRes.ok(HttpStatus.OK);
    }
}
