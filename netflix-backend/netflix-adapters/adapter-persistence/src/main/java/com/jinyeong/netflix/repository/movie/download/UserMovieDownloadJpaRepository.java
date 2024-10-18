package com.jinyeong.netflix.repository.movie.download;

import com.jinyeong.netflix.entity.movie.UserMovieDownloadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMovieDownloadJpaRepository extends JpaRepository<UserMovieDownloadEntity, String>, UserMovieDownloadCustomRepository {
}
