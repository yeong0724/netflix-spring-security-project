package com.jinyeong.netflix.token;

import com.jinyeong.netflix.token.response.TokenResponse;

public interface CreateTokenUseCase {
    TokenResponse createNewToken(String userId);
}
