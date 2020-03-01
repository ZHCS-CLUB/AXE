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

import com.chinare.axe.apm.ApmAppender.ApmLog;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Aspect
public class ApmInterceptor {

    ApmAppender appender;

    UserCollector collector;

    URLProvider urlProvider;

    /**
     * 
     * @param appender
     *            信息处理器
     * @param collector
     *            用户收集器
     * @param urlProvider
     *            URL 提供者
     */
    public ApmInterceptor(ApmAppender appender, UserCollector collector, URLProvider urlProvider) {
        super();
        this.appender = appender;
        this.collector = collector;
        this.urlProvider = urlProvider;
    }

    public Apm getApm(JoinPoint joinPoint) {
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();

        boolean flag = method.isAnnotationPresent(Apm.class);
        if (flag) {
            return method.getAnnotation(Apm.class);
        } else {
            Apm classAnnotation = AnnotationUtils.findAnnotation(joinPointObject.getMethod().getDeclaringClass(), Apm.class);
            if (classAnnotation != null) {
                return classAnnotation;
            } else {
                return null;
            }
        }
    }

    @Around("@within(com.chinare.axe.apm.Apm)|| @annotation(com.chinare.axe.apm.Apm)")
    public Object filter(ProceedingJoinPoint point) throws Throwable {
        Apm log = getApm(point);
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

    /**
     * 记录apm日志
     * 
     * @param log
     *            apm日志信息
     */
    @Async
    public void log(ApmLog log) {
        appender.append(log);
    }
}
