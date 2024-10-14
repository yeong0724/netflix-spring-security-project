package com.jinyeong.netflix.user;

import java.util.Optional;

public interface FetchUserPort {
    Optional<UserPortResponse> findByEmail(String email);
}
