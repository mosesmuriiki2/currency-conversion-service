package com.example.main_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ConversionResponse {
    private String from;
    private String to;
    private String amount;
    private String convertedAmount;
    private String rate;

    public ConversionResponse(String from, String to, BigDecimal amount, BigDecimal convertedAMount, BigDecimal rate) {
        this.from = from;
        this.to = to;
        this.amount = amount.toString();
        this.convertedAmount = convertedAMount.toString();
        this.rate = rate.toString();

    }
}
