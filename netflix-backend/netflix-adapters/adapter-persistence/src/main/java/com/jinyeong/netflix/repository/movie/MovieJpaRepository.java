package com.jinyeong.netflix.repository.movie;

import com.jinyeong.netflix.entity.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieJpaRepository extends JpaRepository<MovieEntity, String>, MovieCustomRepository {
}
