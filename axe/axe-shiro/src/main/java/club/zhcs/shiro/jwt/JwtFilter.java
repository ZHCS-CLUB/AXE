package club.zhcs.shiro.jwt;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import club.zhcs.common.Result;

/**
 * @author 王贵源<kerbores@gmail.com>
 *
 * @date 2018-12-27 16:22:27
 */
public class JwtFilter extends AuthenticatingFilter {

    // @Override
    // protected boolean isLoginAttempt(ServletRequest request, ServletResponse
    // response) {
    // HttpServletRequest req = (HttpServletRequest) request;
    // String authorization = req.getHeader("Authorization");
    // return authorization != null;
    // }

    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        Object p = getSubject(request, response).getPrincipal();
        if (authorization != null && (p == null)) {
            try {
                return executeLogin(request, response);
            }
            catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try {
            getSubject(request, response).login(jwtToken);
        }
        catch (Exception e) {
            resp.setStatus(401);
            resp.setCharacterEncoding("UTF-8");
            Json.toJson(resp.getWriter(), Result.exception(e), JsonFormat.compact());
            return false;
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.shiro.web.filter.authc.AuthenticatingFilter#createToken(javax.
     * servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        return new JwtToken(token);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.
     * servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

}
