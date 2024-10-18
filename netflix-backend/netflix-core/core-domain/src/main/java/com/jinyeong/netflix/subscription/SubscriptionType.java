package com.jinyeong.netflix.subscription;

import lombok.Getter;

@Getter
public enum SubscriptionType {
    FREE("무료 구독권"), // 영화 조회만 가능
    BRONZE("브론즈 구독권"), // 영화 조회 + 다운로드 5회 + 좋아요/싫어요
    SILVER("실버 구독권"), // 영화 조회 + 다운로드 10회 + 좋아요/싫어요
    GOLD("골드 구독권"); // 영화 조회 + 다운로드 무제한 + 좋아요/싫어요

    private final String desc;

    SubscriptionType(String desc) {
        this.desc = desc;
    }

    public String toRole() {
        return "ROLE_" + this.name();
    }
}
