package com.jinyeong.netflix.user;

import com.jinyeong.netflix.user.command.UserResponse;

public interface FetchUserUseCase {
    UserResponse findUserByEmail(String email);

    UserResponse findByProviderId(String providerId);

    UserResponse findKakaoUser(String accessToken);
}
