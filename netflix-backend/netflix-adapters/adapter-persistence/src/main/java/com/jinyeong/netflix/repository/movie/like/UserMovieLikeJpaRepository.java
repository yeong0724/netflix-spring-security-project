package com.jinyeong.netflix.repository.movie.like;

import com.jinyeong.netflix.entity.movie.UserMovieLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMovieLikeJpaRepository extends JpaRepository<UserMovieLikeEntity, String> {
    Optional<UserMovieLikeEntity> findByUserIdAndMovieId(String userId, String movieId);
}
