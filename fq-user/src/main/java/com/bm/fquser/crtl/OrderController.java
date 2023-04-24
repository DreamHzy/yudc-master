package com.bm.fquser.crtl;

import com.bm.fqcore.comment.PageWrap;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fquser.model.dto.ConfirmOrderDTO;
import com.bm.fquser.model.dto.OrderDTO;
import com.bm.fquser.model.dto.PayDTO;
import com.bm.fquser.model.dto.WaitReceiptDTO;
import com.bm.fquser.model.vo.ConfirmOrdervVO;
import com.bm.fquser.service.MobileMallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "我的订单相关接口")
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController extends CommonCtrl {

    @Resource
    MobileMallService mallService;

    @ApiOperation("我的订单")
    @PostMapping("/myOrder")
    public ApiRes<OrderDTO> myOrder(@RequestBody PageWrap<List<String>> pageWrap) {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return mallService.myOrder(pageWrap, jeeUserDetails);
    }

    @ApiOperation("订单详情-待收货页面")
    @GetMapping("/waitReceipt")
    public ApiRes<WaitReceiptDTO> waitReceipt(@RequestParam(value = "orderNumber") String orderNumber) {
        return mallService.waitReceipt(orderNumber);
    }

    @ApiOperation("订单详情-确认收货")
    @GetMapping("/confirmReceipt")
    public ApiRes confirmReceipt(@RequestParam(value = "orderNumber") String orderNumber) {
        return mallService.confirmReceipt(orderNumber);
    }

    @ApiOperation("订单详情-取消订单")
    @GetMapping("/cancelOrder")
    public ApiRes cancelOrder(@RequestParam(value = "orderNumber") String orderNumber) {
        return mallService.cancelOrder(orderNumber);
    }

    /**
     * 计算金额
     */
    @ApiOperation("计算金额")
    @GetMapping("/money")
    public ApiRes money(
            @ApiParam(name = "skuId", value = "sku", required = true) @NonNull @RequestParam("skuId") String skuId,
            @ApiParam(name = "phone", value = "数量", required = true) @NonNull @RequestParam("num") String num
    ) {
        return mallService.money(skuId, num);
    }

    @ApiOperation("确认订单页")
    @PostMapping("/confirmOrder")
    public ApiRes<ConfirmOrdervVO> confirmOrder(@RequestBody List<ConfirmOrderDTO> confirmOrderDTOList) {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return mallService.confirmOrder(jeeUserDetails, confirmOrderDTOList);
    }

    /**
     * 下单
     */
    @ApiOperation("下单")
    @PostMapping("/pay")
    public ApiRes pay(
            @RequestBody PayDTO payDTO
    ) {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return mallService.pay(jeeUserDetails, payDTO);
    }

}
