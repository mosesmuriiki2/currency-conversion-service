package com.example.rate_service.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String currencyCode) {
        super(" invalid currency code " +currencyCode);
    }

    public InvalidCurrencyException(String currencyCode, Throwable cause) {
        super(" invalid currency code " +currencyCode, cause);
    }
}
