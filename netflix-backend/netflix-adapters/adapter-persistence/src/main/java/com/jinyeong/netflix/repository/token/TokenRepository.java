package com.jinyeong.netflix.repository.token;

import com.jinyeong.netflix.entity.token.TokenEntity;
import com.jinyeong.netflix.token.InsertTokenPort;
import com.jinyeong.netflix.token.SearchTokenPort;
import com.jinyeong.netflix.token.TokenPortResponse;
import com.jinyeong.netflix.token.UpdateTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepository implements SearchTokenPort, InsertTokenPort, UpdateTokenPort {
    private final TokenJpaRepository tokenJpaRepository;

    @Override
    @Transactional
    public TokenPortResponse create(String userId, String accessToken, String refreshToken) {
        TokenEntity tokenEntity = TokenEntity.toEntity(userId, accessToken, refreshToken);
        tokenJpaRepository.save(tokenEntity).toDomain();

        return new TokenPortResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public TokenPortResponse findByUserId(String userId) {
        return tokenJpaRepository.findByUserId(userId)
                .map(result -> new TokenPortResponse(
                        result.getAccessToken(), result.getRefreshToken())
                )
                .orElse(null);
    }

    @Override
    @Transactional
    public void updateToken(String userId, String accessToken, String refreshToken) {
        Optional<TokenEntity> byUserId = tokenJpaRepository.findByUserId(userId);
        if (byUserId.isEmpty()) {
            throw new RuntimeException();
        }

        TokenEntity tokenEntity = byUserId.get();
        tokenEntity.updateToken(accessToken, refreshToken);
        tokenJpaRepository.save(tokenEntity);
    }
}
