package com.example.movie.movie.service.impl;

import com.example.movie.movie.entity.Stock;
import com.example.movie.movie.persistence.StockDao;
import com.example.movie.movie.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public List<Stock> findAll() {
        return stockDao.findAll();
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return stockDao.findById(id);
    }

    @Override
    public void save(Stock stock) {
        stockDao.save(stock);
    }

    @Override
    public void deleteById(Long id) {
        stockDao.deleteById(id);
    }
}
