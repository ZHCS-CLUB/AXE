package com.chinare.rop.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.util.NutMap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinare.rop.core.signer.AppsecretFetcher;
import com.chinare.rop.core.signer.DefaultMD5Fetcher;
import com.chinare.rop.server.ROPServlet;
import com.chinare.rop.server.ROPSignInterceptor;
import com.chinare.rop.server.ResettableStreamHttpServletRequest;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@Configuration
@EnableConfigurationProperties(ROPServerConfigurationProperties.class)
public class ROPServerAutoConfiguration {

    /**
     * 没有的时候垫底的存在,不建议使用,请自行实现AppsecretFetcher并声明为bean
     *
     * @return 默认 AppsecretFetcher
     */
    @Bean
    @ConditionalOnMissingBean(AppsecretFetcher.class)
    public AppsecretFetcher appsecretFetcher() {
        return new DefaultMD5Fetcher();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Filter() {

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                ServletRequest requestWrapper = null;
                if (request instanceof HttpServletRequest) {
                    requestWrapper = new ResettableStreamHttpServletRequest((HttpServletRequest) request);
                }
                if (requestWrapper == null) {
                    chain.doFilter(request, response);
                } else {
                    chain.doFilter(requestWrapper, response);
                }
            }
        });
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public ROPSignInterceptor ropSignInterceptor(ROPServerConfigurationProperties properties) {
        return new ROPSignInterceptor(properties.getDigestName());
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(ROPServerConfigurationProperties properties) {
        ServletRegistrationBean ropServletRegistrationBean = new ServletRegistrationBean(new ROPServlet());
        ropServletRegistrationBean.setInitParameters(NutMap.NEW().addv("timeout", "" + properties.getTimeout()));
        ropServletRegistrationBean.addUrlMappings(properties.getRopPath());
        return ropServletRegistrationBean;
    }

}