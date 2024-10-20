package com.jinyeong.netflix.filter;

import com.jinyeong.netflix.token.FetchTokenUseCase;
import com.jinyeong.netflix.user.command.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final FetchTokenUseCase fetchTokenUseCase;

    public Authentication getAuthentication(String accessToken) {
        UserResponse userByAccessToken = fetchTokenUseCase.findUserByAccessToken(accessToken);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userByAccessToken.getRole()));
        String password = Optional.ofNullable(userByAccessToken.getPassword()).orElse("password");
        UserDetails principal = new User(userByAccessToken.getUsername(), password, authorities);
        return new UsernamePasswordAuthenticationToken(principal, userByAccessToken.getUserId(), authorities);
    }

    public String getUserId() {
        /**
         * SecurityContextHolder의 Context는 getAuthentication 메서드에서 UsernamePasswordAuthenticationToken 객체를 생성할때 세팅되는 값이다.
         * - new UsernamePasswordAuthenticationToken(principal, userId, authorities)
         * - principal: 사용자에 대한 정보
         * - userId: 사용자 PK
         * - authorities: ROLE(권한)
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getCredentials();
    }

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().findFirst().orElseThrow(RuntimeException::new).getAuthority();
    }
}
