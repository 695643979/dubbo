package com.pinghua.enums;

/**
 *
 * app接口响应码定义
 * @author luxiaohui
 * @create 2018-08-14 10:42
 **/
public enum ApiServerCode {
    /**处理成功**/
    SUCCESS(1000, "处理成功"),
    /**处理失败**/
    FAIL(2000, "处理失败"),
    /**请求参数非法**/
    REQUEST_PARAM_ILLEGAL(3000, "请求参数非法"),
    /**token失效**/
    TOKEN_EXPIRED(4000, "token为空或已失效"),
    /**服务器内部处理异常**/
    SERVER_ERROR(5000, "服务器内部处理异常"),
    /**请求头信息有误**/
    HEAD_ILLEGAL(6000, "请求头信息有误"),
    /** 查询用户信息为null */
    USER_INFO_SYNC(1001, "用户信息推送记录为空");


    private final Integer code;
    private final String msg;

    private ApiServerCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
