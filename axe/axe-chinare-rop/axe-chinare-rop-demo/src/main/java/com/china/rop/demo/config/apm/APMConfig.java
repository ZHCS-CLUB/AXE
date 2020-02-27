package com.china.rop.demo.config.apm;

import javax.servlet.http.HttpServletRequest;

import org.nutz.log.Logs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import com.chinare.axe.apm.ApmAppender;
import com.chinare.axe.apm.DefaultApmAppender;
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
    public ApmAppender apmAppender(HttpServletRequest request) {
        return new DefaultApmAppender() {
            @Override
            @Async
            public void append(ApmLog log) {
                Logs.get().debug(log);
            }
        };
    }
}
