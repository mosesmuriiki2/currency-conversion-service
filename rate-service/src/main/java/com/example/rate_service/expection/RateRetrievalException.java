package com.example.rate_service.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class RateRetrievalException extends RuntimeException {
    public RateRetrievalException(String message) {
        super(message);
    }
    public RateRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
