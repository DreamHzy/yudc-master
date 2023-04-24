package com.bm.fqservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BArea implements Serializable {


    //gw
    public static final LambdaQueryWrapper<BArea> gw(){
        return new LambdaQueryWrapper<>();
    }


    private static final long serialVersionUID=1L;

    @TableId(value = "area_id", type = IdType.AUTO)
    private Long areaId;

    private String areaName;

    private Long parentId;

    private Integer level;


}
