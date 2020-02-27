package com.chinare.axe.apm;

import java.util.Date;

import org.nutz.json.Json;
import org.nutz.lang.Times;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public interface ApmAppender {

    /**
     * 
     * @author 王贵源(kerbores@gmail.com)
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ApmLog {
        /**
         * 请求URL
         */
        String url;

        /**
         * 日志标签
         */
        String tag;

        /**
         * 访问用户
         */
        String user;

        /**
         * 发生时间
         */
        @Default
        Date actionTime = Times.now();

        /**
         * 执行耗时
         */
        long actionDuration;

        /**
         * 方法参数
         */
        Object[] args;

        /**
         * 方法返回值
         */
        Object retuenObj;

        /**
         * 是否异常
         */
        boolean exception;

        /**
         * 扩展信息
         */
        Object ext;

        @Override
        public String toString() {
            return Json.toJson(this);
        }

    }

    /**
     * 记录apm日志
     * 
     * @param log
     *            apm日志对象
     */
    void append(ApmLog log);

    /**
     * 采集apm日志
     * 
     * @param url
     *            请求地址
     * @param user
     *            当前用户
     * @param log
     *            apm注解
     * @param args
     *            方法参数
     * @param returnObj
     *            方法返回值
     * @param duration
     *            执行耗时
     * @param isException
     *            是否异常
     * @return apm日志对象
     */
    ApmLog collect(String url, String user, Apm log, Object[] args, Object returnObj, long duration, boolean isException);
}
