package com.jinyeong.netflix.controller;

import lombok.Getter;

@Getter
public class NetflixApiResponse<T> {
    private final boolean success;
    private final String code;
    private final String message;
    private final T data;

    public static final String CODE_SUCCEED = "SUCCEED";

    public NetflixApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> NetflixApiResponse<T> ok(T data) {
        return new NetflixApiResponse<>(true, CODE_SUCCEED, null, data);
    }

    public static <T> NetflixApiResponse<T> fail(String errorCode, String message) {
        return new NetflixApiResponse<>(false, errorCode, message, null);
    }
}