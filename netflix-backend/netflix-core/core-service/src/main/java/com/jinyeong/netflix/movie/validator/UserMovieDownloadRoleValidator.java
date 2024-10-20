package com.jinyeong.netflix.movie.validator;

public interface UserMovieDownloadRoleValidator {
    boolean validate(long count);
    boolean isTarget(String role);
}
