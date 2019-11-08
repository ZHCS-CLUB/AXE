package com.china.rop.demo.config.apm;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinare.axe.apm.URLProvider;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class MyUrlProvider implements URLProvider {

    @Autowired
    HttpServletRequest request;

    @Override
    public String provide() {
        return request.getRequestURI();
    }
}
