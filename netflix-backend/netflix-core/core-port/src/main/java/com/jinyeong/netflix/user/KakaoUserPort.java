package com.jinyeong.netflix.user;

public interface KakaoUserPort {
    UserPortResponse findUserFromKakao(String accessToken);
}
