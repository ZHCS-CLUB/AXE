package com.chinare.axe.apm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Apm {

    /**
     * 拦截器标签
     * 
     * @return 标签值
     */
    String value() default "";
}
