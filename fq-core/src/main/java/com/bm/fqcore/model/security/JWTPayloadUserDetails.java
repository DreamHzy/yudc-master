package com.bm.fqcore.model.security;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class JWTPayloadUserDetails {
    /** 用户标识**/
    private Integer userId;

    /** 缓存标志 **/
    private String cacheKey;
}
