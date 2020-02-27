package com.chinare.axe.apm;

import org.nutz.lang.Times;
import org.nutz.log.Logs;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class DefaultApmAppender implements ApmAppender {

    @Override
    public void append(ApmLog log) {
        Logs.get().debug(log);
    }

    @Override
    public ApmLog collect(String url, String user, Apm log, Object[] args, Object returnObj, long duration, boolean isException) {
        return ApmLog.builder()
                     .url(url)
                     .user(user)
                     .actionDuration(duration)
                     .args(args)
                     .actionTime(Times.now())
                     .exception(isException)
                     .retuenObj(returnObj)
                     .tag(log.value())
                     .build();
    }

}
