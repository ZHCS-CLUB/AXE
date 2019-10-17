package com.chinare.axe.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王贵源( kerbores@gmail.com)
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

	/**
	 * 认证逻辑
	 * 
	 * @author 王贵源(wangguiyuan@chinare.com.cn)
	 *
	 */
	enum Logical {
		/**
		 * 且
		 */
		AND, 
		/**
		 * 或
		 */
		OR
	}

	/**
	 * 认证类型
	 * 
	 * @author 王贵源(wangguiyuan@chinare.com.cn)
	 *
	 */
	enum AuthType {
		/**
		 * 角色
		 */
		ROLE, 
		/**
		 * 权限
		 */
		PERMISSION
	}

	/**
	 * 逻辑关系
	 * 
	 * @return 逻辑关系
	 */
	Logical logical() default Logical.AND;

	/**
	 * 
	 * @return 权限值
	 */
	String[] value() default {};

	/**
	 * 类型
	 * 
	 * @return 鉴权类型
	 */
	AuthType type() default AuthType.PERMISSION;
}
