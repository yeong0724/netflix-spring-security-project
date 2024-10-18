package com.jinyeong.netflix.config;

import com.jinyeong.netflix.filter.JwtAuthenticationFilter;
import com.jinyeong.netflix.security.NetflixUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final NetflixUserDetailsService netflixUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .userDetailsService(netflixUserDetailsService)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/", "/register", "/api/v1/user/**", "/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.failureUrl("/login?error=true"))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();

            // 모든 헤더를 허용
            config.setAllowedHeaders(Collections.singletonList("*"));

            // 모든 HTTP 메서드를 허용 (GET, POST, PUT, DELETE 등)
            config.setAllowedMethods(Collections.singletonList("*"));

            // 모든 출처(origin)를 허용. 보안상 위험할 수 있음
            config.setAllowedOriginPatterns(Collections.singletonList("*"));

            // 인증 정보(쿠키, HTTP 인증)를 포함한 요청을 허용
            config.setAllowCredentials(true);

            return config;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
