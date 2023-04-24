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
 * 品牌表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BBrand implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "brand_id", type = IdType.AUTO)
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 图片路径
     */
    private String brandPic;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 备注
     */
    private String memo;

    /**
     * 顺序
     */
    private Integer seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    private Byte state;

    /**
     * 简要描述
     */
    private String brief;

    /**
     * 内容
     */
    private String content;

    /**
     * 记录时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 品牌首字母
     */
    private String firstChar;


}
