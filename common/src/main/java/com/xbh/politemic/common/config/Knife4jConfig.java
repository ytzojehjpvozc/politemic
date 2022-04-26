package com.xbh.politemic.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Knife4jConfig: 接口文档 配置
 * @author: ZBoHang
 * @time: 2021/11/24 9:15
 */
@Configuration
//@EnableKnife4j
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                //分组名称
                .groupName("POLITEMIC-1.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.xbh.politemic.biz.user.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 构建 apiInfo
     * @author: ZBoHang
     * @time: 2021/12/8 15:30
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //.title("swagger-bootstrap-ui-demo RESTful APIs")
                .description("# Politemic RESTful APIs")
                .contact(new Contact("zhengbohang", "https://gitee.com/zheng-bohang/politemic", "161078369@qq.com"))
                .version("1.0")
                .build();
    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return (input) -> {
            return (Boolean)declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {

        return (input) -> {
            return ClassUtils.getPackageName(input).startsWith(basePackage);
        };
    }
}
