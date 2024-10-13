package com.jinyeong.netflix.repository;

import com.jinyeong.netflix.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleJpaRepository extends JpaRepository<SampleEntity, String> {
}
