package com.example.rate_service.service;

import com.example.rate_service.dto.ExchangeRateResponse;
import com.example.rate_service.dto.RateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateService {
    @Qualifier("webClient")
    private final WebClient webClient;

    @Value("${exchange-rate.api.key}")
    private String key;
    @Value("${exchange-rate.api.url}")
    private String url;

    public RateResponse getExchangeRate(String from , String to) {
        try {
            ExchangeRateResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/latest/{from}")
                            .build(from))
                    .header("Authorization", "Bearer " +key)
                    .retrieve()
                    .bodyToMono(ExchangeRateResponse.class)
                    .block();
            if (response == null || response.getRates() == null || response.getRates().isEmpty()) {
                log.error("getExchangeRate failed");

            }
            Double rate = response.getRates().get(to.toUpperCase());
            if (rate == null) {
                log.error("getExchangeRate failed");
            }
            return new RateResponse(from.toUpperCase(), to.toUpperCase(), rate);

        }catch (WebClientException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }


}
