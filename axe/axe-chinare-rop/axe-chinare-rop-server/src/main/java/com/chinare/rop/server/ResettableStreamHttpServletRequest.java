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
import java.util.stream.Collectors;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.nutz.http.Http;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.upload.FastUploading;
import org.nutz.mvc.upload.UploadException;
import org.nutz.mvc.upload.UploadingContext;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {
    public class ResettableServletInputStream extends ServletInputStream {

        private ByteArrayInputStream inputStream;

        public ResettableServletInputStream(byte[] buffer) {
            this.inputStream = new ByteArrayInputStream(buffer);
        }

        @Override
        public int available() throws IOException {
            return inputStream.available();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return inputStream.read(b, off, len);
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // Do nothing
        }
    }

    static final Log log = Logs.get();

    private byte[] buffer;

    private Map<String, String[]> cachedParamMap;

    public ResettableStreamHttpServletRequest(HttpServletRequest request)
            throws IOException {
        super(request);
        try {
            request.getParts();
        }
        catch (ServletException e) {
            throw Lang.wrapThrow(e);
        }
        this.buffer = Streams.readBytes(request.getInputStream());
        this.cachedParamMap = parseParaMap(request);

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ResettableServletInputStream(this.buffer);
    }

    @Override
    public String getParameter(String name) {
        return cachedParamMap.get(name) == null ? null : Strings.join(",", cachedParamMap.get(name));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return cachedParamMap;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getParameterNames()
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(cachedParamMap.keySet());
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.
     * String)
     */
    @Override
    public String[] getParameterValues(String name) {
        return cachedParamMap.get(name);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * @param str
     * @return
     */
    private boolean notFileParam(String str) {
        String[] infos = str.replaceAll("\r", "").split("\n");
        return infos.length > 2 && Strings.isBlank(infos[2]);
    }

    protected void parse(HttpServletRequest request, Map<String, String[]> target, String info) throws UnsupportedEncodingException {
        for (String seg : info.split("&")) {
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
    }

    /**
     * @return
     * @throws IOException
     */
    private Map<String, String[]> parseParaMap(HttpServletRequest request) throws IOException {
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

        if (Strings.isNotBlank(getHeader("Content-Type"))
            && getHeader("Content-Type").startsWith("application/x-www-form-urlencoded")) {// 表单参数
            FastUploading u = new FastUploading();
            try {
                u.parse(request, new UploadingContext("~/test"));
            }
            catch (UploadException e) {
                throw Lang.wrapThrow(e);
            }
            parse(request, target, info);
        } else {
            // 文件上传的情况
            String firstBoundary = "--" + Http.multipart.getBoundary(request.getContentType());
            String[] infos = info.split(firstBoundary);
            Lang.list(infos)
                .stream()
                .filter(str -> Strings.isNotBlank(str) && notFileParam(str))
                .forEach(str -> {
                    List<String> paramInfo = Lang.list(str.replaceAll("\r", "").split("\n"))
                                                 .stream()
                                                 .filter(Strings::isNotBlank)
                                                 .collect(Collectors.toList());
                    String key = paramInfo.get(0);
                    key = key.substring(key.indexOf('"') + 1, key.lastIndexOf('"'));
                    String[] val = paramInfo.get(1).split(",");
                    target.put(key, val);
                });
        }

        return target;
    }
}
