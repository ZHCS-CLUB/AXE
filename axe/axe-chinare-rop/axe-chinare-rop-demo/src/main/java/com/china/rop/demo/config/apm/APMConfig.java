package com.china.rop.demo.config.apm;

import javax.servlet.http.HttpServletRequest;

import org.nutz.log.Logs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import com.chinare.axe.apm.APMAppender;
import com.chinare.axe.apm.DefaultAPMAppender;
import com.chinare.axe.apm.URLProvider;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Configuration
@EnableAsync
public class APMConfig {

    @Bean
    public URLProvider urlProvider() {
        return new MyUrlProvider();
    }

    @Bean
    public APMAppender apmAppender(HttpServletRequest request) {
        return new DefaultAPMAppender() {
            @Override
            @Async
            public void append(APMLog log) {
                Logs.get().debug(log);
            }
        };
    }
}
