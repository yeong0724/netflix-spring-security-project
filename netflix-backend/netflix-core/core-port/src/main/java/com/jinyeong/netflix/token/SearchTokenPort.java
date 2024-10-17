package com.jinyeong.netflix.token;

public interface SearchTokenPort {
    TokenPortResponse findByUserId(String userId);
}
