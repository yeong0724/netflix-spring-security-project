package com.jinyeong.netflix.repository;

import com.jinyeong.netflix.entity.SampleEntity;
import com.jinyeong.netflix.sample.SamplePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SampleRepository implements SamplePersistencePort {
    private final SampleJpaRepository sampleJpaRepository;

    @Override
    @Transactional
    public String getSampleName(String id) {
        Optional<SampleEntity> sampleEntity = sampleJpaRepository.findById(id);
        return sampleEntity.map(SampleEntity::getSampleName).orElseThrow();
    }
}
