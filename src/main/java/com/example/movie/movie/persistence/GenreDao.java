package com.example.movie.movie.persistence;

import com.example.movie.movie.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    public List<Genre> findAll();
    public Optional<Genre> findById(Long id);
    public void save(Genre genre);
    public void deleteById(Long id);
}
