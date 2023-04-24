package com.bm.fqmerchant.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthServiceMapper {
    Integer userHasLeftMenu(@Param("userId") Long userId, @Param("sysType") String sysType);

    List<String> selectEntIdsByUserId(@Param("userId") Long userId, @Param("sysType") String sysType);
}
