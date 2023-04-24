package com.bm.fqservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户配送地址
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BUserAddr implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "addr_id", type = IdType.AUTO)
    private Long addrId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 省
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 区
     */
    private String area;

    /**
     * 区ID
     */
    private Long areaId;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 地址
     */
    private String addr;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 状态,1正常，0无效
     */
    private Integer state;

    /**
     * 是否默认地址 1是
     */
    private Integer commonAddr;

    /**
     * 建立时间
     */
    private Date createdAt;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 更新时间
     */
    private Date updatedAt;


    public static final LambdaQueryWrapper<BUserAddr> gw() {
        return new LambdaQueryWrapper<>();
    }

}
