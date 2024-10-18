package com.jinyeong.netflix.filter;

import com.jinyeong.netflix.token.FetchTokenUseCase;
import com.jinyeong.netflix.user.command.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        String role = Optional.ofNullable(userByAccessToken.getRole()).orElse("ROLE_ADMIN");
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        String password = Optional.ofNullable(userByAccessToken.getPassword()).orElse("password");
        UserDetails principal = new User(userByAccessToken.getUsername(), password, authorities);
        return new UsernamePasswordAuthenticationToken(principal, userByAccessToken.getUserId(), authorities);
    }
}
