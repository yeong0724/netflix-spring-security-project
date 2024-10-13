package com.jinyeong.netflix.repository;

import com.jinyeong.netflix.entity.SampleEntity;

import java.util.List;

public interface SampleCustomRepository {
    List<SampleEntity> findAllByQueryDsl();
}
