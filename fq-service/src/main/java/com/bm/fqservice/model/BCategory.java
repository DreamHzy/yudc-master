package com.bm.fqservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 产品类目
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BCategory implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 类目ID
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 产品类目名称
     */
    private String categoryName;

    /**
     * 类目图标
     */
    private String icon;

    /**
     * 类目的显示图片
     */
    private String pic;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    private Byte state;

    /**
     * 记录时间
     */
    private Date createdAt;

    /**
     * 分类层级
     */
    private Integer grade;

    /**
     * 更新时间
     */
    private Date updatedAt;


}
