package com.bm.fqmerchant.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bm.fqcomponentsoss.config.AliyunOssYmlConfig;
import com.bm.fqcore.comment.PageData;
import com.bm.fqcore.constants.CS;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fqmerchant.mapper.GoodsMapper;
import com.bm.fqmerchant.model.dto.ProductAddDTO;
import com.bm.fqmerchant.model.dto.ProductEditDTO;
import com.bm.fqmerchant.model.dto.SkuDTO;
import com.bm.fqmerchant.model.dto.SkuEditDTO;
import com.bm.fqmerchant.model.vo.GoodDetailVO;
import com.bm.fqmerchant.model.vo.GoodListVO;
import com.bm.fqmerchant.model.vo.SkuDetailDTO;
import com.bm.fqmerchant.service.GoodsService;
import com.bm.fqservice.mapper.BShopDetailMapper;
import com.bm.fqservice.model.BProd;
import com.bm.fqservice.model.BShopDetail;
import com.bm.fqservice.model.BSku;
import com.bm.fqservice.model.BSysUser;
import com.bm.fqservice.service.IBProdService;
import com.bm.fqservice.service.IBShopDetailService;
import com.bm.fqservice.service.IBSkuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    IBProdService ibProdService;
    @Resource
    IBShopDetailService ibShopDetailService;
    @Resource
    IBSkuService ibSkuService;
    @Resource
    BShopDetailMapper bShopDetailMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    AliyunOssYmlConfig aliyunOssYmlConfig;

    @Override
    public ApiRes goodsAdd(ProductAddDTO productAddDTO, JeeUserDetails currentUser) {
        String prodName = productAddDTO.getProdName();
        if (StringUtils.isEmpty(prodName)) {
            return ApiRes.customFail("请输入产品名称");
        }
        String pic = productAddDTO.getPic();
        if (StringUtils.isEmpty(pic)) {
            return ApiRes.customFail("请上传图片");
        }
        String content = productAddDTO.getContent();
        if (StringUtils.isEmpty(content)) {
            return ApiRes.customFail("请输入详情");
        }
        BSysUser sysUser = currentUser.getSysUser();
        BShopDetail bShopDetail = ibShopDetailService.getOne(new QueryWrapper<BShopDetail>().eq("mch_no", sysUser.getBelongInfoId()));
        //添加商品
        BProd bProd = new BProd();
        bProd.setProdName(prodName);
        bProd.setImgs(pic);
        bProd.setPic(pic);
        bProd.setMobileContent(content);
        bProd.setPcContent(content);
        bProd.setShopId(bShopDetail.getShopId());
        bProd.setStatus(CS.PUB_USABLE);
        bProd.setCreatedAt(new Date());
        bProd.setPutawayTime(new Date());
        bProd.setTotalStocks(CS.PUB_DISABLE);
        bProd.setVersion(CS.PUB_DISABLE);
        ibProdService.save(bProd);


        List<SkuDTO> skuList = productAddDTO.getSkuList();
        List<BSku> bSkuList = new ArrayList<>();
        skuList.stream().forEach(
                skuDTO -> {
                    BSku bSku = new BSku();
                    bSku.setPic(bSku.getPic());
                    bSku.setProdId(bProd.getProdId());
                    bSku.setProperties(skuDTO.getProperties());
                    bSku.setCostPrice(new BigDecimal(skuDTO.getPrice()));
                    bSku.setPrice(new BigDecimal(skuDTO.getPrice()));
                    bSku.setStocks(CS.PUB_DISABLE);
                    bSku.setActualStocks(Integer.valueOf(skuDTO.getStocks()));
                    bSku.setRecTime(new Date());
                    bSku.setPartyCode("SKU" + DateUtil.currentSeconds());
                    bSku.setPic(skuDTO.getPic());
                    bSku.setProdName(bProd.getProdName());
                    bSku.setVersion(CS.PUB_DISABLE);
                    bSku.setIsDelete(CS.NO);
                    bSku.setStatus(Byte.valueOf(skuDTO.getStatus()));
                    bProd.setTotalStocks(bProd.getTotalStocks() + Integer.valueOf(skuDTO.getStocks()));
                    bSkuList.add(bSku);
                }
        );
        ibSkuService.saveBatch(bSkuList);
        ibProdService.updateById(bProd);
        return ApiRes.ok();
    }

    @Override
    public ApiRes goodsEdit(ProductEditDTO productEditDTO, JeeUserDetails currentUser) {
        String prodName = productEditDTO.getProdName();
        if (StringUtils.isEmpty(prodName)) {
            return ApiRes.customFail("请输入产品名称");
        }
        String pic = productEditDTO.getPic();
        if (StringUtils.isEmpty(pic)) {
            return ApiRes.customFail("请上传图片");
        }
        String content = productEditDTO.getContent();
        if (StringUtils.isEmpty(content)) {
            return ApiRes.customFail("请输入详情");
        }
        String id = productEditDTO.getGoodCode();
        BProd bProd = ibProdService.getById(id);
        if (bProd == null) {
            return ApiRes.customFail("商品不存在");
        }
        bProd.setProdName(prodName);
        bProd.setPic(pic);
        bProd.setImgs(pic);
        bProd.setMobileContent(content);
        bProd.setPcContent(content);
        bProd.setUpdatedAt(new Date());

        //先将原来的sku变成删除
        goodsMapper.updateByProId(bProd.getProdId());
        ibProdService.updateById(bProd);

        List<SkuEditDTO> skuList = productEditDTO.getSkuList();
        List<BSku> bSkuListUpdate = new ArrayList<>();
        List<BSku> bSkuList = new ArrayList<>();
        skuList.stream().forEach(
                skuDTO -> {
                    BSku bSku = new BSku();
                    bSku.setPic(bSku.getPic());
                    bSku.setProdId(bProd.getProdId());
                    bSku.setProperties(skuDTO.getProperties());
                    bSku.setCostPrice(new BigDecimal(skuDTO.getPrice()));
                    bSku.setPrice(new BigDecimal(skuDTO.getPrice()));
                    bSku.setStocks(CS.PUB_DISABLE);
                    bSku.setActualStocks(Integer.valueOf(skuDTO.getStocks()));
                    bSku.setRecTime(new Date());
                    bSku.setPic(skuDTO.getPic());
                    bSku.setProdName(bProd.getProdName());
                    bSku.setVersion(CS.PUB_DISABLE);
                    bSku.setIsDelete(CS.NO);
                    bSku.setStatus(Byte.valueOf(skuDTO.getStatus()));
                    bSku.setPartyCode("SKU" + DateUtil.currentSeconds());
                    bSkuList.add(bSku);
                    bProd.setTotalStocks(bProd.getTotalStocks() + Integer.valueOf(skuDTO.getStocks()));
                }
        );
        if (bSkuList.size() > 0) {
            ibSkuService.saveBatch(bSkuList);
        }
        if (bSkuListUpdate.size() > 0) {
            ibSkuService.updateBatchById(bSkuListUpdate);
        }
        return ApiRes.ok();
    }

    @Override
    public ApiRes<GoodListVO> goodList(IPage iPage, JSONObject paramJSON, JeeUserDetails currentUser) {
        BSysUser sysUser = currentUser.getSysUser();
        BShopDetail bShopDetail = bShopDetailMapper.selectOne(new QueryWrapper<BShopDetail>().eq("mch_no", sysUser.getBelongInfoId()));
        List<GoodListVO> goodListVOList = new ArrayList<>();
        String goodsCode = paramJSON.getString("goodsCode");
        String goodsName = paramJSON.getString("goodsName");
        String status = paramJSON.getString("status");
        PageHelper.startPage((int) iPage.getPages(), (int) iPage.getSize());
        if (bShopDetail != null) {
            goodListVOList = goodsMapper.goodListVOList(goodsCode, goodsName, status, bShopDetail.getShopId() + "", aliyunOssYmlConfig.getUrl());
        }
        PageData<GoodListVO> from = PageData.from(new PageInfo<>(goodListVOList));
        return ApiRes.ok(from);
    }

    @Override
    public ApiRes goodStatus(String goodsCode, String status) {
        BProd bProd = ibProdService.getById(goodsCode);
        if (bProd == null) {
            return ApiRes.customFail("商品不存在");
        }
        bProd.setStatus(Integer.valueOf(status));
        ibProdService.updateById(bProd);
        return ApiRes.ok();
    }

    @Override
    public ApiRes<GoodDetailVO> goodDetail(String goodsCode) {

        BProd bProd = ibProdService.getById(goodsCode);
        if (bProd == null) {
            return ApiRes.customFail("商品不存在");
        }
        GoodDetailVO goodListVO = new GoodDetailVO();
        goodListVO.setGoodCode(bProd.getProdId() + "");
        goodListVO.setOssUrl(aliyunOssYmlConfig.getUrl());
        goodListVO.setProdName(bProd.getProdName());
        goodListVO.setContent(bProd.getPcContent());
        goodListVO.setPic(aliyunOssYmlConfig.getUrl() + bProd.getPic());
        List<SkuDetailDTO> skuList = goodsMapper.skuList(bProd.getProdId(),aliyunOssYmlConfig.getUrl());
        goodListVO.setSkuList(skuList);
        return ApiRes.ok(goodListVO);
    }
}
