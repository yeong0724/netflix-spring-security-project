package com.jinyeong.netflix.movie;

public interface DownloadMoviePort {
    void save(UserMovieDownload domain);

    long downloadCntToday(String userId);
}
