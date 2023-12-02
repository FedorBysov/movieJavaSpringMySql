package com.example.movie.movie.service.impl;

import com.example.movie.movie.entity.Movie;
import com.example.movie.movie.persistence.MovieDao;
import com.example.movie.movie.repository.MovieRepository;
import com.example.movie.movie.service.MovieService;
import com.example.movie.movie.util.EType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @Override
    public List<Movie> searchByParam(String param) {
        return movieDao.searchByParam(param);
    }

//    @Override
//    public List<Movie> filterByPurchasePrice(BigDecimal minPrice, BigDecimal maxPrice) {
//        return movieDao.filterByPurchasePrice(minPrice, maxPrice);
//    }
//
//    @Override
//    public List<Movie> filterByRentalPrice(BigDecimal minPrice, BigDecimal maxPrice) {
//        return movieDao.filterByRentalPrice(minPrice, maxPrice);
//    }

    @Override
    public List<Movie> filterByRangePrice(BigDecimal minPrice, BigDecimal maxPrice, EType type) {
        if(type == EType.PURCHASE){
            return movieDao.filterByPurchasePrice(minPrice, maxPrice);
        }else{
            return movieDao.filterByRentalPrice(minPrice, maxPrice);
        }
    }

    @Override
    public List<Movie> filterByGenre(String genre) {
        return movieDao.filterByGenre(genre);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieDao.findById(id);
    }

    @Override
    public void save(Movie movie) {
        movieDao.save(movie);
    }

    @Override
    public void deleteById(Long id) {
        movieDao.deleteById(id);
    }
}
