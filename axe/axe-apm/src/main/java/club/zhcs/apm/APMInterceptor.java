package club.zhcs.apm;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Times;
import org.springframework.scheduling.annotation.Async;

import club.zhcs.apm.APMAppender.APMLog;

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
	 * @param appender
	 * @param collector
	 */
	public APMInterceptor(APMAppender appender, UserCollector collector, URLProvider urlProvider) {
		super();
		this.appender = appender;
		this.collector = collector;
		this.urlProvider = urlProvider;
	}

	@Pointcut("@annotation(club.zhcs.apm.APM)")
	public void cut() {
	}

	public APM getApm(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		APM target = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					target = method.getAnnotation(APM.class);
					break;
				}
			}
		}
		return target;
	}

	@Around("cut()")
	public Object filter(ProceedingJoinPoint point) throws Throwable {
		APM log = getApm(point);
		String user = collector.collector();
		APMLog apmLog = new APMLog();
		apmLog.setUser(user);
		apmLog.setTag(log.value());
		apmLog.setActionTime(Times.now());
		apmLog.setArgs(point.getArgs());
		if (urlProvider != null) {
			apmLog.setUrl(urlProvider.provide());
		}
		Object obj = null;
		try {
			Stopwatch stopwatch = Stopwatch.beginNano();
			obj = point.proceed();
			stopwatch.stop();
			apmLog.setActionDuration(stopwatch.getDuration());
			apmLog.setRetuenObj(obj);
		} catch (Throwable e) {
			apmLog.setException(true);
			throw e;
		} finally {
			log(apmLog);
		}
		return obj;
	}

	@Async
	public void log(APMLog log) {
		appender.append(log);
	}

}
