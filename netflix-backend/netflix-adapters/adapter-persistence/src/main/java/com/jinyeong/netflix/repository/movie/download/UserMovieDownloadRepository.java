package com.jinyeong.netflix.repository.movie.download;

import com.jinyeong.netflix.entity.movie.UserMovieDownloadEntity;
import com.jinyeong.netflix.movie.DownloadMoviePort;
import com.jinyeong.netflix.movie.UserMovieDownload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserMovieDownloadRepository implements DownloadMoviePort {
    private final UserMovieDownloadJpaRepository userMovieDownloadJpaRepository;

    @Override
    @Transactional
    public void save(UserMovieDownload userMovieDownload) {
        userMovieDownloadJpaRepository.save(UserMovieDownloadEntity.toEntity(userMovieDownload));
    }

    @Override
    @Transactional
    public long downloadCntToday(String userId) {
        return userMovieDownloadJpaRepository.countDownloadToday(userId);
    }
}
