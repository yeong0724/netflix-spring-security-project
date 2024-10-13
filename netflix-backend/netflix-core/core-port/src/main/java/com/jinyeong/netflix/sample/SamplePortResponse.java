package com.jinyeong.netflix.sample;

import lombok.Getter;

@Getter
public class SamplePortResponse {
    private final String name;

    public SamplePortResponse(String name) {
        this.name = name;
    }
}
