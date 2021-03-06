package com.chinare.axe.auth;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Strings;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public abstract class AbstractAuthService implements AuthService {

    static final String AUTHORIZATION_KEY = "Authorization";

    protected HttpServletRequest request;

    /**
     * 
     * @param request
     *            http 请求
     */
    public AbstractAuthService(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public boolean authentication(List<String> withoutAuthenticationUrlRegulars) {
        return skip() || pass(request.getRequestURI(), withoutAuthenticationUrlRegulars) || user() != null;
    }

    /**
     * 是否放行检测
     * 
     * @param uri
     *            请求地址
     * @param withoutAuthenticationUrlRegulars
     *            放行url正则
     * @return 是否放行标识
     */
    protected boolean pass(String uri, List<String> withoutAuthenticationUrlRegulars) {
        for (String regular : withoutAuthenticationUrlRegulars) {
            if (Pattern.matches(regular, uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String token() {
        String token = request.getHeader(AUTHORIZATION_KEY);
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie ele : cookies) {
                    if (Strings.equalsIgnoreCase(ele.getName(), AUTHORIZATION_KEY)) {
                        token = ele.getValue();
                        break;
                    }
                }
            }

        }
        if (token == null) {
            token = request.getParameter(AUTHORIZATION_KEY);
        }
        return token;
    }

    @Override
    public String userName() {
        if (token() == null) {
            return null;
        }
        return JwtUtil.getUsername(token());
    }

    @Override
    public boolean skip() {
        return false;
    }

}
