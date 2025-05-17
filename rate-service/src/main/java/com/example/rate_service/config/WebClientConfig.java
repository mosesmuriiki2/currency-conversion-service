package com.example.rate_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("https://api.exchangerate-api.com/v4")
                .defaultHeader("Content-Type", "application/json")
                .build();

    }

    //main-service to call rate-service
    @Bean(name = "rateServiceClient")
    public WebClient rateServiceWebClient() {
        return WebClient.builder().baseUrl("http://rate-service:8067")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
