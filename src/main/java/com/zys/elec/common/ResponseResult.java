package com.zys.elec.common;

import lombok.Data;

/**
 * 统一返回结果类
 *
 * @param <T>
 */
@Data
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> success(String message) {
        return new ResponseResult<>(200, message, null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, "操作成功", data);
    }


    public ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseResult<T> failure(String message) {
        return new ResponseResult<T>(400, message, null);
    }
}

