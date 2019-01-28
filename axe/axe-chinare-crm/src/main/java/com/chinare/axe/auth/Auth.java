package com.chinare.axe.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王贵源( kerbores@gmail.com)
 * @date 2018-11-07 14:21:00
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * 
     * @author 王贵源<wangguiyuan@chinare.com.cn>
     *
     * @date 2018-05-25 14:18:29
     */
    public static enum Logical {
    AND, OR
    }

    /**
     * 
     * @author 王贵源<wangguiyuan@chinare.com.cn>
     *
     * @date 2018-05-25 14:19:04
     */
    public static enum AuthType {
         ROLE, PERMISSION
    }

    /**
     * 逻辑关系
     * 
     * @return
     */
    Logical logical() default Logical.AND;

    /**
     * 值
     * 
     * @return
     */
    String[] value();

    /**
     * 类型
     * 
     * @return
     */
    AuthType type() default AuthType.PERMISSION;
}
