package com.bm.fqservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bm.fqservice.mapper.BUserMapper;
import com.bm.fqservice.model.BSmsLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bm.fqservice.model.BUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信记录表 服务类
 * </p>
 *
 * @author [mybatis plus generator]
 * @since 2022-05-27
 */
public interface IBSmsLogService extends IService<BSmsLog> {

    /**
     * <p>
     * 用户表 服务实现类
     * </p>
     *
     * @author [mybatis plus generator]
     * @since 2022-05-27
     */
    @Service
    class BUserService extends ServiceImpl<BUserMapper, BUser> implements IBUserService {

    }
}
