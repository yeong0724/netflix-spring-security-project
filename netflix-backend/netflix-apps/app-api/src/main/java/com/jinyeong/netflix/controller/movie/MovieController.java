package com.jinyeong.netflix.controller.movie;

import com.jinyeong.netflix.controller.NetflixApiResponse;
import com.jinyeong.netflix.filter.JwtTokenProvider;
import com.jinyeong.netflix.movie.DownloadMovieUseCase;
import com.jinyeong.netflix.movie.FetchMovieUseCase;
import com.jinyeong.netflix.movie.LikeMovieUseCase;
import com.jinyeong.netflix.movie.response.PageableMoviesBatchResponse;
import com.jinyeong.netflix.movie.response.PageableMoviesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MovieController {
    private final FetchMovieUseCase fetchMovieUseCase;
    private final DownloadMovieUseCase downloadMovieUseCase;
    private final LikeMovieUseCase likeMovieUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/movie/client/{page}")
    public NetflixApiResponse<PageableMoviesBatchResponse> fetchMoviePageable(@PathVariable int page) {
        PageableMoviesBatchResponse pageableMoviesBatchResponse = fetchMovieUseCase.fetchFromClient(page);
        return NetflixApiResponse.ok(pageableMoviesBatchResponse);
    }

    @GetMapping("/movie/search")
    public NetflixApiResponse<PageableMoviesResponse> searchMovies(@RequestParam int page) {
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUseCase.fetchFromDb(page);
        return NetflixApiResponse.ok(pageableMoviesResponse);
    }

    /**
     * @PreAuthorize
     * - 해당 어노테이션을 사용하려면 SecurityConfig에 @EnableMethodSecurity가 붙어 있어야 한다.
     * - ROLE 체크에서 해당 API 접근 제한이 걸리면 AuthorizationDeniedException이 던져진다.
     */
    @PostMapping("/movie/{movieId}/download")
    @PreAuthorize("hasAnyRole('ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetflixApiResponse<String> downloadMovie(@PathVariable String movieId) {
        String userId = jwtTokenProvider.getUserId();
        String role = jwtTokenProvider.getRole();
        String download = downloadMovieUseCase.downloadMovie(userId, role, movieId);
        return NetflixApiResponse.ok(download);
    }

    @PostMapping("/movie/{movieId}/like")
    @PreAuthorize("hasAnyRole('ROLE_FREE', 'ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetflixApiResponse<String> likeMovie(@PathVariable String movieId) {
        String userId = jwtTokenProvider.getUserId();
        likeMovieUseCase.likeMovie(userId, movieId);
        return NetflixApiResponse.ok("");
    }

    @PostMapping("/movie/{movieId}/unlike")
    @PreAuthorize("hasAnyRole('ROLE_FREE', 'ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetflixApiResponse<String> unlikeMovie(@PathVariable String movieId) {
        String userId = jwtTokenProvider.getUserId();
        likeMovieUseCase.unlikeMovie(userId, movieId);
        return NetflixApiResponse.ok("");
    }
}
