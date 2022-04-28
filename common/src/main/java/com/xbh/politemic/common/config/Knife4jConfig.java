package com.xbh.politemic.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

/**
 * @Knife4jConfig: knife4j 配置
 * @author: ZBoHang
 * @time: 2021/12/9 9:36
 */
@Slf4j
@Configuration
@EnableSwagger2
public class Knife4jConfig {

    /**
     * 配置knife4j
     */
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                //分组名称
                .groupName("1.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(basePackage("com.xbh.politemic"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 构建apiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("POLITEMIC RESTful APIs")
                .description("# POLITEMIC RESTful APIs")
                // .termsOfServiceUrl("http://www.xx.com/")
                .contact(new Contact("XBH", "https://gitee.com/zheng-bohang/politemic", "161078369@qq.com"))
                .version("1.0")
                .build();
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {

        return new Function<Class<?>, Boolean>() {
            @Override
            public Boolean apply(Class<?> input) {
                String packageName = ClassUtils.getPackageName(input);
                log.info("knife4j扫描 -->>> " + input.getSimpleName());
                return packageName.startsWith(basePackage);
            }
        };
        // return input -> ClassUtils.getPackageName(input).startsWith(basePackage);
    }

    public static Predicate<RequestHandler> basePackage(String basePackage) {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean test(RequestHandler input) {
                return declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
            }
        };
        // return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return ofNullable(input.declaringClass());
    }
}
