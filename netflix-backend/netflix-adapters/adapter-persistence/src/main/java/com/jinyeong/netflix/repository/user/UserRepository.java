package com.jinyeong.netflix.repository.user;

import com.jinyeong.netflix.entity.user.SocialUserEntity;
import com.jinyeong.netflix.entity.user.UserEntity;
import com.jinyeong.netflix.entity.user.UserHistoryEntity;
import com.jinyeong.netflix.repository.subscription.UserSubscriptionRepository;

import com.jinyeong.netflix.subscription.UserSubscription;
import com.jinyeong.netflix.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.jinyeong.netflix.subscription.SubscriptionType.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository implements FetchUserPort, InsertUserPort, UserHistoryPort {
    private final UserJpaRepository userJpaRepository;
    private final SocialUserJpaRepository socialUserJpaRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserHistoryJpaRepository userHistoryJpaRepository;

    @Override
    public Optional<UserPortResponse> findByUserId(String userId) {
        Optional<UserEntity> userByUserId = userJpaRepository.findByUserId(userId);

        return userByUserId.map(userEntity -> UserPortResponse.builder()
                .userId(userEntity.getUserId())
                .password(userEntity.getPassword())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .build());
    }

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

        Optional<UserSubscription> userBySocialUserId = userSubscriptionRepository.findByUserId(socialUserEntity.getSocialUserId());

        String role = userBySocialUserId
                .map(subscription -> subscription.getSubscriptionType().toRole())
                .orElse(FREE.toRole());

        return Optional.of(UserPortResponse.builder()
                .userId(socialUserEntity.getSocialUserId())
                .username(socialUserEntity.getUsername())
                .provider(socialUserEntity.getProvider())
                .providerId(socialUserEntity.getProviderId())
                .role(role)
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

        // 회원가입시 기본 구독권 등록
        userSubscriptionRepository.create(savedUserEntity.getUserId());

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

        // 소셜회원가입시 기본 구독권 등록
        userSubscriptionRepository.create(socialUserEntity.getSocialUserId());

        return UserPortResponse.builder()
                .username(socialUserEntity.getUsername())
                .provider(socialUserEntity.getProvider())
                .providerId(socialUserEntity.getProviderId())
                .build();
    }

    @Override
    @Transactional
    public void createHistory(
            String userId,
            String userRole,
            String clientIp,
            String reqMethod,
            String reqUrl,
            String reqHeader,
            String reqPayload
    ) {
        UserHistoryEntity userHistoryEntity = new UserHistoryEntity(
                userId, userRole, clientIp, reqMethod, reqUrl, reqHeader, reqPayload
        );

        userHistoryJpaRepository.save(userHistoryEntity);
    }
}
