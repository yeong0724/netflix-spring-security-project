package com.jinyeong.netflix.repository.sample;

import com.jinyeong.netflix.entity.sample.SampleEntity;

import java.util.List;

public interface SampleCustomRepository {
    List<SampleEntity> findAllByQueryDsl();
}
