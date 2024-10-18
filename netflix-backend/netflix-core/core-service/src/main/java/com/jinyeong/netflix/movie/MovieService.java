package com.jinyeong.netflix.movie;

import com.jinyeong.netflix.movie.response.MovieResponse;
import com.jinyeong.netflix.movie.response.PageableMoviesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieService implements FetchMovieUseCase, InsertMovieUseCase {
    private final TmdbMoviePort tmdbMoviePort;
    private final PersistenceMoviePort persistenceMoviePort;

    public MovieService(TmdbMoviePort tmdbMoviePort, PersistenceMoviePort persistenceMoviePort) {
        this.tmdbMoviePort = tmdbMoviePort;
        this.persistenceMoviePort = persistenceMoviePort;
    }

    @Override
    public PageableMoviesResponse fetchFromClient(int page) {
        TmdbPageableMovies tmdbPageableMovies = tmdbMoviePort.fetchPageable(page);
        return new PageableMoviesResponse(
                tmdbPageableMovies.getTmdbMovies().stream()
                        .map(movie -> new MovieResponse(
                                movie.getMovieName(),
                                movie.isAdult(),
                                movie.getGenre(),
                                movie.getOverview(),
                                movie.getReleaseAt()
                        )).collect(Collectors.toList()),
                tmdbPageableMovies.getPage(),
                tmdbPageableMovies.isHasNext()
        );
    }

    @Override
    public void insert(List<MovieResponse> movies) {
        movies.forEach(movie -> {
            NetflixMovie netflixMovie = NetflixMovie.builder()
                    .movieName(movie.getMovieName())
                    .isAdult(movie.isAdult())
                    .overview(movie.getOverview())
                    .releasedAt(movie.getReleaseAt())
                    .genre(String.join(",", movie.getGenre()))
                    .build();

            persistenceMoviePort.insert(netflixMovie);
        });

    }
}
