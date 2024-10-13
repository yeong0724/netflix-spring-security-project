package com.jinyeong.netflix.movie;

import com.jinyeong.netflix.movie.response.MovieResponse;
import com.jinyeong.netflix.movie.response.PageableMoviesResponse;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MovieService implements FetchMovieUseCase {
    private final TmdbMoviePort tmdbMoviePort;

    public MovieService(TmdbMoviePort tmdbMoviePort) {
        this.tmdbMoviePort = tmdbMoviePort;
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
}
