package com.example.movie.movie.persistence;

import com.example.movie.movie.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface StockDao {
    public List<Stock> findAll();
    public Optional<Stock> findById(Long id);
    public void save(Stock stock);
    public void deleteById(Long id);
}
