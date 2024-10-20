package com.jinyeong.netflix.movie;

public interface LikeMovieUseCase {
    void likeMovie(String userId, String movieId);
    void unlikeMovie(String userId, String movieId);
}
