package com.chinare.axe.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王贵源 (kerbores@gmail.com)
 *
 * @date 2019-01-21 16:10:28
 */
@Configuration
public class AuthAutoConfiguration {

	@Bean
	@ConditionalOnBean(AuthService.class)
	public TokenAuthInterceptor tokenAuthInterceptor(AuthService authService) {
		return new TokenAuthInterceptor(authService);
	}

}
