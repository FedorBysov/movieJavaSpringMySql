package com.example.movie.movie.util;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public class RangePrice {
    public BigDecimal minPrice;
    public BigDecimal maxPrice;
    @Enumerated(EnumType.STRING)
    public EType type;
}
