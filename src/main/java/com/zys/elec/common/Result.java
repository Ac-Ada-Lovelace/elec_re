package com.zys.elec.common;

public interface Result<T> {
    public boolean  isSuccess();
    public T getData();
}
