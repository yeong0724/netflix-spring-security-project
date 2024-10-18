package com.jinyeong.netflix.repository.movie.download;

import com.jinyeong.netflix.util.LocalDateTimeUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.jinyeong.netflix.entity.movie.QUserMovieDownloadEntity.*;

@Repository
@RequiredArgsConstructor
public class UserMovieDownloadCustomRepositoryImpl implements UserMovieDownloadCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long countDownloadToday(String userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTimeUtil.atStartOfDay(now);
        LocalDateTime end = LocalDateTimeUtil.atEndOfDay(now);

        return jpaQueryFactory.selectFrom(userMovieDownloadEntity)
                .where(userMovieDownloadEntity.userId.eq(userId)
                        .and(userMovieDownloadEntity.createdAt.goe(start))
                        .and(userMovieDownloadEntity.createdAt.lt(end)))
                .fetch()
                .size();
    }
}
