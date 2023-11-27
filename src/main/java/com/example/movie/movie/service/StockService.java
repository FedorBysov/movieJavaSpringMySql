package com.example.movie.movie.service;

import com.example.movie.movie.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {
    public List<Stock> findAll();
    public Optional<Stock> findById(Long id);
    public void save(Stock stock);
    public void deleteById(Long id);
}
