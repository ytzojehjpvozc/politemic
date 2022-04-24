package com.xbh.politemic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 自定义配置
 * @Author: zhengbohang
 * @Date: 2021/10/3 17:10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // @Autowired
    // AuthInterceptor authInterceptor;
    //
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //
    //     registry.addInterceptor(authInterceptor)
    //             .addPathPatterns("/**")
    //             .excludePathPatterns("/swgger**/**");
    //     // WebMvcConfigurer.super.addInterceptors(registry);
    // }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    // /**
    //  * @description: 拦截器配置
    //  * @author: zhengbohang
    //  * @date: 2021/10/3 17:11
    //  */
    // @Override
    // protected void addInterceptors(InterceptorRegistry registry) {
    //     // registry.addInterceptor(authInterceptor)
    //     //         .addPathPatterns("/**")
    //     //         .excludePathPatterns("/swagger-resources/**",
    //     //                 "/webjars/**",
    //     //                 "/v2/**",
    //     //                 "/swagger-ui.html/**",
    //     //                 "/doc.html/**"
    //     //         );
    //     super.addInterceptors(registry);
    // }
    //
    // @Override
    // protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    //
    //     // ctrl直接返回中文string乱码设置
    //     converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    //     addDefaultHttpMessageConverters(converters);
    //     // super.extendMessageConverters(converters);
    // }
    //
    // @Override
    // protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    //     registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    //     // super.addResourceHandlers(registry);
    // }
}
