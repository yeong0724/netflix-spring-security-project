package com.jinyeong.netflix.controller.movie;

import com.jinyeong.netflix.controller.NetplixApiResponse;
import com.jinyeong.netflix.movie.FetchMovieUseCase;
import com.jinyeong.netflix.movie.response.PageableMoviesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MovieController {
    private final FetchMovieUseCase fetchMovieUseCase;

    public MovieController(FetchMovieUseCase fetchMovieUseCase) {
        this.fetchMovieUseCase = fetchMovieUseCase;
    }

    @GetMapping("/movie/client/{page}")
    public NetplixApiResponse<PageableMoviesResponse> fetchMoviePageable(@PathVariable int page) {
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUseCase.fetchFromClient(page);

        return NetplixApiResponse.ok(pageableMoviesResponse);
    }
}
