package com.chinare.rop.server;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.chinare.rop.ROPConfig;
import com.chinare.rop.core.ROPData;

/**
 *
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class ROPServlet extends HttpServlet {

    static final Log log = Logs.get();

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Date addSeconds(Date base, long seconds) {
        return Times.D(base.getTime() + seconds * 1000);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.
     * HttpServletRequest , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void service(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 时间戳校验 2.签名校验转移到nutz的filter中去处理,这样便于获取ioc中的对象 3.方法校验
         *
         */
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            log.debugf("header name : %s and header value is : %s", key, request.getHeader(key));
        }
        try {
            String method = request.getHeader(ROPConfig.METHOD_KEY);
            if (Strings.isBlank(method)) {// 空方法
                response.getWriter().write(Json.toJson(ROPData.exception("null method")));
                return;
            }
            String timeStemp = request.getHeader(ROPConfig.TS_KEY);
            if (Strings.isBlank(timeStemp)) {
                response.getWriter().write(Json.toJson(ROPData.exception("no timeStemp")));
                return;
            }
            long time = Long.parseLong(timeStemp);
            if (addSeconds(Times.D(time), Long.parseLong(getInitParameter("timeout"))).before(Times.now())) {
                response.getWriter().write(Json.toJson(ROPData.exception("request timeout")));
                return;
            }
            // 转发之前把签名相关的信息带过去
            request.getRequestDispatcher(method).forward(request, response);// 将请求转发给真实的函数入口
        }
        catch (Exception e) {
            response.getWriter().write(Json.toJson(ROPData.exception(e)));
        }
    }

}
