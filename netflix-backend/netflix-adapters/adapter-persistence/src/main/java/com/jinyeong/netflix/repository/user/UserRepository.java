package com.jinyeong.netflix.repository.user;

import com.jinyeong.netflix.entity.user.SocialUserEntity;
import com.jinyeong.netflix.entity.user.UserEntity;
import com.jinyeong.netflix.user.CreateUser;
import com.jinyeong.netflix.user.FetchUserPort;
import com.jinyeong.netflix.user.InsertUserPort;
import com.jinyeong.netflix.user.UserPortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements FetchUserPort, InsertUserPort {
    private final UserJpaRepository userJpaRepository;
    private final SocialUserJpaRepository socialUserJpaRepository;

    @Override
    public Optional<UserPortResponse> findByEmail(String email) {
        Optional<UserEntity> userByEmail = userJpaRepository.findByEmail(email);
        return userByEmail.map(userEntity -> UserPortResponse.builder()
                .userId(userEntity.getUserId())
                .password(userEntity.getPassword())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .build());
    }

    @Override
    @Transactional
    public Optional<UserPortResponse> findByProviderId(String providerId) {
        Optional<SocialUserEntity> userByProviderId = socialUserJpaRepository.findByProviderId(providerId);

        if (userByProviderId.isEmpty()) {
            return Optional.empty();
        }

        SocialUserEntity socialUserEntity = userByProviderId.get();
        return Optional.of(UserPortResponse.builder()
                .username(socialUserEntity.getUsername())
                .provider(socialUserEntity.getProvider())
                .providerId(socialUserEntity.getProviderId())
                .build());
    }

    @Override
    @Transactional
    public UserPortResponse create(CreateUser createUser) {
        UserEntity userEntity = new UserEntity(
                createUser.getUsername(),
                createUser.getEncryptedPassword(),
                createUser.getEmail(),
                createUser.getPhone()
        );

        UserEntity savedUserEntity = userJpaRepository.save(userEntity);
        return UserPortResponse.builder()
                .userId(savedUserEntity.getUserId())
                .username(savedUserEntity.getUsername())
                .password(savedUserEntity.getPassword())
                .email(savedUserEntity.getEmail())
                .phone(savedUserEntity.getPhone())
                .build();
    }

    @Override
    @Transactional
    public UserPortResponse createSocialUser(String username, String provider, String providerId) {
        SocialUserEntity socialUserEntity = new SocialUserEntity(username, provider, providerId);
        socialUserJpaRepository.save(socialUserEntity);
        return UserPortResponse.builder()
                .username(socialUserEntity.getUsername())
                .provider(socialUserEntity.getProvider())
                .providerId(socialUserEntity.getProviderId())
                .build();
    }
}
