package com.jinyeong.netflix.entity.subscription;

import com.jinyeong.netflix.entity.audit.MutableBaseEntity;
import com.jinyeong.netflix.subscription.SubscriptionType;
import com.jinyeong.netflix.subscription.UserSubscription;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "user_subscriptions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserSubscriptionEntity extends MutableBaseEntity {
    @Id
    @Column(name = "USER_SUBSCRIPTION_ID")
    private String userSubscriptionId;

    @Column(name = "USER_ID")
    private String userId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "SUBSCRIPTION_NAME")
    private SubscriptionType subscriptionName;

    @Column(name = "START_AT")
    private LocalDateTime subscriptionStartAt;

    @Column(name = "END_AT")
    private LocalDateTime subscriptionEndAt;

    @Column(name = "VALID_YN")
    private Boolean validYn;

    public UserSubscription toDomain() {
        return UserSubscription.builder()
                .userId(this.userId)
                .subscriptionType(this.subscriptionName)
                .startAt(this.subscriptionStartAt)
                .endAt(this.subscriptionEndAt)
                .validYn(this.validYn)
                .build();
    }

    public static UserSubscriptionEntity toEntity(UserSubscription userSubscription) {
        return new UserSubscriptionEntity(
                UUID.randomUUID().toString(),
                userSubscription.getUserId(),
                userSubscription.getSubscriptionType(),
                userSubscription.getStartAt(),
                userSubscription.getEndAt(),
                userSubscription.getValidYn()
        );
    }
}
