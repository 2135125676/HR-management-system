package com.group2.cms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 当方法需要权限判断时，在指定的方法使用该注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {
    String value(); // 使用该注解的开发者，必须明确指定方法所需要的权限 admin system...
}
