package com.example.rate_service.service;

import com.example.rate_service.dto.ExchangeRateResponse;
import com.example.rate_service.dto.RateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private RateService rateService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(rateService, "key", "test-api-key");
        ReflectionTestUtils.setField(rateService, "url", "https://api.exchangerate-api.com/v4");
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(java.util.function.Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void testGetExchangeRate_Success() {
        // Arrange
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        Double expectedRate = 0.85;

        ExchangeRateResponse mockResponse = new ExchangeRateResponse();
        mockResponse.setResult("success");
        mockResponse.setBase_code(fromCurrency);
        
        Map<String, Double> rates = new HashMap<>();
        rates.put(toCurrency, expectedRate);
        mockResponse.setRates(rates);

        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(Mono.just(mockResponse));

        // Act
        RateResponse response = rateService.getExchangeRate(fromCurrency, toCurrency);

        // Assert
        assertNotNull(response);
        assertEquals(fromCurrency, response.getFrom());
        assertEquals(toCurrency, response.getTo());
        assertEquals(expectedRate, response.getRate());
    }
}