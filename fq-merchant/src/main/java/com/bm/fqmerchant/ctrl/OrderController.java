package com.bm.fqmerchant.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fqmerchant.model.dto.DeliveryInfoDTO;
import com.bm.fqmerchant.model.dto.OrderDTO;
import com.bm.fqmerchant.model.dto.OrderInfoDTO;
import com.bm.fqmerchant.model.vo.OrderListVO;
import com.bm.fqmerchant.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author terrfly
 * @modify zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@Api(tags = "订单列表相关接口")
@RestController
@RequestMapping("api/order")
@Slf4j
public class OrderController extends CommonCtrl {

    @Resource
    OrderService orderService;

    @ApiOperation("订单列表首页")
    @GetMapping("/orderList")
    public ApiRes<OrderListVO> orderList(
            @ApiParam(name = "pageNumber", value = "页数", required = true) @RequestParam String pageNumber,
            @ApiParam(name = "pageSize", value = "条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "orderNumber", value = "订单编号") @RequestParam String orderNumber,
            @ApiParam(name = "userName", value = "下单人") @RequestParam String userName,
            @ApiParam(name = "createTime", value = "开始时间") @RequestParam String createTime,
            @ApiParam(name = "endTime", value = "结束时间") @RequestParam String endTime,
            @ApiParam(name = "prodStatus", value = "'订单状态 1:待付款 2:进行中 3:配送中 5完成 6:取消 ") @RequestParam String prodStatus) {
        JeeUserDetails currentUser = getCurrentUser();
        JSONObject paramJSON = getReqParamJSON();
        return orderService.orderList(pageNumber,pageSize, paramJSON, currentUser);
    }

    @ApiOperation("订单详情")
    @GetMapping("/orderInfo")
    public ApiRes<OrderDTO> orderInfo(@RequestParam(value = "orderNumber") String orderNumber) {
        return orderService.orderInfo(orderNumber);
    }

    @ApiOperation("取消订单")
    @GetMapping("/recallOrder")
    public ApiRes recallOrder(@RequestParam(value = "orderNumber") String orderNumber) {
        return orderService.recallOrder(orderNumber);
    }

    @ApiOperation("填写配送信息")
    @PostMapping("/deliveryInfo")
    public ApiRes deliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) {
//        JSONObject paramJSON = getReqParamJSON();
        return orderService.deliveryInfo(deliveryInfoDTO);
    }

    @ApiOperation("确认收货")
    @GetMapping("/receipt")
    public ApiRes receipt(@RequestParam(value = "orderNumber") String orderNumber) {
        return orderService.receipt(orderNumber);
    }
}