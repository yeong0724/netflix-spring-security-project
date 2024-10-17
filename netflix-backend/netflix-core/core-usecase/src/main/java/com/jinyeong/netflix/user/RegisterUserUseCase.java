package com.jinyeong.netflix.user;

import com.jinyeong.netflix.user.command.UserRegistrationCommand;
import com.jinyeong.netflix.user.response.UserRegistrationResponse;

public interface RegisterUserUseCase {
    UserRegistrationResponse register(UserRegistrationCommand request);

    UserRegistrationResponse registerSocialUser(String username, String provider, String providerId);
}
