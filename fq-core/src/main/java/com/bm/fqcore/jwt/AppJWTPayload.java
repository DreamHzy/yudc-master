package com.bm.fqcore.jwt;

import com.alibaba.fastjson.JSONObject;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqcore.model.security.JWTPayloadUserDetails;
import lombok.Data;

import java.util.Map;

@Data
public class AppJWTPayload {


    private Long sysUserId;       //登录用户ID
    private Long created;         //创建时间, 格式：13位时间戳
    private String cacheKey;      //redis保存的key

    protected AppJWTPayload() {
    }

    public AppJWTPayload(AppUserDetails userDetails) {
        this.setSysUserId(userDetails.getBUser().getUserId());
        this.setCreated(System.currentTimeMillis());
        this.setCacheKey(userDetails.getCacheKey());
    }

    /**
     * toMap
     **/
    public Map<String, Object> toMap() {
        JSONObject json = (JSONObject) JSONObject.toJSON(this);
        return json.toJavaObject(Map.class);
    }
}
