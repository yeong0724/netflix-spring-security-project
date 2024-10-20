package com.jinyeong.netflix.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinyeong.netflix.user.UserHistoryUseCase;
import com.jinyeong.netflix.user.command.UserHistoryCommand;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserHistoryLoggingFilter extends OncePerRequestFilter {
    private final UserHistoryUseCase userHistoryUseCase;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 비동기 처리
        CompletableFuture.runAsync(() -> log(authentication, request));

        filterChain.doFilter(request, response);
    }

    public void log(Authentication authentication, HttpServletRequest httpServletRequest) {
        userHistoryUseCase.createHistory(
                new UserHistoryCommand(
                        authentication.getName(),
                        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")),
                        httpServletRequest.getRemoteAddr(),
                        httpServletRequest.getMethod(),
                        httpServletRequest.getRequestURI(),
                        getHeaders(httpServletRequest),
                        getPayload(httpServletRequest)
                )
        );
    }

    private String getHeaders(HttpServletRequest request) {
        // 헤더를 저장할 Map 생성
        Map<String, String> headersMap = new HashMap<>();

        // 모든 헤더 추출
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                headersMap.put(headerName, headerValue);
            }
        }

        // Map을 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(headersMap);
        } catch (JsonProcessingException e) {
            return "{}"; // 변환 실패 시 빈 JSON 반환
        }
    }

    private String getPayload(HttpServletRequest request) {
        StringBuilder payload = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                payload.append(line);
            }
        } catch (IOException e) {
            logger.error("Error reading payload", e);
            return "";
        }
        return payload.toString();
    }
}
