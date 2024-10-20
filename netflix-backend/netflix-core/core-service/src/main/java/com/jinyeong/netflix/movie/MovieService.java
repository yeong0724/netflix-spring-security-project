package com.jinyeong.netflix.movie;

import com.jinyeong.netflix.exception.ErrorCode;
import com.jinyeong.netflix.exception.NetflixException;
import com.jinyeong.netflix.movie.response.MovieBatchResponse;
import com.jinyeong.netflix.movie.response.MovieResponse;
import com.jinyeong.netflix.movie.response.PageableMoviesBatchResponse;
import com.jinyeong.netflix.movie.response.PageableMoviesResponse;
import com.jinyeong.netflix.movie.validator.UserMovieDownloadRoleValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService implements FetchMovieUseCase, InsertMovieUseCase, DownloadMovieUseCase, LikeMovieUseCase {
    private final TmdbMoviePort tmdbMoviePort;
    private final PersistenceMoviePort persistenceMoviePort;
    private final DownloadMoviePort downloadMoviePort;
    private final LikeMoviePort likeMoviePort;
    private final List<UserMovieDownloadRoleValidator> userMovieDownloadRoleValidators; // UserMovieDownloadRoleValidator을 구현한 스프링 빈이 List로 자동 등록

    @Override
    public PageableMoviesBatchResponse fetchFromClient(int page) {
        TmdbPageableMovies tmdbPageableMovies = tmdbMoviePort.fetchPageable(page);
        return new PageableMoviesBatchResponse(
                tmdbPageableMovies.getTmdbMovies().stream()
                        .map(movie -> new MovieBatchResponse(
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
    public PageableMoviesResponse fetchFromDb(int page) {
        List<NetflixMovie> moviesByPageAndSize = persistenceMoviePort.fetchByPageAndSize(page, 10);

        return new PageableMoviesResponse(
                moviesByPageAndSize.stream().map(movie -> new MovieResponse(
                        movie.getMovieId(),
                        movie.getMovieName(),
                        movie.getIsAdult(),
                        StringToList(movie.getGenre()),
                        movie.getOverview(),
                        movie.getReleasedAt()
                )).toList(),
                page,
                true
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

    @Override
    public String downloadMovie(String userId, String role, String movieId) {
        long currentDownloadCount = downloadMoviePort.downloadCntToday(userId);
        boolean validate = userMovieDownloadRoleValidators.stream()
                .filter(validator -> validator.isTarget(role))
                .findFirst()
                .orElseThrow()
                .validate(currentDownloadCount);

        if (!validate) {
            throw new NetflixException(ErrorCode.NO_MORE_MOVIE_DOWNLOAD);
        }

        NetflixMovie movieByMovieId = persistenceMoviePort.findByMovieId(movieId);
        downloadMoviePort.save(UserMovieDownload.newDownload(userId, movieId));
        return movieByMovieId.getMovieName();
    }

    @Override
    public void likeMovie(String userId, String movieId) {
        Optional<UserMovieLike> byUserIdAndMovieId = likeMoviePort.findByUserIdAndMovieId(userId, movieId);
        if (byUserIdAndMovieId.isEmpty()) {
            likeMoviePort.save(UserMovieLike.newLike(userId, movieId));
        } else {
            UserMovieLike userMovieLike = byUserIdAndMovieId.get();
            userMovieLike.like();
            likeMoviePort.save(userMovieLike);
        }
    }

    @Override
    public void unlikeMovie(String userId, String movieId) {
        Optional<UserMovieLike> byUserIdAndMovieId = likeMoviePort.findByUserIdAndMovieId(userId, movieId);
        if (byUserIdAndMovieId.isEmpty()) {
            likeMoviePort.save(UserMovieLike.newLike(userId, movieId));
        } else {
            UserMovieLike userMovieLike = byUserIdAndMovieId.get();
            userMovieLike.unlike();
            likeMoviePort.save(userMovieLike);
        }
    }

    private static List<String> StringToList(String genre) {
        return Optional.ofNullable(genre)
                .map(stringGenre -> Arrays.asList(stringGenre.split(",")))
                .orElse(Collections.emptyList());
    }
}
