package com.jinyeong.netflix.controller.user;

import com.jinyeong.netflix.controller.NetflixApiResponse;
import com.jinyeong.netflix.controller.user.request.UserLoginRequest;
import com.jinyeong.netflix.controller.user.request.UserRegistrationRequest;
import com.jinyeong.netflix.security.NetflixAuthUser;
import com.jinyeong.netflix.user.RegisterUserUseCase;
import com.jinyeong.netflix.user.command.UserRegistrationCommand;
import com.jinyeong.netflix.user.response.UserRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/user/register")
    public NetflixApiResponse<UserRegistrationResponse> userRegister(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationResponse userRegistrationResponse = registerUserUseCase.register(
                UserRegistrationCommand.builder()
                        .email(userRegistrationRequest.getEmail())
                        .encryptedPassword(userRegistrationRequest.getPassword())
                        .phone(userRegistrationRequest.getPhone())
                        .username(userRegistrationRequest.getUsername())
                        .build()
        );

        return NetflixApiResponse.ok(userRegistrationResponse);
    }

    @PostMapping("/user/login")
    public NetflixApiResponse<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        /**
         * 1. AuthenticationManagerBuilder에서 구성된 AuthenticationManager 인스턴스를 가져온다.
         * 2. AuthenticationManager의 authenticate 메서드를 호출하여 실제 인증을 수행하게 되는데 현재 프로젝트 기준으로
         *    UserDetailsService를 구현한 NetflixUserDetailsService의 loadUserByUsername 메서드를 통하여 주어진 email에 대한 User를 조회한다.
         * 3. PasswordEncoder를 통해 입력받은 Password와 DB에서 조회한 User의 Password를 검증한다.
         * 4. 여기서 일치하지 않으면 (org.springframework.security.authentication.BadCredentialsException: 자격 증명에 실패하였습니다.)
         *    라는 exception이 발생한다.
         * 5. 입력받은 email에 대한 User가 없다면 loadUserByUsername 메서드 단계에서 예외를 던져 준다.
         */
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        NetflixAuthUser netflixAuthUser = (NetflixAuthUser) authentication.getPrincipal();

        return NetflixApiResponse.ok("access-token");
    }
}
