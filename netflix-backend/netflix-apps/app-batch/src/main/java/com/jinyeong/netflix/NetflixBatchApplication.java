package com.jinyeong.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NetflixBatchApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(NetflixBatchApplication.class, args);
        SpringApplication.exit(run);
    }
}
