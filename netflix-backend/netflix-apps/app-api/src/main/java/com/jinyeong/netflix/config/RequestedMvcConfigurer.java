package com.jinyeong.netflix.config;

import com.jinyeong.netflix.interceptor.RequestedByInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@EnableWebMvc
@RequiredArgsConstructor
public class RequestedMvcConfigurer implements WebMvcConfigurer {
    private final RequestedByInterceptor requestedByInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addWebRequestInterceptor(requestedByInterceptor);
    }
}
