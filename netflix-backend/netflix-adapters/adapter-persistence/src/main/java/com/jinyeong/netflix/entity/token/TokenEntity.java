package com.jinyeong.netflix.entity.token;

import com.jinyeong.netflix.entity.audit.MutableBaseEntity;
import com.jinyeong.netflix.token.NetflixToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenEntity extends MutableBaseEntity {
    @Id
    @Column(name = "TOKEN_ID")
    private String tokenId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "ACCESS_TOKEN_EXPIRES_AT")
    private LocalDateTime accessTokenExpiresAt;

    @Column(name = "REFRESH_TOKEN_EXPIRES_AT")
    private LocalDateTime refreshTokenExpiresAt;

    public TokenEntity(
            String userId,
            String accessToken,
            String refreshToken,
            LocalDateTime accessTokenExpiresAt,
            LocalDateTime refreshTokenExpiresAt
    ) {
        this.tokenId = UUID.randomUUID().toString();
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public void updateToken(String accessToken, String refreshToken) {
        LocalDateTime now = LocalDateTime.now();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresAt = getAccessTokenExpiredAt(now);
        this.refreshTokenExpiresAt = getRefreshTokenExpiredAt(now);
    }

    private static LocalDateTime getAccessTokenExpiredAt(LocalDateTime now) {
        return now.plusMinutes(15);
    }

    private static LocalDateTime getRefreshTokenExpiredAt(LocalDateTime now) {
        return now.plusMinutes(60);
    }

    public NetflixToken toDomain() {
        return NetflixToken.builder()
                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .accessTokenExpireAt(this.accessTokenExpiresAt)
                .refreshTokenExpireAt(this.refreshTokenExpiresAt)
                .build();
    }

    public static TokenEntity toEntity(String userId, String accessToken, String refreshToken) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new TokenEntity(
                userId,
                accessToken,
                refreshToken,
                getAccessTokenExpiredAt(localDateTime),
                getRefreshTokenExpiredAt(localDateTime)
        );
    }
}
