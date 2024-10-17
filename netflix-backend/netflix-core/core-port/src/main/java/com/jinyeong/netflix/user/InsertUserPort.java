package com.jinyeong.netflix.user;

public interface InsertUserPort {
    UserPortResponse create(CreateUser createUser);
    UserPortResponse createSocialUser(String username, String provider, String providerId);
}
