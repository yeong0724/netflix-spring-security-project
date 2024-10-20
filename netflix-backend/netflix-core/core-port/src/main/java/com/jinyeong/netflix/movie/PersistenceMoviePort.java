package com.jinyeong.netflix.movie;

import java.util.List;

public interface PersistenceMoviePort {
    List<NetflixMovie> fetchByPageAndSize(int page, int size);

    NetflixMovie findByMovieName(String movieName);

    NetflixMovie findByMovieId(String movieId);

    void insert(NetflixMovie netflixMovie);
}
