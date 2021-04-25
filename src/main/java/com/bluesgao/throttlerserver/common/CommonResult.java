package com.bluesgao.throttlerserver.common;

import java.io.Serializable;

public class CommonResult<T> implements Serializable {
    private String msg;
    private Integer code;
    private T data;

    public CommonResult(Integer code, String msg, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(0, "success", data);
    }

    public static <T> CommonResult<T> fail(String msg, T data) {
        return new CommonResult<T>(9999, msg, data);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
