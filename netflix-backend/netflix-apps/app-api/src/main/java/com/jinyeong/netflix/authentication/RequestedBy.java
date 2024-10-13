package com.jinyeong.netflix.authentication;

import lombok.Getter;

@Getter
public class RequestedBy implements Authentication {
    private final String requestedBy;

    public RequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
}
