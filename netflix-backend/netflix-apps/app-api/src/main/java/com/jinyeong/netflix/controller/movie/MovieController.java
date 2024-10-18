package com.jinyeong.netflix.controller.movie;

import com.jinyeong.netflix.controller.NetflixApiResponse;
import com.jinyeong.netflix.movie.FetchMovieUseCase;
import com.jinyeong.netflix.movie.response.PageableMoviesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MovieController {
    private final FetchMovieUseCase fetchMovieUseCase;

    @GetMapping("/movie/client/{page}")
    public NetflixApiResponse<PageableMoviesResponse> fetchMoviePageable(@PathVariable int page) {
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUseCase.fetchFromClient(page);
        return NetflixApiResponse.ok(pageableMoviesResponse);
    }

    @GetMapping("/movie/search")
    public NetflixApiResponse<PageableMoviesResponse> searchMovies(@RequestParam int page) {
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUseCase.fetchFromDb(page);
        return NetflixApiResponse.ok(pageableMoviesResponse);
    }
}
