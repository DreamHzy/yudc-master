package com.bm.fqservice.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BSysRole implements Serializable {

    //gw
    public static final LambdaQueryWrapper<BSysRole> gw(){
        return new LambdaQueryWrapper<>();
    }


    private static final long serialVersionUID=1L;

    /**
     * 角色ID, ROLE_开头
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 所属系统： MGR-运营平台, MCH-商户中心
     */
    private String sysType;

    /**
     * 所属商户ID / 0(平台)
     */
    private String belongInfoId;

    /**
     * 更新时间
     */
    private Date updatedAt;



}
