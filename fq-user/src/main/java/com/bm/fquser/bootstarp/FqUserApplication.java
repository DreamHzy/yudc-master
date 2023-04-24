package com.bm.fquser.bootstarp;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;


/**
 * spring-boot 主启动程序
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@SpringBootApplication
@EnableScheduling
@MapperScan({"com.bm.fquser.mapper.**", "com.bm.fqservice.mapper.**","ma.glasnost.orika"})  //Mybatis mapper接口路径
//@MapperScan({"com.bm.fquser.mapper.**", "com.bm.fqservice.mapper.**"})  //Mybatis mapper接口路径
@ComponentScan(basePackages = "com.bm.*")   //由于MainApplication没有在项目根目录， 需要配置basePackages属性使得成功扫描所有Spring组件；
@Configuration
public class FqUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(FqUserApplication.class, args);
    }


    /** fastJson 配置信息 **/
    @Bean
    public HttpMessageConverters fastJsonConfig(){

        //新建fast-json转换器
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        //fast-json 配置信息
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(config);

        //设置响应的 Content-Type
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8}));
        return new HttpMessageConverters(converter);
    }

    /** Mybatis plus 分页插件 **/
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        return paginationInterceptor;
    }


}
