package com.chinare.rop.core.signer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.http.Http;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.upload.FastUploading;
import org.nutz.mvc.upload.Html5Uploading;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadException;
import org.nutz.mvc.upload.Uploading;
import org.nutz.mvc.upload.UploadingContext;
import org.nutz.mvc.upload.Uploads;

import com.chinare.rop.ROPConfig;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public abstract class AbstractSinger implements Signer {
    UploadingContext context = new UploadingContext(System.getProperty("java.io.tmpdir"));

    Log log = Logs.get();

    @Override
    public boolean check(HttpServletRequest request, AppsecretFetcher fetcher) {
        if (Strings.isBlank(request.getHeader(ROPConfig.APP_KEY_KEY))
            ||
            Strings.isBlank(request.getHeader(ROPConfig.TS_KEY))
            ||
            Strings.isBlank(request.getHeader(ROPConfig.METHOD_KEY))
            ||
            Strings.isBlank(request.getHeader(ROPConfig.NONCE_KEY))
            ||
            Strings.isBlank(request.getHeader(ROPConfig.SIGN_KEY))) {
            return false;
        }
        String sign = request.getHeader(ROPConfig.SIGN_KEY);
        log.debugf("Expected sign is %s", sign);
        return Strings.equalsIgnoreCase(sign(request, fetcher), sign);
    }

    protected String getDataMate(HttpServletRequest request) {
        if (Strings.equalsIgnoreCase(request.getMethod(), "GET")) {// GET请求需要处理一下
            return Lang.md5(request.getQueryString());
        }
        // 文件上传
        if (isFileUpload(request)) {
            try {
                return Lang.md5(new ByteArrayInputStream(getURLEncodedParams(request).getBytes(request.getCharacterEncoding())));
            }
            catch (UnsupportedEncodingException e) {
                log.debug("不支持的编码!");
                throw Lang.wrapThrow(e);
            }
        }
        try {
            return Lang.md5(request.getInputStream());
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
    }

    // NUTZ的文件上传解析先拿过来用起来
    public Map<String, Object> getReferObject(HttpServletRequest request) {
        try {
            if (!"POST".equals(request.getMethod()) && !"PUT".equals(request.getMethod())) {
                String str = "Not POST or PUT, Wrong HTTP method! --> " + request.getMethod();
                throw new UploadException(str);
            }
            // 看看是不是传统的上传
            String contentType = request.getContentType();
            if (contentType == null) {
                throw new UploadException("Content-Type is NULL!!");
            }
            if (contentType.contains("multipart/form-data")) { // 普通表单上传
                if (log.isDebugEnabled())
                    log.debug("Select Html4 Form upload parser --> " + request.getRequestURI());
                Uploading ing = new FastUploading();
                return ing.parse(request, context);
            }
            if (contentType.contains("application/octet-stream")) { // Html5
                // 流式上传
                if (log.isDebugEnabled())
                    log.debug("Select Html5 Stream upload parser --> " + request.getRequestURI());
                Uploading ing = new Html5Uploading();
                return ing.parse(request, context);
            }
            // 100%是没写enctype='multipart/form-data'
            if (contentType.contains("application/x-www-form-urlencoded")) {
                log.warn("Using form upload ? You forgot this --> enctype='multipart/form-data' ?");
            }
            throw new UploadException("Unknow Content-Type : " + contentType);
        }
        catch (UploadException e) {
            throw Lang.wrapThrow(e);
        }
        finally {
            Uploads.removeInfo(request);
        }
    }

    public String getURLEncodedParams(final HttpServletRequest request) {
        Map<String, ?> params = getReferObject(request);
        final StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        for (final String key : keys) {
            Object val = params.get(key);
            if (val == null)
                val = "";
            Lang.each(val, new Each<Object>() {
                @Override
                public void invoke(int index, Object ele, int length)
                        throws ExitLoop, ContinueLoop, LoopException {
                    if (ele instanceof File) {// 文件
                        sb.append(Http.encode(key, request.getCharacterEncoding()))
                          .append('=')
                          .append(Http.encode(Lang.md5((File) ele), request.getCharacterEncoding()))
                          .append('&');
                    } else if (ele instanceof TempFile) {// tempFile
                        try {
                            sb.append(Http.encode(key, request.getCharacterEncoding()))
                              .append('=')
                              .append(Http.encode(Lang.md5(((TempFile) ele).getInputStream()), request.getCharacterEncoding()))
                              .append('&');
                        }
                        catch (IOException e) {
                            throw Lang.wrapThrow(e);
                        }
                    } else {
                        sb.append(Http.encode(key, request.getCharacterEncoding()))
                          .append('=')
                          .append(Http.encode(ele, request.getCharacterEncoding()))
                          .append('&');
                    }
                }
            });
        }
        if (sb.length() > 0)
            sb.setLength(sb.length() - 1);
        return sb.toString();

    }

    private boolean isCommonFileUpload(HttpServletRequest request) {
        return request.getHeader("Content-Type") != null && request.getHeader("Content-Type").startsWith("multipart/form-data");
    }

    /**
     * @param request
     * @return
     */
    private boolean isFileUpload(HttpServletRequest request) {
        return isCommonFileUpload(request) || isHtml5FileUpload(request);
    }

    private boolean isHtml5FileUpload(HttpServletRequest request) {
        return request.getHeader("Content-Type") != null && request.getHeader("Content-Type").startsWith("application/octet-stream");
    }

    /**
     * 根据appSecret获取器签名
     *
     * @param fetcher
     *            appSecret获取器
     * @param appKey
     *            应用key
     * @param timestamp
     *            时间戳
     * @param gateway
     *            方法/路由
     * @param nonce
     *            随机串
     * @param dataMate
     *            数据元数据
     * @return
     */
    public String sign(AppsecretFetcher fetcher, String appKey, String timestamp, String gateway, String nonce, String dataMate) {
        return sign(fetcher.fetch(appKey), timestamp, gateway, nonce, dataMate);
    }

    /**
     * 服务器端签名
     *
     * @param request
     *            请求
     * @param fetcher
     *            appSecret获取器
     * @return
     * @throws IOException
     */
    public String sign(HttpServletRequest request, AppsecretFetcher fetcher) {
        return sign(fetcher,
                    request.getHeader(ROPConfig.APP_KEY_KEY),
                    request.getHeader(ROPConfig.TS_KEY),
                    request.getHeader(ROPConfig.METHOD_KEY),
                    request.getHeader(ROPConfig.NONCE_KEY),
                    getDataMate(request));
    }

}
