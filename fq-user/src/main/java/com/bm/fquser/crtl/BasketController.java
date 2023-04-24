package com.bm.fquser.crtl;

import cn.hutool.core.map.MapUtil;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fquser.model.dto.ChangeShopCartDTO;
import com.bm.fquser.model.vo.BasketGoodsVO;
import com.bm.fquser.model.vo.ShopCartAmountDto;
import com.bm.fquser.service.BasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(tags = "购物车相关接口")
@RestController
@RequestMapping("/basket")
@Slf4j
public class BasketController extends CommonCtrl {

    @Resource
    BasketService basketService;


    @PostMapping("/changeItem")
    @ApiOperation(value = "添加、修改用户购物车物品", notes = "通过商品id(prodId)、skuId、店铺Id(shopId),添加/修改用户购物车商品，并传入改变的商品个数(count)，" +
            "当count为正值时，增加商品数量，当count为负值时，将减去商品的数量，当最终count值小于0时，会将商品从购物车里面删除")
    public ApiRes addItem(@RequestBody ChangeShopCartDTO param) {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return basketService.addItem(jeeUserDetails, param);
    }

    /**
     * 获取用户购物车信息
     *
     * @param
     * @return
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取用户购物车信息")
    public ApiRes<BasketGoodsVO> info() {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return basketService.info(jeeUserDetails);

    }

    @GetMapping("/deleteItem")
    @ApiOperation(value = "删除用户购物车物品")
    public ApiRes deleteItem(@RequestParam(value = "skuIds") List<String> skuIds) {
        return basketService.deleteItem(skuIds);
    }


    @GetMapping("/deleteAll")
    @ApiOperation(value = "清空用户购物车所有物品")
    public ApiRes deleteAll() {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return basketService.deleteAllShopCartItems(jeeUserDetails.getBUser().getUserId());
    }



    @GetMapping("/totalPay")
    @ApiOperation(value = "获取选中购物项总计、选中的商品数量")
    public ApiRes<ShopCartAmountDto> getTotalPay(@RequestParam(value = "skuIds") List<String> skuIds) {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return basketService.getTotalPay(jeeUserDetails,skuIds);
    }

}
