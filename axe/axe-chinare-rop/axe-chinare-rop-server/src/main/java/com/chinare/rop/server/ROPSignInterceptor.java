package com.chinare.rop.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinare.rop.core.signer.AppsecretFetcher;
import com.chinare.rop.core.signer.DigestSigner;
import com.chinare.rop.core.signer.Signer;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.
 *
 *         import com.chinare.rop.core.signer.AppsecretFetcher; import
 *         com.chinare.rop.core.signer.DigestSigner;com.cn)
 */
@Aspect
public class ROPSignInterceptor {

    @Autowired
    RequestChecker checker;

    String digestName;

    @Autowired
    AppsecretFetcher fetcher;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    /**
     *
     */
    public ROPSignInterceptor(String digestName) {
        this.digestName = digestName;
    }

    @Around("@within(com.chinare.rop.server.ROP)|| @annotation(com.chinare.rop.server.ROP)")
    public Object filter(ProceedingJoinPoint point) throws Throwable {
        Signer signer = new DigestSigner(digestName);
        if (checker.check(request) && signer.check(request, fetcher)) {
            return point.proceed();
        } else {
            throw new ROPException("checkSign failed");
        }
    }

}
