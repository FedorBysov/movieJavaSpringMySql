package com.example.movie.movie.service;

import com.example.movie.movie.entity.Movie;
import com.example.movie.movie.util.EType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    public List<Movie> findAll();
    public List<Movie> searchByParam(String param);

//    public List<Movie> filterByPurchasePrice(BigDecimal minPrice, BigDecimal maxPrice);
//    public List<Movie> filterByRentalPrice(BigDecimal minPrice, BigDecimal maxPrice);

    public List<Movie> filterByRangePrice(BigDecimal minPrice, BigDecimal maxPrice, EType type);

    public List<Movie> filterByGenre(String genre);
    public Optional<Movie> findById(Long id);
    public void save(Movie movie);
    public void deleteById(Long id);
}
