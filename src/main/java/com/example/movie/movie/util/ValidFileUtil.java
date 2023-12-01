package com.example.movie.movie.util;

import com.example.movie.movie.controller.Dto.GenreDto;
import com.example.movie.movie.controller.Dto.MovieDto;
import org.springframework.stereotype.Component;

@Component
public class ValidFileUtil {
    public boolean isValidField(GenreDto genreDTO){
        return genreDTO.getName().isBlank();
    }

    public boolean isValidField(MovieDto movieDTO){
        return movieDTO.getTitle().isBlank()
                || movieDTO.getDescription().isBlank()
                || movieDTO.getPosterImage().isBlank()
                || movieDTO.getBackgroundImage().isBlank()
                || movieDTO.getRentalPrice() == null
                || movieDTO.getPurchasePrice() == null;
    }
}
