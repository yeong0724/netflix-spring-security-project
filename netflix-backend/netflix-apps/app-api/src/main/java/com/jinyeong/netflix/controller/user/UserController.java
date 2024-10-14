package com.jinyeong.netflix.controller.user;

import com.jinyeong.netflix.controller.NetplixApiResponse;
import com.jinyeong.netflix.controller.user.request.UserRegistrationRequest;
import com.jinyeong.netflix.user.RegisterUserUseCase;
import com.jinyeong.netflix.user.command.UserRegistrationCommand;
import com.jinyeong.netflix.user.response.UserRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/user/register")
    public NetplixApiResponse<UserRegistrationResponse> userRegister(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationResponse userRegistrationResponse = registerUserUseCase.register(
                UserRegistrationCommand.builder()
                        .email(userRegistrationRequest.getEmail())
                        .encryptedPassword(userRegistrationRequest.getPassword())
                        .phone(userRegistrationRequest.getPhone())
                        .username(userRegistrationRequest.getUsername())
                        .build()
        );

        return NetplixApiResponse.ok(userRegistrationResponse);
    }
}
