package com.chinare.axe.handler;

import java.util.List;
import java.util.regex.Pattern;

import org.nutz.lang.Lang;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.chinare.axe.Result;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    List<String> ignorePaths;

    static final List<String> DEFAULT_IGNORED_PATH = Lang.list("/swagger-resources.*", "/v2/api-docs", "/actuator.*");

    /**
     * @param ignorePaths
     *            忽略的路径
     */
    public GlobalResponseHandler(List<String> ignorePaths) {
        if (ignorePaths == null || ignorePaths.isEmpty()) {
            ignorePaths = DEFAULT_IGNORED_PATH;
        }
        ignorePaths.addAll(DEFAULT_IGNORED_PATH);
        this.ignorePaths = ignorePaths;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof Result || ignored(request.getURI().getRawPath())) {
            return body;
        }
        return Result.success(body);

    }

    /**
     * @param rawPath
     * @return
     */
    private boolean ignored(String path) {
        return ignorePaths.stream().anyMatch(item -> Pattern.matches(item, path));
    }
}
