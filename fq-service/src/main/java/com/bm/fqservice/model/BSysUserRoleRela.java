package com.bm.fqservice.model;

import java.io.Serializable;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作员<->角色 关联表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BSysUserRoleRela implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private String roleId;

    public static final LambdaQueryWrapper<BSysUserRoleRela> gw(){
        return new LambdaQueryWrapper<>();
    }

}
