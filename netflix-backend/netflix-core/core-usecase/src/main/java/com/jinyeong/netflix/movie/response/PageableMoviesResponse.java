package com.jinyeong.netflix.movie.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PageableMoviesResponse {
    private final List<MovieResponse> movieResponses;
    private final int page;
    private final boolean hasNext;

    public PageableMoviesResponse(List<MovieResponse> movieResponses, int page, boolean hasNext) {
        this.movieResponses = movieResponses;
        this.page = page;
        this.hasNext = hasNext;
    }
}
