package com.example.main_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "conversions")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversions implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "from_currency")
    private String fromCurrency;
    @Column(name = "to_currency")
    private String toCurrency;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "rate")
    private BigDecimal rate;
    @Column(name = "converted_amount")
    private BigDecimal convertedAmount;
    @Column(name = "timestamp")
    private Date timestamp;

}
