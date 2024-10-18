package com.jinyeong.netflix.movie;

import com.jinyeong.netflix.movie.response.MovieResponse;

import java.util.List;

public interface InsertMovieUseCase {
    void insert(List<MovieResponse> movies);
}
