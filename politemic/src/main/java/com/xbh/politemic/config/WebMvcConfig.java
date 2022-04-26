package com.xbh.politemic.config;

import com.xbh.politemic.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 自定义配置
 * @Author: zhengbohang
 * @Date: 2021/10/3 17:10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    /**
     * 拦截器配置
     * @author: zhengbohang
     * @date: 2021/12/8 19:59
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/doc.html", "/swagger**/**");
        // WebMvcConfigurer.super.addInterceptors(registry);
    }

    /**
     * 静态资源处理
     * @author: zhengbohang
     * @date: 2021/12/8 19:59
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
