package com.jinyeong.netflix.repository;

import com.jinyeong.netflix.entity.QSampleEntity;
import com.jinyeong.netflix.entity.SampleEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SampleCustomRepositoryImpl implements SampleCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SampleEntity> findAllByQueryDsl() {
        return jpaQueryFactory.selectFrom(QSampleEntity.sampleEntity).fetch();
    }
}
