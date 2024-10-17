package com.jinyeong.netflix.token;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NetflixToken {
    private String accessToken;
    private String refreshToken;
    private final LocalDateTime accessTokenExpireAt;
    private final LocalDateTime refreshTokenExpireAt;

    public NetflixToken(
            String accessToken,
            String refreshToken,
            LocalDateTime accessTokenExpireAt,
            LocalDateTime refreshTokenExpireAt
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireAt = accessTokenExpireAt;
        this.refreshTokenExpireAt = refreshTokenExpireAt;
    }
}
