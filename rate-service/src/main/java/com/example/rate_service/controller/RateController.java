package com.example.rate_service.controller;

import com.example.rate_service.dto.RateResponse;
import com.example.rate_service.service.RateService;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @GetMapping
    public RateResponse getRate(
            @RequestParam @NotBlank String from,
            @RequestParam @NotBlank String to){
                return rateService.getExchangeRate(from, to);
    }



}
