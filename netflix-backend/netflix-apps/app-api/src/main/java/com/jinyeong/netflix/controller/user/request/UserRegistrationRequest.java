package com.jinyeong.netflix.controller.user.request;

import com.jinyeong.netflix.annotation.PasswordEncryption;
import lombok.Getter;

@Getter
public class UserRegistrationRequest {
    private final String username;

    @PasswordEncryption
    private String password;

    private final String email;

    private final String phone;

    public UserRegistrationRequest(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
