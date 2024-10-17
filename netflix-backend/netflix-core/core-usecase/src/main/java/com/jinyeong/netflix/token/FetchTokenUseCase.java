package com.jinyeong.netflix.token;

import com.jinyeong.netflix.user.command.UserResponse;

public interface FetchTokenUseCase {
    Boolean validateToken(String accessToken);

    String getTokenFromKakao(String code);

    UserResponse findUserByAccessToken(String accessToken);
}
