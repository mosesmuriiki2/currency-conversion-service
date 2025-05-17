package com.example.main_service.service;

import com.example.main_service.dto.ConversionRequest;
import com.example.main_service.dto.ConversionResponse;
import com.example.main_service.dto.RateResponse;
import com.example.main_service.entity.Conversions;
import com.example.main_service.repository.ConversionsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConversionServiceTest {

    @Mock
    private ConversionsRepo conversionsRepo;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ConversionService conversionService;

    @BeforeEach
    void setUp() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(java.util.function.Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void testConvertCurrency_Success() {
        // Arrange
        ConversionRequest request = new ConversionRequest();
        request.setFrom("USD");
        request.setTo("EUR");
        request.setAmount(new BigDecimal("100"));

        RateResponse rateResponse = new RateResponse();
        rateResponse.setFrom("USD");
        rateResponse.setTo("EUR");
        rateResponse.setRate(new BigDecimal("0.85"));

        when(responseSpec.bodyToMono(RateResponse.class)).thenReturn(Mono.just(rateResponse));
        when(conversionsRepo.save(any(Conversions.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ConversionResponse response = conversionService.convertCurrency(request);

        // Assert
        assertNotNull(response);
        assertEquals("USD", response.getFrom());
        assertEquals("EUR", response.getTo());
        assertEquals(new BigDecimal("100"), response.getAmount());
        assertEquals(new BigDecimal("85.00").stripTrailingZeros(), response.getConvertedAmount().stripTrailing());
        assertEquals(new BigDecimal("0.85"), response.getRate());

        verify(conversionsRepo, times(1)).save(any(Conversions.class));
    }
}