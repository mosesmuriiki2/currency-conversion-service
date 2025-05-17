package com.example.main_service.controller;

import com.example.main_service.dto.ConversionRequest;
import com.example.main_service.dto.ConversionResponse;
import com.example.main_service.service.ConversionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
@Slf4j
@RequiredArgsConstructor
public class ConversionController {
    private final ConversionService conversionService;

    @PostMapping
    public ConversionResponse convert(@RequestBody @Valid ConversionRequest conversionRequest) {
        return conversionService.convertCurrency(conversionRequest);
    }
}
