package com.jinyeong.netflix.entity.movie;

import com.jinyeong.netflix.entity.audit.MutableBaseEntity;
import com.jinyeong.netflix.movie.NetflixMovie;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "movies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MovieEntity extends MutableBaseEntity {
    @Id
    @Column(name = "MOVIE_ID")
    private String movieId;

    @Column(name = "MOVIE_NAME")
    private String movieName;

    @Column(name = "IS_ADULT")
    private Boolean isAdult;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "OVERVIEW")
    private String overview;

    @Column(name = "RELEASED_AT")
    private String releasedAt;

    public static MovieEntity newMovieEntity(
            String movieName,
            Boolean isAdult,
            String genre,
            String overview,
            String releasedAt
    ) {
        return new MovieEntity(
                UUID.randomUUID().toString(),
                movieName,
                isAdult,
                genre,
                getSubstrOverview(overview),
                releasedAt
        );
    }

    public NetflixMovie toDomain() {
        return NetflixMovie.builder()
                .movieId(this.movieId)
                .movieName(this.movieName)
                .isAdult(this.isAdult)
                .genre(this.genre)
                .overview(this.overview)
                .releasedAt(this.releasedAt)
                .build();
    }

    public static MovieEntity toEntity(NetflixMovie netflixMovie) {
        return new MovieEntity(
                UUID.randomUUID().toString(),
                netflixMovie.getMovieName(),
                netflixMovie.getIsAdult(),
                netflixMovie.getGenre(),
                getSubstrOverview(netflixMovie.getOverview()),
                netflixMovie.getReleasedAt()
        );
    }

    private static String getSubstrOverview(String overview) {
        if (StringUtils.isBlank(overview)) {
            return "별도의 설명이 존재하지 않습니다.";
        }

        // 저장 길이는 200자내로 제한
        return overview.substring(0, Math.min(overview.length(), 200));
    }
}
