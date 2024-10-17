package com.jinyeong.netflix.repository.token;

import com.jinyeong.netflix.entity.token.TokenEntity;

import java.util.Optional;

public interface TokenCustomRepository {
    Optional<TokenEntity> findByUserId(String userId);
}
