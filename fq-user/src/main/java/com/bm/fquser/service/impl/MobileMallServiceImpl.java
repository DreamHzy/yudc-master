package com.bm.fquser.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bm.fqcomponentsoss.config.AliyunOssYmlConfig;
import com.bm.fqcore.comment.PageData;
import com.bm.fqcore.comment.PageWrap;
import com.bm.fqcore.constants.CS;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqservice.model.*;
import com.bm.fqservice.service.IBOrderItemService;
import com.bm.fqservice.service.impl.*;
import com.bm.fquser.mapper.MobileMallMapper;
import com.bm.fquser.model.dto.*;
import com.bm.fquser.model.vo.ConfirmOrdervVO;
import com.bm.fquser.model.vo.ProductVO;
import com.bm.fquser.model.vo.SkuDetailVO;
import com.bm.fquser.model.vo.SkuVO;
import com.bm.fquser.service.MobileMallService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileMallServiceImpl implements MobileMallService {

    @Resource
    MobileMallMapper mallMapper;

    @Resource
    AliyunOssYmlConfig aliyunOssYmlConfig;

    @Resource
    BUserAddrService bUserAddrService;
    @Resource
    BProdService bProdService;
    @Resource
    BSkuService bSkuService;
    @Resource
    BOrderService bOrderService;
    @Resource
    BOrderItemService bOrderItemService;
    @Resource
    BBasketService bBasketService;

    @Override
    public ApiRes<HomeDTO> home(PageWrap<String> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<HomeDTO> homeList = mallMapper.selectHome(pageWrap.getModel(), aliyunOssYmlConfig.getUrl());
        PageData<HomeDTO> from = PageData.from(new PageInfo<>(homeList));
        return ApiRes.ok(from);
    }

    @Override
    public ApiRes<ProductVO> details(String prodId) {
        BProd product = bProdService.getById(prodId);
        if (product == null) {
            return ApiRes.customFail("产品不存在");
        }
        product.setImgs(aliyunOssYmlConfig.getUrl() + product.getImgs());
        product.setPic(aliyunOssYmlConfig.getUrl() + product.getPic());
        List<BSku> skuList = bSkuService.list(BSku.gw().eq(BSku::getProdId, prodId).eq(BSku::getStatus, CS.YES));
        // 启用的sku列表
        List<BSku> useSkuList = skuList.stream().filter(sku -> sku.getActualStocks() > 0).collect(Collectors.toList());
        List<SkuVO> skuVOS = new ArrayList<>();

        useSkuList.stream().forEach(bSku -> {
            SkuVO skuVO = new SkuVO();
            skuVO.setPic(aliyunOssYmlConfig.getUrl() + bSku.getPic());
            skuVO.setSkuId(bSku.getSkuId());
            skuVO.setOriPrice(bSku.getPrice());
            skuVO.setPrice(bSku.getPrice());
            skuVO.setProperties(bSku.getProperties());
            skuVO.setStocks(bSku.getStocks());
            skuVOS.add(skuVO);
        });
        product.setSkuList(useSkuList);
        ProductVO productVO = new ProductVO();
        productVO.setSkuList(skuVOS);
        productVO.setPic(product.getPic());
        productVO.setContent(product.getMobileContent());
        productVO.setProdId(product.getProdId());
        productVO.setProdName(product.getProdName());
        return ApiRes.ok(productVO);
    }

    @Override
    public ApiRes<AddressDTO> shippingAddress(PageWrap<String> pageWrap, AppUserDetails jeeUserDetails) {
        BUser bUser = jeeUserDetails.getBUser();
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<AddressDTO> addressDTO = mallMapper.selectAddress(bUser.getUserId());
        PageData<AddressDTO> from = PageData.from(new PageInfo<>(addressDTO));
        return ApiRes.ok(from);
    }

    @Override
    public ApiRes maintainShippingAddress(MaintainDTO maintainDTO, AppUserDetails jeeUserDetails) {
        BUser bUser = jeeUserDetails.getBUser();
        if (maintainDTO.getCommonAddr() == 1) {
            mallMapper.update(bUser.getUserId());
//            if (!mallMapper.update(bUser.getUserId())) {
//                return ApiRes.customFail("修改失败");
//            }
        }
        BUserAddr bUserAddr = new BUserAddr();
        bUserAddr.setAddr(maintainDTO.getAddr());
        bUserAddr.setUserId(bUser.getUserId().toString());
        bUserAddr.setAddrId(maintainDTO.getAddrId());
        bUserAddr.setReceiver(maintainDTO.getReceiver());
        bUserAddr.setMobile(maintainDTO.getMobile());
        bUserAddr.setProvince(maintainDTO.getProvince());
        bUserAddr.setCity(maintainDTO.getCity());
        bUserAddr.setArea(maintainDTO.getArea());
        bUserAddr.setCommonAddr(maintainDTO.getCommonAddr());
        bUserAddr.setProvinceId(maintainDTO.getProvinceId());
        bUserAddr.setAreaId(maintainDTO.getAreaId());
        bUserAddr.setCityId(maintainDTO.getCityId());
        bUserAddr.setState(1);
        bUserAddr.setCreatedAt(new Date());
        bUserAddr.setUpdatedAt(new Date());
        if (!bUserAddrService.saveOrUpdate(bUserAddr)) {
            return ApiRes.customFail("修改失败");
        }
        return ApiRes.ok(HttpStatus.OK);
    }

    @Override
    public ApiRes<QueryShippingDTO> queryShippingAddress(Integer addrId) {
        BUserAddr byId = bUserAddrService.getById(addrId);
        if (byId == null) {
            return ApiRes.customFail("地址不存在");
        }
        QueryShippingDTO queryShippingDTO = new QueryShippingDTO();
        queryShippingDTO.setAddr(byId.getAddr());
        queryShippingDTO.setAddrId(byId.getAddrId().toString());
        queryShippingDTO.setReceiver(byId.getReceiver());
        queryShippingDTO.setMobile(byId.getMobile());
        queryShippingDTO.setCommonAddr(byId.getCommonAddr());
        queryShippingDTO.setProvince(byId.getProvince());
        queryShippingDTO.setCity(byId.getCity());
        queryShippingDTO.setArea(byId.getArea());
        queryShippingDTO.setProvinceId(byId.getProvinceId());
        queryShippingDTO.setCityId(byId.getCityId());
        queryShippingDTO.setAreaId(byId.getAreaId());
        return ApiRes.ok(queryShippingDTO);
    }

    @Override
    public ApiRes<OrderDTO> myOrder(PageWrap<List<String>> pageWrap, AppUserDetails jeeUserDetails) {
        BUser bUser = jeeUserDetails.getBUser();
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<OrderDTO> order = mallMapper.selectOrder(pageWrap.getModel(), bUser.getUserId());
        for (OrderDTO orderDTO : order) {
            List<OrderAndSkuDTO> andSkuDTOList = mallMapper.selectOrderAndSku(orderDTO.getOrderNumber(), aliyunOssYmlConfig.getUrl());
            orderDTO.setAndSkuDTOList(andSkuDTOList);
        }
        PageData<OrderDTO> from = PageData.from(new PageInfo<>(order));
        return ApiRes.ok(from);
    }

    @Override
    public ApiRes<WaitReceiptDTO> waitReceipt(String orderNumber) {
        BOrder order_number = bOrderService.getOne(new QueryWrapper<BOrder>().eq("order_number", orderNumber));
        if (order_number == null) {
            return ApiRes.customFail("订单不存在");
        }
        WaitReceiptDTO waitReceiptDTO = new WaitReceiptDTO();
        //订单商品信息
        List<OrderAndSkuDTO> andSkuDTOList = mallMapper.selectOrderAndSku(orderNumber, aliyunOssYmlConfig.getUrl());
        waitReceiptDTO.setAndSkuDTOList(andSkuDTOList);
        //配送地址信息
        WaitAddressDTO addressDTO = mallMapper.selectWait(orderNumber);
        waitReceiptDTO.setAddressDTO(addressDTO);
        return ApiRes.ok(waitReceiptDTO);
    }

    @Override
    public ApiRes confirmReceipt(String orderNumber) {
        BOrder order_number = bOrderService.getOne(new QueryWrapper<BOrder>().eq("order_number", orderNumber));
        if (order_number == null) {
            return ApiRes.customFail("订单不存在");
        }
        if (!mallMapper.updateStatus(orderNumber)) {
            return ApiRes.customFail("修改失败");
        }
        return ApiRes.ok(HttpStatus.OK);
    }

    @Override
    public ApiRes cancelOrder(String orderNumber) {
        BOrder order_number = bOrderService.getOne(new QueryWrapper<BOrder>().eq("order_number", orderNumber));
        if (order_number == null) {
            return ApiRes.customFail("订单不存在");
        }
        if (!mallMapper.updateOrder(orderNumber)) {
            return ApiRes.customFail("修改失败");
        }
        return ApiRes.ok(HttpStatus.OK);
    }

    @Override
    public ApiRes deleteAddress(long addrId) {
        BUserAddr byId = bUserAddrService.getById(addrId);
        if (byId == null) {
            return ApiRes.customFail("地址不存在");
        }
        if (!mallMapper.updateAddress(addrId)) {
            return ApiRes.customFail("修改失败");
        }
        return ApiRes.ok(HttpStatus.OK);
    }

    @Override
    public ApiRes money(String skuId, String num) {
        BSku bSku = bSkuService.getById(skuId);
        BigDecimal money = bSku.getPrice().multiply(new BigDecimal(num));
        return ApiRes.ok(money);
    }


    @Override
    public ApiRes<ConfirmOrdervVO> confirmOrder(AppUserDetails jeeUserDetails, List<ConfirmOrderDTO> confirmOrderDTOList) {
        BUser bUser = jeeUserDetails.getBUser();
        List<SkuDetailVO> skuDetailVOS = new ArrayList<>();
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (ConfirmOrderDTO confirmOrderDTO : confirmOrderDTOList) {
            BSku bSku = bSkuService.getOne(BSku.gw().eq(BSku::getSkuId, confirmOrderDTO.getSkuId()).eq(BSku::getStatus, CS.YES).eq(BSku::getIsDelete, CS.NO));
            if (bSku == null) {
                return ApiRes.customFail("商品已下架");
            }
            if (bSku.getActualStocks() < Integer.valueOf(confirmOrderDTO.getNum())) {
                return ApiRes.customFail("库存不足");
            }
            //查询产品信息
            BProd bProd = bProdService.getById(bSku.getProdId());
            if (bProd == null) {
                return ApiRes.customFail("商品已下架");
            }
            SkuDetailVO skuDetailVO = new SkuDetailVO();
            skuDetailVO.setSkuId(confirmOrderDTO.getSkuId());
            skuDetailVO.setPrdName(bProd.getProdName());
            skuDetailVO.setNum(confirmOrderDTO.getNum());
            skuDetailVO.setPrdMoney(bSku.getPrice() + "");
            skuDetailVO.setPic(aliyunOssYmlConfig.getUrl() + bProd.getPic());
            skuDetailVO.setProperties(bSku.getProperties());
            skuDetailVOS.add(skuDetailVO);
            totalMoney = totalMoney.add(bSku.getPrice().multiply(new BigDecimal(confirmOrderDTO.getNum())));
        }
        ConfirmOrdervVO confirmOrdervVO = new ConfirmOrdervVO();
        //查询用户收货地址，先取默认，如果默认无则取第一条，如果都无则返回空
        BUserAddr bUserAddr = null;
        Integer addrCount = bUserAddrService.count(BUserAddr.gw().eq(BUserAddr::getUserId, bUser.getUserId()).eq(BUserAddr::getState, CS.YES));
        if (addrCount > 0) {
            bUserAddr = bUserAddrService.getOne(BUserAddr.gw().eq(BUserAddr::getUserId, bUser.getUserId()).eq(BUserAddr::getCommonAddr, CS.YES).eq(BUserAddr::getState, CS.YES));
            if (bUserAddr == null) {
                bUserAddr = bUserAddrService.list(BUserAddr.gw().eq(BUserAddr::getUserId, bUser.getUserId()).eq(BUserAddr::getState, CS.YES)).get(0);
            }
            confirmOrdervVO.setAddr(bUserAddr.getAddr());
            confirmOrdervVO.setAddrId(bUserAddr.getAddrId() + "");
            confirmOrdervVO.setReceiver(bUserAddr.getReceiver());
            confirmOrdervVO.setMobile(bUserAddr.getMobile());
        } else {
            confirmOrdervVO.setAddrId("0");
        }
        confirmOrdervVO.setSkuDetailVOS(skuDetailVOS);
        confirmOrdervVO.setBottoMmoney(totalMoney + "");
        return ApiRes.ok(confirmOrdervVO);
    }

    @Override
    public ApiRes pay(AppUserDetails jeeUserDetails, PayDTO payDTO) {
        List<ConfirmOrderDTO> confirmOrderDTOList = payDTO.getConfirmOrderDTOList();
        String addrId = payDTO.getAddrId();
        BUser bUser = jeeUserDetails.getBUser();
        //创建订单
        BOrder bOrder = new BOrder();
        BigDecimal total = BigDecimal.ZERO;
        List<BOrderItem> bOrderItemList = new ArrayList<>();
        List<BSku> bSkuList = new ArrayList<>();
        for (ConfirmOrderDTO confirmOrderDTO : confirmOrderDTOList) {
            BSku bSku = bSkuService.getOne(BSku.gw().eq(BSku::getSkuId, confirmOrderDTO.getSkuId()).eq(BSku::getStatus, CS.YES).eq(BSku::getIsDelete, CS.NO));
            if (bSku == null) {
                return ApiRes.customFail("商品已下架");
            }
            if (bSku.getActualStocks() < Integer.valueOf(confirmOrderDTO.getNum())) {
                return ApiRes.customFail("库存不足");
            }
            //查询产品信息
            BProd bProd = bProdService.getById(bSku.getProdId());
            if (bProd == null) {
                return ApiRes.customFail("商品已下架");
            }
            BigDecimal money = bSku.getPrice().multiply(new BigDecimal(confirmOrderDTO.getNum()));
            if (bProd.getProdName() == null) {
                bOrder.setProdName(bProd.getProdName());
            } else {
                bOrder.setProdName(bProd.getProdName() + "," + bProd.getProdName());
            }

            //先默认支付成功
            BOrderItem bOrderItem = new BOrderItem();
            bOrderItem.setShopId(bProd.getShopId());
            bOrderItem.setOrderNumber(bOrder.getOrderNumber());
            bOrderItem.setProdId(bProd.getProdId());
            bOrderItem.setSkuId(bSku.getSkuId());
            bOrderItem.setProdCount(Integer.valueOf(confirmOrderDTO.getNum()));
            bOrderItem.setProdName(bProd.getProdName());
            bOrderItem.setSkuName(bSku.getSkuName());
            bOrderItem.setPic(bSku.getPic());
            bOrderItem.setUserId(bUser.getUserId() + "");
            bOrderItem.setProductTotalAmount(money);
            bOrderItem.setOrderNumber(bOrder.getOrderNumber());
            bOrderItem.setRecTime(new Date());
            bOrderItemList.add(bOrderItem);
            bSku.setActualStocks(bSku.getActualStocks() - bOrderItem.getProdCount());
            total = total.add(bSku.getPrice().multiply(new BigDecimal(confirmOrderDTO.getNum())));
        }
        bOrder.setShopId(1L);
        bOrder.setUserId(bUser.getUserId() + "");
        bOrder.setOrderNumber("OD" + DateUtil.currentSeconds());
        bOrder.setTotal(total);
        bOrder.setActualTotal(total);
        bOrder.setPayType(3);
        bOrder.setStatus(2);
        bOrder.setAddrOrderId(Long.valueOf(addrId));
        bOrder.setCreatedAt(new Date());
        bOrder.setPayTime(new Date());
        bOrder.setIsPayed(CS.SEX_MALE);
        bOrder.setDeleteStatus(CS.PUB_DISABLE);
        bOrder.setRefundSts(2);
        bOrderService.save(bOrder);
        bOrderItemService.saveBatch(bOrderItemList);
        bSkuService.updateBatchById(bSkuList);
        //清空对应的购物车
//        mallMapper.removeBasket(bSkuList);


        return ApiRes.ok();

    }
}