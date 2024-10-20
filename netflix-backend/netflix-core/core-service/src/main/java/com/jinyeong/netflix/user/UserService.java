package com.jinyeong.netflix.user;

import com.jinyeong.netflix.exception.UserException;
import com.jinyeong.netflix.user.command.UserRegistrationCommand;
import com.jinyeong.netflix.user.command.UserResponse;
import com.jinyeong.netflix.user.response.UserRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService implements FetchUserUseCase, RegisterUserUseCase {
    private final FetchUserPort fetchUserPort;
    private final InsertUserPort insertUserPort;
    private final KakaoUserPort kakaoUserPort;

    @Override
    public UserResponse findUserByUserId(String userId) {
        return findUserBy(userId, fetchUserPort::findByUserId);
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        return findUserBy(email, fetchUserPort::findByEmail);
    }

    private UserResponse findUserBy(String searchParam, Function<String, Optional<UserPortResponse>> searchFunction) {
        return searchFunction.apply(searchParam)
                .map(this::mapToUserResponse)
                .orElseThrow(UserException.UserDoesNotExistException::new);
    }

    private UserResponse mapToUserResponse(UserPortResponse userPortResponse) {
        return UserResponse.builder()
                .userId(userPortResponse.getUserId())
                .email(userPortResponse.getEmail())
                .password(userPortResponse.getPassword())
                .phone(userPortResponse.getPhone())
                .role(userPortResponse.getRole())
                .username(userPortResponse.getUsername())
                .build();
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationCommand userRegistrationCommand) {
        String email = userRegistrationCommand.getEmail();

        // 가입 되어 있는 회원임을 검증하기 위한 회원 조회
        Optional<UserPortResponse> userByEmail = fetchUserPort.findByEmail(email);

        // 기존 회원이 있으면 예외 던짐
        if (userByEmail.isPresent()) {
            throw new UserException.UserAlreadyExistException();
        }

        UserPortResponse userPortResponse = insertUserPort.create(
                CreateUser.builder()
                        .username(userRegistrationCommand.getUsername())
                        .encryptedPassword(userRegistrationCommand.getEncryptedPassword())
                        .email(userRegistrationCommand.getEmail())
                        .phone(userRegistrationCommand.getPhone())
                        .build()
        );

        return new UserRegistrationResponse(
                userPortResponse.getUsername(),
                userPortResponse.getEmail(),
                userPortResponse.getPhone()
        );
    }

    @Override
    public UserRegistrationResponse registerSocialUser(String username, String provider, String providerId) {
        Optional<UserPortResponse> userByProviderId = fetchUserPort.findByProviderId(providerId);
        if (userByProviderId.isPresent()) {
            return null;
        }

        UserPortResponse socialUser = insertUserPort.createSocialUser(username, provider, providerId);
        return new UserRegistrationResponse(socialUser.getUsername(), null, null);
    }

    @Override
    public UserResponse findByProviderId(String providerId) {

        return fetchUserPort.findByProviderId(providerId)
                .map(userByProviderId -> UserResponse.builder()
                        .userId(userByProviderId.getUserId())
                        .providerId(userByProviderId.getProviderId())
                        .provider(userByProviderId.getProvider())
                        .username(userByProviderId.getUsername())
                        .role(userByProviderId.getRole())
                        .build())
                .orElse(null);
    }

    @Override
    public UserResponse findKakaoUser(String accessToken) {
        UserPortResponse userPortResponse = kakaoUserPort.findUserFromKakao(accessToken);

        return UserResponse.builder()
                .username(userPortResponse.getUsername())
                .provider(userPortResponse.getProvider())
                .providerId(userPortResponse.getProviderId())
                .build();
    }
}
