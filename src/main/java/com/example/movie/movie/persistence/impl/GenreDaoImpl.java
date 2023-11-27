package com.example.movie.movie.persistence.impl;

import com.example.movie.movie.entity.Genre;
import com.example.movie.movie.persistence.GenreDao;
import com.example.movie.movie.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class GenreDaoImpl implements GenreDao {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return (List<Genre>) genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
