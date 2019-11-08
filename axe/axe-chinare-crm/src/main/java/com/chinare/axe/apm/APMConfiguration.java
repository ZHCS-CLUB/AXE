package com.chinare.axe.apm;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kerbores
 *
 */
@Configuration
public class APMConfiguration {

    @Bean
    public APMInterceptor apmInterceptor(APMAppender appender, UserCollector collector, URLProvider urlProvider) {
        return new APMInterceptor(appender, collector, urlProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public APMAppender apmAppender() {
        return new DefaultAPMAppender();
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
