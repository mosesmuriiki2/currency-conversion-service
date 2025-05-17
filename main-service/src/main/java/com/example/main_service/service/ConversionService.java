package com.example.main_service.service;

import com.example.main_service.dto.ConversionRequest;
import com.example.main_service.dto.ConversionResponse;
import com.example.main_service.dto.RateResponse;
import com.example.main_service.entity.Conversions;
import com.example.main_service.exceptions.ServiceUnavailableException;
import com.example.main_service.repository.ConversionsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversionService {
    private final ConversionsRepo conversionsRepo;
    private final WebClient webClient;

    public ConversionResponse convertCurrency(ConversionRequest conversionRequest) {
        try {

            RateResponse rateResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/rate")
                            .queryParam("from", conversionRequest.getFrom())
                            .queryParam("to", conversionRequest.getTo())
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(RateResponse.class)
                    .block();
            if (rateResponse == null) {
                throw new ServiceUnavailableException("Rate Service is unavailable");
            }
            //calculate converted amount
            BigDecimal convertedAMount = conversionRequest.getAmount().multiply(rateResponse.getRate());
            Conversions conversions = Conversions.builder()
                    .fromCurrency(conversionRequest.getFrom())
                    .toCurrency(conversionRequest.getTo())
                    .amount(conversionRequest.getAmount())
                    .rate(rateResponse.getRate())
                    .convertedAmount(convertedAMount)
                    .timestamp(new Date())
                    .build();
            conversionsRepo.save(conversions);

            return new ConversionResponse(
                    conversionRequest.getFrom(),
                    conversionRequest.getTo(),
                    conversionRequest.getAmount(),
                    convertedAMount,
                    rateResponse.getRate()
            );

        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }
}
