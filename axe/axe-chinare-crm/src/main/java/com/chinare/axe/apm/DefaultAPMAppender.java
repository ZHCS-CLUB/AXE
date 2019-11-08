package com.chinare.axe.apm;

import org.nutz.lang.Times;
import org.nutz.log.Logs;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class DefaultAPMAppender implements APMAppender {

    @Override
    public void append(APMLog log) {
        Logs.get().debug(log);
    }

    @Override
    public APMLog collect(String url, String user, APM log, Object[] args, Object obj, long duration, boolean exception) {
        return APMLog.builder()
                     .url(url)
                     .user(user)
                     .actionDuration(duration)
                     .args(args)
                     .actionTime(Times.now())
                     .exception(exception)
                     .retuenObj(obj)
                     .tag(log.value())
                     .build();
    }
}
