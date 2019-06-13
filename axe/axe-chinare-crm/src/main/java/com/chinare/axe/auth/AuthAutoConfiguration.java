package com.chinare.axe.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王贵源 (kerbores@gmail.com)
 *
 */
@Configuration
@EnableConfigurationProperties(AuthAutoConfigurationPeroperties.class)
public class AuthAutoConfiguration {

    @Autowired
    AuthAutoConfigurationPeroperties configurationPeroperties;

    @Bean
    @ConditionalOnBean(AuthService.class)
    public TokenAuthInterceptor tokenAuthInterceptor(AuthService authService) {
        return new TokenAuthInterceptor(authService, configurationPeroperties.getWithoutAuthenticationUrlRegulars());
    }

}
