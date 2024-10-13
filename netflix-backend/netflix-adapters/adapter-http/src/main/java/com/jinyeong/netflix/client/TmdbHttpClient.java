package com.jinyeong.netflix.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * Tmdb Data 를 요청할 때 우리는 항상 Access Token을 붙여서 호출해야 하므로 HttpClient 를 TmdbHttpClient 로 감싸준다.
 */
@Component
@RequiredArgsConstructor
public class TmdbHttpClient {
    @Value("${tmdb.auth.access-token}")
    private String accessToken;

    private final HttpClient httpClient;

    public String request(String uri, HttpMethod httpMethod, MultiValueMap<String, String> headers, Map<String, Object> params) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add(HttpHeaders.ACCEPT, "application/json");
        multiValueMap.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        multiValueMap.addAll(headers);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(multiValueMap);

        return httpClient.request(uri, httpMethod, httpHeaders, params);
    }
}
