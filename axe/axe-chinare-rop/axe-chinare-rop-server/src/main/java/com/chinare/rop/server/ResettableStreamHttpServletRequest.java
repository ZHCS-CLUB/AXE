package com.chinare.rop.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {

    public class ResettableServletInputStream extends ServletInputStream {

        private ByteArrayInputStream stream;

        public ResettableServletInputStream(ByteArrayInputStream stream) {
            this.stream = stream;
        }

        @Override
        public boolean isFinished() {
            return stream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw Lang.noImplement();
        }
    }

    private Map<String, String[]> paramMap;

    private byte[] rawData;

    private HttpServletRequest request;

    public ResettableStreamHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
        try {
            paramMap = parsrParaMap();
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null) {
            rawData = Streams.readBytes(this.request.getInputStream());
        }
        return new ResettableServletInputStream(new ByteArrayInputStream(rawData));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String name) {
        return paramMap.get(name) == null ? null : Strings.join(",", paramMap.get(name));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return paramMap;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getParameterNames()
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(paramMap.keySet());
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.
     * String)
     */
    @Override
    public String[] getParameterValues(String name) {
        return paramMap.get(name);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    protected void parseParam(Map<String, String[]> target, String seg) throws UnsupportedEncodingException {
        String key = seg.split("=")[0];
        String val = seg.split("=")[1] == null ? null : seg.split("=")[1];

        String[] v;
        if (val == null) {
            v = target.get(key);// 获取之前的
        } else {
            List<String> vals = new ArrayList<>();
            for (String v1 : val.split(",")) {
                vals.add(URLDecoder.decode(v1, request.getCharacterEncoding()));
            }
            String[] v2 = target.get(key);
            if (v2 != null) {
                vals.addAll(Lang.array2list(v2));
            }
            v = Lang.collection2array(vals);
        }
        target.put(key, v);
    }

    /**
     * @return
     * @throws IOException
     */
    private Map<String, String[]> parsrParaMap() throws IOException {
        Map<String, String[]> target = new HashMap<>();
        String info = null;
        if (Strings.equalsIgnoreCase(request.getMethod(), "GET")) {// get请求
            info = getQueryString();
        } else {
            info = new String(Streams.readBytes(getInputStream()), request.getCharacterEncoding());
        }
        if (Strings.isBlank(info)) {
            return target;
        }

        if (Strings.isNotBlank(getHeader("Content-Type")) && getHeader("Content-Type").startsWith("application/x-www-form-urlencoded")) {// 表单参数
            for (String seg : info.split("&")) {
                parseParam(target, seg);

            }
        }

        return target;
    }
}
