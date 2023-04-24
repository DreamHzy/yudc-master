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
 * 短信记录表
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BSmsLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 手机号码
     */
    private String userPhone;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 手机验证码
     */
    private String mobileCode;

    /**
     * 短信类型  1:注册  2:验证
     */
    private Integer type;

    /**
     * 发送时间
     */
    private Date recDate;

    /**
     * 发送短信返回码
     */
    private String responseCode;

    /**
     * 状态  1:有效  0：失效
     */
    private Byte state;


}
