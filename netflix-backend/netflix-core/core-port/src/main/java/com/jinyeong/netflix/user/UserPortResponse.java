package com.jinyeong.netflix.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPortResponse {
    private String userId;

    private String userName;

    private String password;

    private String email;

    private String phone;

    private String provider;

    private String providerId;

    private String role;
}
