package com.bm.fqservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BProd implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 产品ID
     */
    @TableId(value = "prod_id", type = IdType.AUTO)
    private Long prodId;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 简要描述,卖点等
     */
    private String brief;

    /**
     * pc详情详细描述
     */
    private String pcContent;

    /**
     * 手机端详情
     */
    private String mobileContent;

    /**
     * 商品主图
     */
    private String pic;

    /**
     * 商品图片，以,分割
     */
    private String imgs;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架
     */
    private Integer status;

    /**
     * 商品分类
     */
    private Long categoryId;

    /**
     * 销量
     */
    private Integer soldNum;

    /**
     * 总库存
     */
    private Integer totalStocks;

    /**
     * 录入时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 上架时间
     */
    private Date putawayTime;

    /**
     * 版本 乐观锁
     */
    private Integer version;



    /**
     * sku列表
     */
    @TableField(exist = false)
    private List<BSku> skuList;


    public static final LambdaQueryWrapper<BProd> gw() {
        return new LambdaQueryWrapper<>();
    }

}
