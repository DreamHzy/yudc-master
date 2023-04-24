package com.bm.fquser.service.impl;

import com.bm.fqcomponentsoss.config.AliyunOssYmlConfig;
import com.bm.fqcore.constants.CS;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqservice.model.BBasket;
import com.bm.fqservice.model.BProd;
import com.bm.fqservice.model.BSku;
import com.bm.fqservice.model.BUser;
import com.bm.fqservice.service.impl.BBasketService;
import com.bm.fqservice.service.impl.BOrderService;
import com.bm.fqservice.service.impl.BProdService;
import com.bm.fqservice.service.impl.BSkuService;
import com.bm.fquser.mapper.BasketMapper;
import com.bm.fquser.model.dto.ChangeShopCartDTO;
import com.bm.fquser.model.vo.BasketGoodsVO;
import com.bm.fquser.model.vo.ShopCartAmountDto;
import com.bm.fquser.service.BasketService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {
    @Resource
    BBasketService bBasketService;

    @Resource
    BSkuService bSkuService;
    @Resource
    BProdService bProdService;
    @Resource
    BasketMapper basketMapper;
    @Resource
    AliyunOssYmlConfig aliyunOssYmlConfig;

    @Override
    public ApiRes addItem(AppUserDetails jeeUserDetails, ChangeShopCartDTO param) {
        BUser bUser = jeeUserDetails.getBUser();
        String prodId = param.getProdId();
        if (StringUtils.isEmpty(prodId)) {
            return ApiRes.customFail("产品参数不对");
        }
        String skuId = param.getSkuId();
        if (StringUtils.isEmpty(skuId)) {
            return ApiRes.customFail("skuId参数不对");
        }
        Integer count = param.getCount();

        if (count == 0) {
            return ApiRes.customFail("输入更改数量");
        }
        String userId = bUser.getUserId() + "";
        //查询产品是否上架状态
        BProd bProd = bProdService.getOne(BProd.gw().eq(BProd::getProdId, prodId).eq(BProd::getStatus, CS.YES));
        if (bProd == null) {
            return ApiRes.customFail("商品已下架");
        }
        BSku bSku = bSkuService.getOne(BSku.gw().eq(BSku::getSkuId, skuId).eq(BSku::getStatus, CS.YES).eq(BSku::getIsDelete, CS.NO));
        if (bSku == null) {
            return ApiRes.customFail("商品已下架");
        }
        //查询购物车信息
        BBasket bBasket = bBasketService.getOne(BBasket.gw().eq(BBasket::getProdId, bProd.getProdId()).
                        eq(BBasket::getSkuId, bSku.getSkuId()).
                        eq(BBasket::getUserId, userId));

        if (bBasket == null) {//说明这个商品前面没在购物车里面
            if (count < 0) {//从购物车删除商品
                return ApiRes.customFail("购物车不存在改商品");
            }
            //查出库存
            if (bSku.getActualStocks() < count) {
                return ApiRes.customFail("库存不足");
            }
            bBasket = new BBasket(bProd.getShopId(), bProd.getProdId(), userId, bSku.getSkuId(), count, new Date());
            bBasketService.save(bBasket);
        } else {
            if (count < 0) {//移除购物车
                bBasketService.removeById(bBasket.getBasketId());
            }
            //查出库存
            if (bSku.getActualStocks() < count) {
                return ApiRes.customFail("库存不足");
            }
            bBasket.setBasketCount(count);
            bBasketService.updateById(bBasket);
        }
        return ApiRes.ok();
    }

    @Override
    public ApiRes<BasketGoodsVO> info(AppUserDetails jeeUserDetails) {
        BUser bUser = jeeUserDetails.getBUser();
        //查询用户购物车列表
        List<BasketGoodsVO> basketGoodsVOS = basketMapper.basketGoodsVOS(bUser.getUserId(), aliyunOssYmlConfig.getUrl());
        return ApiRes.ok(basketGoodsVOS);
    }

    @Override
    public ApiRes deleteItem(List<String> skuIds) {
        skuIds.stream().forEach(
                s -> {
                    bBasketService.remove(BBasket.gw().eq(BBasket::getSkuId, s));
                }
        );
        return ApiRes.ok();
    }

    @Override
    public ApiRes deleteAllShopCartItems(Long userId) {
        bBasketService.remove(BBasket.gw().eq(BBasket::getUserId, userId));
        return ApiRes.ok();
    }

    @Override
    public ApiRes<ShopCartAmountDto> getTotalPay(AppUserDetails appUserDetails,List<String> skuIds) {
        ShopCartAmountDto shopCartAmountDto = null;
        if (skuIds.size() == 0) {
            shopCartAmountDto = new ShopCartAmountDto();

            shopCartAmountDto.setCount(0);
            shopCartAmountDto.setFinalMoney(new Double(0));
        } else {
            shopCartAmountDto = basketMapper.getTotalPay(skuIds,appUserDetails.getBUser().getUserId());

        }
        return ApiRes.ok(shopCartAmountDto);
    }
}
