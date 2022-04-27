package com.xbh.politemic.config;

import com.xbh.politemic.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description: 自定义配置
 * @Author: zhengbohang
 * @Date: 2021/10/3 17:10
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    AuthInterceptor authInterceptor;
    
    /**
     * @description: 拦截器配置
     * @author: zhengbohang
     * @date: 2021/10/3 17:11
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**");
        // super.addInterceptors(registry);
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        // ctrl直接返回中文string乱码设置
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        addDefaultHttpMessageConverters(converters);
        // super.extendMessageConverters(converters);
    }
}
