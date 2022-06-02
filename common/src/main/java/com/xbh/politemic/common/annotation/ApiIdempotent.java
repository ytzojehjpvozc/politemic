package com.xbh.politemic.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ApiIdempotent: 接口幂等性校验 注解 放在要求登录的接口上面
 * @author: ZBoHang
 * @time: 2022/1/4 17:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {
}
