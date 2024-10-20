package com.jinyeong.netflix.movie.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieResponse {
    private final String movieId;
    private final String movieName;
    private final boolean isAdult;
    private final List<String> genre;
    private final String overview;
    private final String releaseAt;

    public MovieResponse(
            String movieId,
            String movieName,
            boolean isAdult,
            List<String> genre,
            String overview,
            String releaseAt
    ) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.isAdult = isAdult;
        this.genre = genre;
        this.overview = overview;
        this.releaseAt = releaseAt;
    }
}
