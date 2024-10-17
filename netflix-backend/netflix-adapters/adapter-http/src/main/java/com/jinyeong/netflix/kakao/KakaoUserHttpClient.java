package com.jinyeong.netflix.kakao;

import com.jinyeong.netflix.user.KakaoUserPort;
import com.jinyeong.netflix.user.UserPortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoUserHttpClient implements KakaoUserPort {
    @Value("${spring.security.oauth2.kakao.userinfo-api-url}")
    private String KAKAO_USERINFO_API_URL;

    @Override
    public UserPortResponse findUserFromKakao(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);  // 액세스 토큰을 Authorization Header에 추가

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                KAKAO_USERINFO_API_URL,
                HttpMethod.GET,
                httpEntity,
                Map.class
        );

        Long providerId = (Long) response.getBody().get("id");

        Map properties = (Map) response.getBody().get("kakao_account");
        String username = (String) properties.get("name");

        return UserPortResponse.builder()
                .username(username)
                .providerId(providerId.toString())
                .provider("kakao")
                .build();
    }
}
