package com.jinyeong.netflix.movie;

import lombok.Getter;

import java.util.List;

@Getter
public class TmdbMovie {
    private final String movieName;
    private final boolean isAdult;
    private final List<String> genre;
    private final String overview;
    private final String releaseAt;

    public TmdbMovie(String movieName, boolean isAdult, List<String> genre, String overview, String releaseAt) {
        this.movieName = movieName;
        this.isAdult = isAdult;
        this.genre = genre;
        this.overview = overview;
        this.releaseAt = releaseAt;
    }
}
