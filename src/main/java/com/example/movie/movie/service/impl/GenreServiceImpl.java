package com.example.movie.movie.service.impl;

import com.example.movie.movie.entity.Genre;
import com.example.movie.movie.persistence.GenreDao;
import com.example.movie.movie.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreDao genreDao;

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll()  ;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreDao.findById(id);
    }

    @Override
    public void save(Genre genre) {
        genreDao.save(genre);
    }

    @Override
    public void deleteById(Long id) {
        genreDao.deleteById(id);
    }
}
