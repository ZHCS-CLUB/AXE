package com.chinare.axe;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.util.NutMap;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据返回对象封装
 * 
 * @author 王贵源(kerbores@gmail.com)
 * 
 *         create at 2019-11-21 09:47:25
 * @param <T>
 *            数据泛型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 操作状态枚举
     * 
     * @author 王贵源(kerbores@gmail.com)
     * 
     *         create at 2019-11-21 09:47:41
     */
    public enum OperationState {
        /**
         * 成功
         */
        SUCCESS,
        /**
         * 失败
         */
        FAIL,
        /**
         * 异常
         */
        EXCEPTION;

    }

    @Default
    @ApiModelProperty(value = "响应扩展数据", required = false)
    private NutMap ext = new NutMap();

    @Default
    @ApiModelProperty(value = "响应状态", required = true)
    private OperationState state = OperationState.SUCCESS;

    @ApiModelProperty(value = "错误信息列表")
    private String[] errors;

    @ApiModelProperty(value = "响应数据", required = true)
    private T data;

    /**
     * 返回成功
     * 
     * @return 成功的结果
     */
    public static Result success() {
        return Result.builder().build();
    }

    /**
     * 携带数据返回成功
     * 
     * @param <T>
     *            数据泛型
     * @param t
     *            数据
     * @return 成功的结果
     */
    public static <T> Result<T> success(T t) {
        return Result.<T> builder().data(t).build();
    }

    /**
     * 返回失败及原因
     * 
     * @param errors
     *            失败原因
     * @return 失败的结果
     */
    public static Result fail(String... errors) {
        return Result.builder().state(OperationState.FAIL).errors(errors).build();
    }

    /**
     * 返回异常及原因
     * 
     * @param errors
     *            异常原因
     * @return 异常的结果
     */
    public static Result exception(String... errors) {
        return Result.builder().state(OperationState.EXCEPTION).errors(errors).build();
    }

    /**
     * 返回异常及原因
     * 
     * @param errors
     *            异常原因
     * @return 异常的结果
     */
    public static Result exception(List<String> errors) {
        return Result.builder().state(OperationState.EXCEPTION).errors(errors.toArray(new String[errors.size()])).build();
    }

    /**
     * 返回异常及原因
     * 
     * @param exceptions
     *            异常
     * @return 异常的结果
     */
    public static Result exception(Throwable... exceptions) {
        return exception(Arrays.stream(exceptions).map(Throwable::getMessage).collect(Collectors.toList()));
    }

    /**
     * 添加扩展数据
     * 
     * @param key
     *            数据key
     * @param value
     *            数据
     * @return 返回结果对象
     */
    public Result addExtData(String key, Object value) {
        getExt().setv(key, value);
        return this;
    }

    /**
     * 是否成功
     * 
     * @return 是否成功标识
     */
    public boolean isSuccess() {
        return getState() == OperationState.SUCCESS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Json.toJson(this, JsonFormat.forLook());
    }
}
