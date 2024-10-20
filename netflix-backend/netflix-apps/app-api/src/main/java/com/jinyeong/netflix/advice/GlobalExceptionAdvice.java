package com.jinyeong.netflix.advice;

import com.jinyeong.netflix.controller.NetflixApiResponse;
import com.jinyeong.netflix.exception.ErrorCode;
import com.jinyeong.netflix.exception.NetflixException;
import com.jinyeong.netflix.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.jinyeong.netflix.exception.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(UserException.class)
    protected NetflixApiResponse<?> handleUserException(UserException userException) {
        log.error("error={}", userException.getMessage(), userException);

        ErrorCode errorCode = userException.getErrorCode();
        return NetflixApiResponse.fail(errorCode.getCode(), errorCode.getDesc());
    }

    @ExceptionHandler(RuntimeException.class)
    protected NetflixApiResponse<?> handleRuntimeException(RuntimeException runtimeException) {
        log.error("error={}", runtimeException.getMessage(), runtimeException);

        String errorMessage = runtimeException.getMessage();
        if (errorMessage == null) {
            errorMessage = DEFAULT_ERROR.getDesc();
        }

        return NetflixApiResponse.fail(DEFAULT_ERROR.getCode(), errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected NetflixApiResponse<?> handleBadCredentialsException(BadCredentialsException e) {
        log.error("BadCredentialsException: {}", e.getMessage(), e);
        return NetflixApiResponse.fail(AUTHENTICATION_FAILED.getCode(), AUTHENTICATION_FAILED.getDesc());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    protected NetflixApiResponse<?> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        log.error("AuthorizationDeniedException : {}", e.getMessage(), e);
        return NetflixApiResponse.fail(ACCESS_DENIED.getCode(), ACCESS_DENIED.getDesc());
    }

    @ExceptionHandler(NetflixException.class)
    protected NetflixApiResponse<?> handleBadCredentialsException(NetflixException netflixException) {
        log.error("netflixException: {}", netflixException.getMessage(), netflixException);
        ErrorCode errorCode = netflixException.getErrorCode();
        return NetflixApiResponse.fail(errorCode.getCode(), errorCode.getDesc());
    }
}
