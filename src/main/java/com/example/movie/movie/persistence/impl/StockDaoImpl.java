package com.example.movie.movie.persistence.impl;

import com.example.movie.movie.entity.Stock;
import com.example.movie.movie.persistence.StockDao;
import com.example.movie.movie.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StockDaoImpl implements StockDao {

    @Autowired
    private StockRepository stockRepository;


    @Override
    public List<Stock> findAll() {
        return (List<Stock>) stockRepository.findAll();
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public void save(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public void deleteById(Long id) {
        stockRepository.deleteById(id);
    }
}
