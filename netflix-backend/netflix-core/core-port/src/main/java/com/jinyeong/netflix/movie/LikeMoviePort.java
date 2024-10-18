package com.jinyeong.netflix.movie;

import java.util.Optional;

public interface LikeMoviePort {
    void save(UserMovieLike userMovieLike);

    Optional<UserMovieLike> findByUserIdAndMovieId(String userId, String movieId);
}
