package com.jinyeong.netflix.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TmdbMovieNowPlayingResponse {
    private TmdbDateResponse dates;

    private int page;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private String totalResults;

    private List<TmdbMovieNowPlaying> results;
}
