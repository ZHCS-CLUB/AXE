package com.chinare.axe.apm;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Configuration
public class ApmAutoConfiguration {

    @Bean
    public ApmInterceptor apmInterceptor(ApmAppender appender, UserCollector collector, URLProvider urlProvider) {
        return new ApmInterceptor(appender, collector, urlProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApmAppender apmAppender() {
        return new DefaultApmAppender();
    }

    @Bean
    @ConditionalOnMissingBean
    public URLProvider urlProvider() {
        return () -> null;
    }

    @Bean
    @ConditionalOnMissingBean
    public UserCollector userCollector() {
        return () -> null;
    }

}
