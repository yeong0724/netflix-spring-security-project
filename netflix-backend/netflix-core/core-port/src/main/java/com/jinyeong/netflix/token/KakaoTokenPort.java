package com.jinyeong.netflix.token;

public interface KakaoTokenPort {
    String getAccessTokenByCode(String code);
}
