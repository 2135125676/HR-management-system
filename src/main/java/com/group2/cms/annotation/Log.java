package com.group2.cms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 只有使用该注解的方法才记录日志信息
 */
@Target(ElementType.METHOD) // 只加在方法上
@Retention(RetentionPolicy.RUNTIME) // 程序运行时获取注释的信息
public @interface Log {
    String value() default "";
}
