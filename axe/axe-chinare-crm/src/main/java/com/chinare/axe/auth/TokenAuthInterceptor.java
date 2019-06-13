package com.chinare.axe.auth;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.core.annotation.AnnotationUtils;

import com.chinare.axe.auth.Auth.AuthType;
import com.chinare.axe.auth.Auth.Logical;

/**
 * @author 王贵源( kerbores@gmail.com)
 */
@Aspect
public class TokenAuthInterceptor {

	AuthService authService;

	String[] withoutAuthenticationUrlRegulars;
	Log logger = Logs.get();

	public TokenAuthInterceptor(AuthService authService, String[] withoutAuthenticationUrlRegulars) {
		this.authService = authService;
		this.withoutAuthenticationUrlRegulars = withoutAuthenticationUrlRegulars;
	}

	public Auth getAuth(JoinPoint joinPoint) {
		MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
		Method method = joinPointObject.getMethod();
		return method.isAnnotationPresent(Auth.class) ? method.getAnnotation(Auth.class)
				: AnnotationUtils.findAnnotation(joinPointObject.getMethod().getDeclaringClass(), Auth.class);
	}

	@Around("@within(com.chinare.axe.auth.Auth)|| @annotation(com.chinare.axe.auth.Auth)")
	public Object filter(ProceedingJoinPoint point) throws Throwable {
		if (!authService.authentication(this.withoutAuthenticationUrlRegulars)) {
			throw new AuthException();
		}
		Auth auth = getAuth(point);
		if (auth == null || auth.value().length == 0) {// 没有注解 跳过
			return point.proceed();
		}

		if (checkAuth(auth)) { // 验证通过执行业务
			return point.proceed();
		}
		// 不能通过 返回401状态码
		throw new AuthException();
	}

	private boolean checkAuth(Auth auth) {
		if (authService.user() == null) {
			return false;
		}
		if (auth.type() == AuthType.ROLE) {
			return checkRole(auth.value(), auth.logical());
		}
		return checkPermission(auth.value(), auth.logical());

	}

	private boolean checkPermission(String[] value, Logical logical) {
		if (logical == Logical.AND) {
			for (String p : value) {
				if (!hasPermission(p)) {
					logger.debugf("user does not has peermission %s", p);
					return false;
				}
			}
			return true;
		} else {
			for (String p : value) {
				if (hasPermission(p)) {
					return true;
				}
			}
			logger.debugf("user does not has any permission of %s", Json.toJson(value, JsonFormat.compact()));
			return false;
		}
	}

	private boolean hasPermission(String p) {
		List<String> list = authService.permissions();
		for (String permission : list) {
			if (Strings.equals(permission, p)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkRole(String[] value, Logical logical) {
		if (logical == Logical.AND) {
			for (String r : value) {
				if (!hasRole(r)) {
					logger.debugf("user does not has role %s", r);
					return false;
				}
			}
			return true;
		} else {
			for (String r : value) {
				if (hasRole(r)) {
					return true;
				}
			}
			logger.debugf("user does not has any role of %s", Json.toJson(value, JsonFormat.compact()));
			return false;
		}

	}

	private boolean hasRole(String r) {
		for (String role : authService.roles()) {
			if (Strings.equals(role, r)) {
				return true;
			}
		}
		return false;
	}

}
