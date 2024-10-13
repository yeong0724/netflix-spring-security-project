package com.jinyeong.netflix.movie;

public interface TmdbMoviePort {
    TmdbPageableMovies fetchPageable(int page);
}
