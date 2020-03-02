package com.chinare.axe.handler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Configuration
@EnableConfigurationProperties(GlobalResponseConfigurationProerties.class)
@ConditionalOnExpression("${axe.global.response.enabled:false}")
public class GlobalResponseAutoConfiguration {

    @Bean
    public GlobalResponseHandler globalResponseHandler(GlobalResponseConfigurationProerties proerties) {
        return new GlobalResponseHandler(proerties.getIgnorePaths());
    }
}
