package com.xbh.politemic.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 对controlelr上面的方法注释 收集日志
 * @Author: zhengbohang
 * @Date: 2021/10/3 15:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {
    /**
     * @description: 模块名
     * @author: zhengbohang
     * @date: 2021/10/3 15:13
     */
    String modelName() default "unknow";
    /**
     * @description: 功能
     * @author: zhengbohang
     * @date: 2021/10/3 15:14
     */
    String behavior() default "unknow";
    /**
     * @description: 备注
     * @author: zhengbohang
     * @date: 2021/10/3 15:14
     */
    String remark() default "";
}
