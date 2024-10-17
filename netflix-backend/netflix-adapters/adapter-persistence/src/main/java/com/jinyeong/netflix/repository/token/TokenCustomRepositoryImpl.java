package com.jinyeong.netflix.repository.token;

import com.jinyeong.netflix.entity.token.QTokenEntity;
import com.jinyeong.netflix.entity.token.TokenEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenCustomRepositoryImpl implements TokenCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<TokenEntity> findByUserId(String userId) {
        // QTokenEntity 생성을 위해 코드 작성 전에 build를 한번 진행해 줄것
        return jpaQueryFactory.selectFrom(QTokenEntity.tokenEntity)
                .where(QTokenEntity.tokenEntity.userId.eq(userId))
                .fetch()
                .stream().findFirst();
    }
}
