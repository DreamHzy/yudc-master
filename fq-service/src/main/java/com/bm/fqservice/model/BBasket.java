package com.bm.fqservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BBasket implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "basket_id", type = IdType.AUTO)
    private Long basketId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 产品ID
     */
    private Long prodId;

    /**
     * SkuID
     */
    private Long skuId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 购物车产品个数
     */
    private Integer basketCount;

    /**
     * 加入购物车时间
     */
    private Date basketDate;
    public BBasket(){

    }

    public BBasket(Long shopId, Long prodId, String userId, Long skuId, Integer count, Date date) {
        this.setShopId(shopId);
        this.setProdId(prodId);
        this.setUserId(userId);
        this.setSkuId(skuId);
        this.setBasketCount(count);
        this.setBasketDate(date);
    }


    //gw
    public static final LambdaQueryWrapper<BBasket> gw() {
        return new LambdaQueryWrapper<>();
    }
}
