package com.jinyeong.netflix.controller;

import com.jinyeong.netflix.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NetplixApiResponse<T> {
    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    public static final String CODE_SUCCEED = "SUCCEED";

    public NetplixApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> NetplixApiResponse<T> ok(T data) {
        return new NetplixApiResponse<>(true, CODE_SUCCEED, null, data);
    }

    public static <T> NetplixApiResponse<T> fail(String errorCode, String message) {
        return new NetplixApiResponse<>(false, errorCode, message, null);
    }
}