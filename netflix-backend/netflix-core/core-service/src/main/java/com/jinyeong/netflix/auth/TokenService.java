package com.jinyeong.netflix.auth;

import com.jinyeong.netflix.token.*;
import com.jinyeong.netflix.token.response.TokenResponse;
import com.jinyeong.netflix.user.FetchUserUseCase;
import com.jinyeong.netflix.user.command.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService implements FetchTokenUseCase, UpdateTokenUseCase, CreateTokenUseCase {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expire.access-token}")
    private int accessTokenExpireHour;

    @Value("${jwt.expire.refresh-token}")
    private int refreshTokenExpireHour;

    private final SearchTokenPort searchTokenPort;
    private final InsertTokenPort insertTokenPort;
    private final UpdateTokenPort updateTokenPort;
    private final FetchUserUseCase fetchUserUseCase;
    private final KakaoTokenPort kakaoTokenPort;

    @Override
    public TokenResponse createNewToken(String userId) {
        String accessToken = getToken(userId, Duration.ofHours(accessTokenExpireHour));
        String refreshToken = getToken(userId, Duration.ofHours(refreshTokenExpireHour));
        TokenPortResponse tokenPortResponse = insertTokenPort.create(userId, accessToken, refreshToken);

        return TokenResponse.builder()
                .accessToken(tokenPortResponse.getAccessToken())
                .refreshToken(tokenPortResponse.getRefreshToken())
                .build();
    }

    @Override
    public Boolean validateToken(String accessToken) {
        // accessToken 유효하다면 true를 반환, 그렇지 않다면 Exception 발생
        Jwts.parser()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(accessToken);

        return true;
    }

    @Override
    public String getTokenFromKakao(String code) {
        return kakaoTokenPort.getAccessTokenByCode(code);
    }

    @Override
    public UserResponse findUserByAccessToken(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Object userId = claims.get("userId");

        if (ObjectUtils.isEmpty(userId)) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        UserResponse userByProviderId = fetchUserUseCase.findByProviderId(userId.toString());

        if (userByProviderId != null) {
            return userByProviderId;
        }

        return fetchUserUseCase.findUserByUserId(userId.toString());
    }

    @Override
    public String updateInsertToken(String providerId) {
        TokenPortResponse tokenByUserId = searchTokenPort.findByUserId(providerId);
        String accessToken = getToken(providerId, Duration.ofHours(accessTokenExpireHour));
        String refreshToken = getToken(providerId, Duration.ofHours(refreshTokenExpireHour));

        // create
        if (tokenByUserId == null) {
            insertTokenPort.create(providerId, accessToken, refreshToken);
        }
        // update
        else {
            updateTokenPort.updateToken(providerId, accessToken, refreshToken);
        }

        return accessToken;
    }

    private String getToken(String userId, Duration expireAt) {
        Date now = new Date();
        Instant instant = now.toInstant();

        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(Date.from(instant.plus(expireAt)))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
