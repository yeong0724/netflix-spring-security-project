package com.jinyeong.netflix.user;

public interface InsertUserPort {
    UserPortResponse create(CreateUser createUser);
}
