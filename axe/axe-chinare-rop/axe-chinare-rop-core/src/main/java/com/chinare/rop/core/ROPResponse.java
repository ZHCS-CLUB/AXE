package com.chinare.rop.core;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class ROPResponse<T> {

    /**
     * 创建一个异常结果
     *
     * @return 一个异常结果实例,不携带异常信息
     */
    public static ROPResponse exception() {
        return ROPResponse.me().setOperationState(OperationState.EXCEPTION);
    }

    /**
     * 创建一个异常结果
     *
     * @param e
     *            异常
     * @return 一个异常结果实例,包含参数异常的信息
     */
    public static ROPResponse exception(Exception e) {
        return ROPResponse.exception(e.getMessage());
    }

    /**
     * 创建一个异常结果
     *
     * @param msg
     *            异常信息
     * @return 一个异常结果实例,不携带异常信息
     */
    public static ROPResponse exception(String msg) {
        return ROPResponse.exception().setMsg(msg);
    }

    /**
     * 创建一个带失败信息的ROPData
     *
     * @param reason
     *            失败原因
     * @return ROPData实例
     */
    public static ROPResponse fail(String reason) {
        return ROPResponse.me().setOperationState(OperationState.FAIL).setMsg(reason);
    }

    /**
     * 获取一个ROPData实例
     *
     * @return 一个不携带任何信息的ROPData实例
     */
    public static ROPResponse me() {
        return new ROPResponse();
    }

    /**
     * 创建一个成功结果
     *
     * @return ROPData实例状态为成功无数据携带
     */
    public static ROPResponse success() {
        return ROPResponse.me().setOperationState(OperationState.SUCCESS);
    }

    /**
     * 未登录
     *
     * @return 未登录
     */
    public static ROPResponse unlogin() {
        return ROPResponse.me().setOperationState(OperationState.UNLOGINED);
    }

    /**
     * 操作结果数据 假设一个操作要返回很多的数据 一个用户名 一个产品 一个相关产品列表 一个产品的评论信息列表 我们以key
     * value形式进行保存，页面获取data对象读取其对于的value即可
     */
    private T data;

    private String msg;

    /**
     * 带状态的操作 比如登录有成功和失败
     */
    private OperationState operationState = OperationState.DEFAULT;

    public ROPResponse() {
        super();
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public OperationState getOperationState() {
        return operationState;
    }

    /**
     * 是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return getOperationState() == OperationState.SUCCESS;
    }

    public ROPResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ROPResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ROPResponse setOperationState(OperationState operationState) {
        this.operationState = operationState;
        return this;
    }

    public ROPResponse<T> success(T t) {
        return success().setData(t);
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
