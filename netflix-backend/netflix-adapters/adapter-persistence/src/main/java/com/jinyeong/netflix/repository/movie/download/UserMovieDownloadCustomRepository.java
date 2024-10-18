package com.jinyeong.netflix.repository.movie.download;

public interface UserMovieDownloadCustomRepository {
    long countDownloadToday(String userId);
}
