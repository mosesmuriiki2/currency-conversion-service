package com.example.rate_service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateResponse {
    private String result;
    private String base_code;
    private Map<String, Double> rates;

    public Double getBaseRate(){
        return rates != null ? rates.get(base_code) : null;
    }
}
