package com.jinyeong.netflix.user;

public interface UserHistoryPort {
    void createHistory(
            String userId,
            String userRole,
            String clientIp,
            String reqMethod,
            String reqUrl,
            String reqHeader,
            String reqPayload
    );
}
