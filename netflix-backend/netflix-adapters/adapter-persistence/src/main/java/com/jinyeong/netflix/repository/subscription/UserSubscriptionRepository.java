package com.jinyeong.netflix.repository.subscription;

import com.jinyeong.netflix.entity.subscription.UserSubscriptionEntity;
import com.jinyeong.netflix.subscription.FetchUserSubscriptionPort;
import com.jinyeong.netflix.subscription.InsertUserSubscriptionPort;
import com.jinyeong.netflix.subscription.UpdateUserSubscriptionPort;
import com.jinyeong.netflix.subscription.UserSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserSubscriptionRepository implements UpdateUserSubscriptionPort, InsertUserSubscriptionPort, FetchUserSubscriptionPort {
    private final UserSubscriptionJpaRepository userSubscriptionJpaRepository;

    @Override
    @Transactional
    public Optional<UserSubscription> findByUserId(String userId) {
        Optional<UserSubscriptionEntity> userSubscriptionEntity = userSubscriptionJpaRepository.findByUserId(userId);
        return userSubscriptionEntity.map(UserSubscriptionEntity::toDomain);
    }

    @Override
    @Transactional
    public void create(String userId) {
        UserSubscription userSubscription = UserSubscription.newSubscription(userId);
        userSubscriptionJpaRepository.save(UserSubscriptionEntity.toEntity(userSubscription));
    }

    @Override
    @Transactional
    public void update(UserSubscription userSubscription) {
        userSubscriptionJpaRepository.save(UserSubscriptionEntity.toEntity(userSubscription));
    }
}
