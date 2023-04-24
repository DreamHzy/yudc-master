package com.bm.fqmerchant.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fqmerchant.model.dto.ProductAddDTO;
import com.bm.fqmerchant.model.dto.ProductEditDTO;
import com.bm.fqmerchant.model.vo.GoodDetailVO;
import com.bm.fqmerchant.model.vo.GoodListVO;
import com.bm.fqmerchant.model.vo.OrderListVO;
import com.bm.fqmerchant.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author terrfly
 * @modify zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@Api(tags = "商品相关列表")
@RestController
@RequestMapping("api/goods")
public class GoodsController extends CommonCtrl {


    @Resource
    GoodsService goodsService;

    @ApiOperation("商品列表")
    @GetMapping("/goodList")
    public ApiRes<GoodListVO> goodList(
            @ApiParam(name = "pageNumber", value = "页数", required = true) @RequestParam String pageNumber,
            @ApiParam(name = "pageSize", value = "条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "goodsCode", value = "商品编码") @RequestParam String goodsCode,
            @ApiParam(name = "goodsName", value = "商品名称") @RequestParam String goodsName,
            @ApiParam(name = "status", value = "'0 下降 1上架") @RequestParam String status) {
        JeeUserDetails currentUser = getCurrentUser();
        JSONObject paramJSON = getReqParamJSON();
        return goodsService.goodList(getIPage(), paramJSON, currentUser);
    }


    /**
     * 商品上架下架功能
     */
    @ApiOperation("商品上架下架功能")
    @GetMapping("/goodStatus")
    public ApiRes goodStatus(
            @ApiParam(name = "goodsCode", value = "商品编码") @RequestParam String goodsCode,
            @ApiParam(name = "status", value = "'0 下降 1上架") @RequestParam String status) {
        return goodsService.goodStatus(goodsCode, status);
    }

    /**
     * 商品详情
     */
    @ApiOperation("商品详情")
    @GetMapping("/goodDetail")
    public ApiRes<GoodDetailVO> goodDetail(
            @ApiParam(name = "goodsCode", value = "商品编码", required = true) @RequestParam String goodsCode)
    {
        return goodsService.goodDetail(goodsCode);
    }

    /**
     * 新增
     */
    @ApiOperation("新增商品")
    @PostMapping("/goodsAdd")
//    @PreAuthorize("hasAuthority( 'ENT_GOODS_ADD' )")
    public ApiRes goodsAdd(@RequestBody ProductAddDTO productAddDTO) {
        JeeUserDetails currentUser = getCurrentUser();
        return goodsService.goodsAdd(productAddDTO, currentUser);
    }


    @ApiOperation("编辑商品")
    @PostMapping("/goodsEdit")
//    @PreAuthorize("hasAuthority( 'ENT_GOODS_EDIT' )")
    public ApiRes goodsEdit(@RequestBody ProductEditDTO productEditDTO) {
        JeeUserDetails currentUser = getCurrentUser();
        return goodsService.goodsEdit(productEditDTO, currentUser);
    }


}
