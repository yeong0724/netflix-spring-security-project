package com.jinyeong.netflix.exception;

import lombok.Getter;

@Getter
public class NetflixException extends RuntimeException {
    private final ErrorCode errorCode;

    public NetflixException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
