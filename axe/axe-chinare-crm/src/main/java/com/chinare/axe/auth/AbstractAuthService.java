package com.chinare.axe.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Strings;

/**
 * @author 王贵源 (kerbores@gmail.com)
 *
 * @date 2019-01-21 16:05:51
 */
public abstract class AbstractAuthService implements AuthService {

	static final String AUTHORIZATION_KEY = "Authorization";

	HttpServletRequest request;

	/**
	 * 
	 */
	public AbstractAuthService(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String token() {
		String token = request.getHeader(AUTHORIZATION_KEY);
		if (token == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie ele : cookies) {
					if (Strings.equalsIgnoreCase(ele.getName(), AUTHORIZATION_KEY)) {
						token = ele.getValue();
					}
				}
			}

		}
		if (token == null) {
			token = request.getParameter(AUTHORIZATION_KEY);
		}
		return token;
	}
	
	/* (non-Javadoc)
	 * @see com.chinare.axe.auth.AuthService#userName()
	 */
	@Override
	public String userName() {
		if (token() == null) {
			return null;
		}
		return JwtUtil.getUsername(token());
	}

}