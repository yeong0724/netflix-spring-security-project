package com.jinyeong.netflix.user.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private final String userId;

    private final String username;

    private final String password;

    private final String email;

    private final String phone;

    private final String provider;

    private final String providerId;

    private final String role;

    public UserResponse(String userId, String username, String password, String email, String phone, String provider, String providerId, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }
}
