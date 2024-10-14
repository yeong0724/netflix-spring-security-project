package com.jinyeong.netflix.user;

import com.jinyeong.netflix.user.command.UserResponse;

public interface FetchUserUseCase {
    UserResponse findUserByEmail(String email);
}
