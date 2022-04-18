package com.xbh.politemic.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @NoneNeedLogin: 用于控制层的方法上面, 表示该方法不需要登录(权限验证)
 * @author: ZBoHang
 * @time: 2021/10/9 13:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NoneNeedLogin {
}
