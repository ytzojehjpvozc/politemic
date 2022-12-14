package com.xbh.politemic.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xbh.politemic.interceptor.AuthInterceptor;
import com.xbh.politemic.interceptor.IdempotentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 自定义配置
 * @Author: zhengbohang
 * @Date: 2021/10/3 17:10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private IdempotentInterceptor idempotentInterceptor;

    /**
     * 添加类型转换器和格式化器
     * @author fxbin
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new DateFormatter());
    }

    /**
     * 跨域支持
     * @author fxbin
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }

    /**
     * 配置消息转换器-- 使用 fastjson
     * @author: ZBoHang
     * @time: 2021/12/15 15:17
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //5.将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);
    }

    /**
     * 拦截器配置
     * @author: ZBoHang
     * @time: 2021/12/9 8:38
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("doc.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html/**");
        registry.addInterceptor(this.idempotentInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("doc.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html/**");
        // WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
