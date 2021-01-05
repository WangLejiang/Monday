package com.wanglejiang.common.result;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int code;

    private final String message;

    private T data;

    private boolean successful;

    private R(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static R<?> ok() {
        return new R<>(ResultStatusEnum.SUCCESS.getCode(), ResultStatusEnum.SUCCESS.getMessage());
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>(ResultStatusEnum.SUCCESS.getCode(), ResultStatusEnum.SUCCESS.getMessage());
        r.put(data);
        return r;
    }

    public static <T> R<T> error(int code, String message) {
        return new R<>(code, message);
    }

    public R<T> put(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccessful() {
        return this.code == ResultStatusEnum.SUCCESS.getCode();
    }

}
