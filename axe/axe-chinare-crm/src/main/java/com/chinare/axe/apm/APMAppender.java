package com.chinare.axe.apm;

import java.util.Date;

import org.nutz.json.Json;

import lombok.Builder;
import lombok.Data;

/**
 * @author kerbores
 *
 */
public interface APMAppender {

    @Data
    @Builder
    public static class APMLog {

        String url;

        String tag;

        String user;

        Date actionTime;

        long actionDuration;

        Object[] args;

        Object retuenObj;

        boolean exception;

        Object ext;

        @Override
        public String toString() {
            return Json.toJson(this);
        }

    }

    void append(APMLog log);

    /**
     * @param provide
     * @param collector
     * @param log
     * @param args
     * @param obj
     * @param duration
     * @param exception
     * @return
     */
    APMLog collect(String provide, String collector, APM log, Object[] args, Object obj, long duration, boolean exception);
}
