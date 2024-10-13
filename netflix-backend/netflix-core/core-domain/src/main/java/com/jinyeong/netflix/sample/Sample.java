package com.jinyeong.netflix.sample;

import lombok.Getter;

@Getter
public class Sample {
    private final String name;

    public Sample(String name) {
        this.name = name;
    }
}
