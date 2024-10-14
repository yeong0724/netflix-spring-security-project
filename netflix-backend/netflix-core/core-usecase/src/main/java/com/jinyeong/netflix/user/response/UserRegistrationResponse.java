package com.jinyeong.netflix.user.response;

import lombok.Getter;

@Getter
public class UserRegistrationResponse {
    private final String username;

    private final String email;

    private final String phone;

    public UserRegistrationResponse(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }
}
