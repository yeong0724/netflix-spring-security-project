package com.jinyeong.netflix.movie;

import com.jinyeong.netflix.movie.response.PageableMoviesResponse;

public interface FetchMovieUseCase {
    PageableMoviesResponse fetchFromClient(int page);
}
