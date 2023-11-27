package com.example.movie.movie.service;

import com.example.movie.movie.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    public List<Movie> findAll();
    public List<Movie> searchByParam(String param);
    public List<Movie> filterByPurchasePrice(Long minPrice, Long maxPrice);
    public List<Movie> filterByRentalPrice(Long minPrice, Long maxPrice);
    public List<Movie> filterByGenre(String genre);
    public Optional<Movie> findById(Long id);
    public void save(Movie movie);
    public void deleteById(Long id);
}
