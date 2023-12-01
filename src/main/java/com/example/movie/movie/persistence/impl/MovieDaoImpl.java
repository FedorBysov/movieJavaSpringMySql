package com.example.movie.movie.persistence.impl;

import com.example.movie.movie.entity.Movie;
import com.example.movie.movie.persistence.MovieDao;
import com.example.movie.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MovieDaoImpl implements MovieDao {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        return (List<Movie>) movieRepository.findAll();
    }

    @Override
    public List<Movie> searchByParam(String param) {
        return movieRepository.searchByParam(param);
    }

    @Override
    public List<Movie> filterByPurchasePrice(Long minPrice, Long maxPrice) {
        return movieRepository.filterByPurchasePrice(minPrice, maxPrice);
    }

    @Override
    public List<Movie> filterByRentalPrice(Long minPrice, Long maxPrice) {
        return movieRepository.filterByRentalPrice(minPrice, maxPrice);
    }

    @Override
    public List<Movie> filterByGenre(String genre) {
        return movieRepository.filterByGenre(genre);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
}
