package com.bm.fquser.config;

import com.bm.fquser.secruity.SwaggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Swagger拦截器配置
 *
 * @author Caesar Liu
 * @date 2021/03/26 19:48
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private SwaggerInterceptor swaggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(swaggerInterceptor).addPathPatterns("/swagger-ui.html", "/doc.html");
    }
}