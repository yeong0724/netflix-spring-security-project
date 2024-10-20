package com.jinyeong.netflix.repository.movie.movie;

import com.jinyeong.netflix.entity.movie.MovieEntity;
import com.jinyeong.netflix.movie.NetflixMovie;
import com.jinyeong.netflix.movie.PersistenceMoviePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MovieRepository implements PersistenceMoviePort {
    private final MovieJpaRepository movieJpaRepository;

    @Override
    @Transactional
    public List<NetflixMovie> fetchByPageAndSize(int page, int size) {
        return movieJpaRepository.search(PageRequest.of(page, size))
                .stream()
                .map(MovieEntity::toDomain)
                .toList();
    }

    @Override
    public NetflixMovie findByMovieName(String movieName) {
        return movieJpaRepository.findByMovieName(movieName)
                .map(MovieEntity::toDomain)
                .orElse(null);
    }

    @Override
    public NetflixMovie findByMovieId(String movieId) {
        return movieJpaRepository.findByMovieId(movieId)
                .map(MovieEntity::toDomain)
                .orElse(null);
    }

    @Override
    @Transactional
    public void insert(NetflixMovie netflixMovie) {
        Optional<MovieEntity> movieByMovieName = movieJpaRepository.findByMovieName(netflixMovie.getMovieName());

        // 기존에 있는 영화 데이터라면 그냥 Pass
        if (movieByMovieName.isPresent()) return;

        log.info("신규 영화 추가 = {}", netflixMovie.getMovieName());
        MovieEntity movieEntity = MovieEntity.toEntity(netflixMovie);
        movieJpaRepository.save(movieEntity);
    }
}
