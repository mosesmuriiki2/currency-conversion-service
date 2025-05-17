package com.example.main_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateResponse {
    private String from;
    private String to;
    private BigDecimal rate;
}
