package com.chinare.axe.apm;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.nutz.lang.Stopwatch;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.Async;

import com.chinare.axe.apm.APMAppender.APMLog;

/**
 * @author kerbores
 *
 */
@Aspect
public class APMInterceptor {

    APMAppender appender;

    UserCollector collector;

    URLProvider urlProvider;

    /**
     * 
     * @param appender
     *            信息处理器
     * @param collector
     *            用户收集器
     * @param urlProvider
     *            url 提供者
     */
    public APMInterceptor(APMAppender appender, UserCollector collector, URLProvider urlProvider) {
        super();
        this.appender = appender;
        this.collector = collector;
        this.urlProvider = urlProvider;
    }

    public APM getApm(JoinPoint joinPoint) {
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();

        boolean flag = method.isAnnotationPresent(APM.class);
        if (flag) {
            return method.getAnnotation(APM.class);
        } else {
            APM classAnnotation = AnnotationUtils.findAnnotation(joinPointObject.getMethod().getDeclaringClass(), APM.class);
            if (classAnnotation != null) {
                return classAnnotation;
            } else {
                return null;
            }
        }
    }

    @Around("@within(com.chinare.axe.apm.APM)|| @annotation(com.chinare.axe.apm.APM)")
    public Object filter(ProceedingJoinPoint point) throws Throwable {
        APM log = getApm(point);
        Object[] args = point.getArgs();
        Object obj = null;
        long duration = 0;
        boolean exception = false;
        try {
            Stopwatch stopwatch = Stopwatch.beginNano();
            obj = point.proceed();
            stopwatch.stop();
            duration = stopwatch.getDuration();
        }
        catch (Throwable e) {
            exception = true;
            throw e;
        }
        finally {
            log(appender.collect(urlProvider.provide(), collector.collector(), log, args, obj, duration, exception));
        }
        return obj;
    }

    @Async
    public void log(APMLog log) {
        appender.append(log);
    }

}
