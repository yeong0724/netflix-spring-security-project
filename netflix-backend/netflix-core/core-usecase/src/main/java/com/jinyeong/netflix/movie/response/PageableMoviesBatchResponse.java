package com.jinyeong.netflix.movie.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PageableMoviesBatchResponse {
    private final List<MovieBatchResponse> movieBatchResponses;
    private final int page;
    private final boolean hasNext;

    public PageableMoviesBatchResponse(List<MovieBatchResponse> movieBatchResponses, int page, boolean hasNext) {
        this.movieBatchResponses = movieBatchResponses;
        this.page = page;
        this.hasNext = hasNext;
    }
}
