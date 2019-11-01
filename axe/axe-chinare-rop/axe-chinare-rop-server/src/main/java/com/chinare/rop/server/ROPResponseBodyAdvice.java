package com.chinare.rop.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.chinare.rop.ROPConfig;
import com.chinare.rop.config.ROPServerConfigurationProperties;
import com.chinare.rop.core.signer.AppsecretFetcher;
import com.chinare.rop.core.signer.DigestSigner;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
@ControllerAdvice(annotations = ROP.class)
public class ROPResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    AppsecretFetcher appsecretFetcher;

    String digestName;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    /**
     *
     */
    public ROPResponseBodyAdvice(ROPServerConfigurationProperties properties) {
        this.digestName = properties.getDigestName();
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest req,
                                  ServerHttpResponse resp) {
        HttpMessageConverters messageConverters = applicationContext.getBean(HttpMessageConverters.class);
        HttpMessageConverter messageConverter = null;
        for (HttpMessageConverter<?> httpMessageConverter : messageConverters) {
            if (httpMessageConverter.getClass() == selectedConverterType) {
                messageConverter = httpMessageConverter;
                break;
            }
        }
        if (messageConverter == null) {
            throw Lang.makeThrow("messageConverter of type %s can't found!", selectedConverterType.getName());
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            messageConverter.write(body, selectedContentType, new HttpOutputMessage() {

                @Override
                public OutputStream getBody() throws IOException {
                    return baos;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return new HttpHeaders();
                }
            });
        }
        catch (RuntimeException | IOException e) {
            throw Lang.wrapThrow(e);
        }
        String bodyMd5 = Lang.md5(new ByteArrayInputStream(baos.toByteArray()));
        String key = request.getHeader(ROPConfig.APP_KEY_KEY);
        String gateway = request.getHeader(ROPConfig.METHOD_KEY);
        String timestamp = Times.now().getTime() + "";
        String nonce = request.getHeader(ROPConfig.NONCE_KEY);
        String sign = new DigestSigner(digestName).sign(appsecretFetcher.fetch(key), timestamp, gateway, nonce, bodyMd5);
        response.setHeader(ROPConfig.TS_KEY, timestamp);
        response.setHeader(ROPConfig.SIGN_KEY, sign);
        return body;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

}
