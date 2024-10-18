package com.jinyeong.netflix.subscription;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserSubscription {
    private String userId;
    private SubscriptionType subscriptionType;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean validYn;

    public UserSubscription(
            String userId,
            SubscriptionType subscriptionType,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Boolean validYn
    ) {
        this.userId = userId;
        this.subscriptionType = subscriptionType;
        this.startAt = startAt;
        this.endAt = endAt;
        this.validYn = validYn;
    }

    /**
     * 구독권 종료
     */
    public void off() {
        this.validYn = false;
    }

    /**
     * 구독권 갱신
     */
    public void renew() {
        this.startAt = LocalDateTime.now();
        this.endAt = getEndAt(this.startAt);
        this.validYn = true;
    }

    /**
     * 구독권 등급 변경
     */
    public void change(SubscriptionType type) {
        this.subscriptionType = type;
    }

    /**
     * 갱신 가능 여부 판별
     */
    public boolean ableToRenew() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endAt);
    }

    /**
     * 구독권 변경 가능 판별
     */
    public boolean ableToChange() {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(endAt) && now.isAfter(startAt) && validYn;
    }

    private LocalDateTime getEndAt(LocalDateTime startAt) {
        return startAt.plusMonths(1L);
    }

    public static UserSubscription newSubscription(String userId) {
        LocalDateTime now = LocalDateTime.now();
        return UserSubscription.builder()
                .userId(userId)
                .subscriptionType(SubscriptionType.FREE)
                .startAt(now)
                .endAt(now.plusMonths(1L))
                .validYn(true)
                .build();
    }
}
