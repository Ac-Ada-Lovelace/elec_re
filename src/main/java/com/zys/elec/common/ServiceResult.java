package com.zys.elec.common;

// 定义通用的结果类 ServiceResult
public class ServiceResult<T> {
    private final boolean success;
    private final String message;
    private final T data;

    // 构造方法

    public ServiceResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getter 和 Setter 方法省略

    // 静态方法，方便创建 ServiceResult 对象
    public static <T> ServiceResult<T> success(T data) {
        return new ServiceResult<>(true, "操作成功", data);
    }

    public static <T> ServiceResult<T> failure(String message) {
        return new ServiceResult<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        // TODO Auto-generated method stub
        return data;
    }
}