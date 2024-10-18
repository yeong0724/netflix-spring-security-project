package com.jinyeong.netflix.movie;

import java.util.List;

public interface PersistenceMoviePort {
    List<NetflixMovie> fetchByPageAndSize(int page, int size);

    NetflixMovie findById(String movieName);

    void insert(NetflixMovie netflixMovie);
}
