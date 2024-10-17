package com.jinyeong.netflix.repository.sample;

import com.jinyeong.netflix.entity.sample.QSampleEntity;
import com.jinyeong.netflix.entity.sample.SampleEntity;
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
