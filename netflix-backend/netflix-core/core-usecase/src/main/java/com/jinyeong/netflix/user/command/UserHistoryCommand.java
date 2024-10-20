package com.jinyeong.netflix.user.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserHistoryCommand {
    private final String userId;
    private final String userRole;
    private final String clientIp;
    private final String reqMethod;
    private final String reqUrl;
    private final String reqHeader;
    private final String reqPayload;

    public UserHistoryCommand(String userId, String userRole, String clientIp, String reqMethod, String reqUrl, String reqHeader, String reqPayload) {
        this.userId = userId;
        this.userRole = userRole;
        this.clientIp = clientIp;
        this.reqMethod = reqMethod;
        this.reqUrl = reqUrl;
        this.reqHeader = reqHeader;
        this.reqPayload = reqPayload;
    }
}
