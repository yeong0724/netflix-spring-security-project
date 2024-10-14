package com.jinyeong.netflix.security;

import com.jinyeong.netflix.user.FetchUserUseCase;
import com.jinyeong.netflix.user.command.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NetflixUserDetailsService implements UserDetailsService {
    private final FetchUserUseCase fetchUserUseCase;

    /**
     * 파라미터로 전달받은 username(현재 프로젝트는 email을 주 ID로 간주) 으로 사용자를 조회 해야 한다.
     * - UseCase를 통해 User 정보 조회
     */
    @Override
    public NetflixAuthUser loadUserByUsername(String email) throws UsernameNotFoundException {
        UserResponse userResponse = fetchUserUseCase.findUserByEmail(email);
        return new NetflixAuthUser(
                userResponse.getUserId(),
                userResponse.getUserName(),
                userResponse.getPassword(),
                userResponse.getEmail(),
                userResponse.getPhone(),
                List.of(new SimpleGrantedAuthority(userResponse.getRole()))
        );
    }
}
