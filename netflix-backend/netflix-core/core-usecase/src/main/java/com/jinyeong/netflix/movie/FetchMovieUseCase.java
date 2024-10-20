package com.jinyeong.netflix.movie;

import com.jinyeong.netflix.movie.response.PageableMoviesBatchResponse;
import com.jinyeong.netflix.movie.response.PageableMoviesResponse;

public interface FetchMovieUseCase {
    PageableMoviesBatchResponse fetchFromClient(int page);

    PageableMoviesResponse fetchFromDb(int page);
}
