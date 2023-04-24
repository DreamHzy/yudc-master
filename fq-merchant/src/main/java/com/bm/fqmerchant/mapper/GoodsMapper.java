package com.bm.fqmerchant.mapper;

import com.bm.fqmerchant.model.vo.GoodListVO;
import com.bm.fqmerchant.model.vo.SkuDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    List<GoodListVO> goodListVOList(@Param("goodsCode") String goodsCode, @Param("goodsName")String goodsName,
                                    @Param("status")String status,@Param("shopId") String shopId,@Param("url") String url);

    List<SkuDetailDTO> skuList(@Param("prodId")Long prodId,@Param("url") String url);

    void updateByProId(Long prodId);
}
