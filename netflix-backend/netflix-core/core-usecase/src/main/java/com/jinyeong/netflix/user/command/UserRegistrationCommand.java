package com.jinyeong.netflix.user.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegistrationCommand {
    private final String username;

    private final String encryptedPassword;

    private final String email;

    private final String phone;

    public UserRegistrationCommand(String username, String encryptedPassword, String email, String phone) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.email = email;
        this.phone = phone;
    }
}
