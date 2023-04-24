package com.bm.fqservice.model;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统角色权限关联表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("b_sys_role_ent_rela")
public class BSysRoleEntRela implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 权限ID
     */
    private String entId;


    //gw
    public static final LambdaQueryWrapper<BSysRoleEntRela> gw(){
        return new LambdaQueryWrapper<>();
    }
}
