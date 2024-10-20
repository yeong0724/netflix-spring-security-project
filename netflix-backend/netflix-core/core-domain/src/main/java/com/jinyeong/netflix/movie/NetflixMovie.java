package com.jinyeong.netflix.movie;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NetflixMovie {
    private final String movieId;
    private final String movieName;
    private final Boolean isAdult;
    private final String genre;
    private final String overview;
    private final String releasedAt;

    public NetflixMovie(String movieId, String movieName, Boolean isAdult, String genre, String overview, String releasedAt) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.isAdult = isAdult;
        this.genre = genre;
        this.overview = overview;
        this.releasedAt = releasedAt;
    }
}
