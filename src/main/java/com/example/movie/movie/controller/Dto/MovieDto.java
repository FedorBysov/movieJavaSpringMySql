package com.example.movie.movie.controller.Dto;

import com.example.movie.movie.entity.Genre;
import com.example.movie.movie.entity.Stock;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long id;
    private String posterImage;
    private String backgroundImage;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private double popularity;
    @NotNull
    @Column(name = "rental_price")
    private Long rentalPrice;
    @NotNull
    @Column(name = "purchase_price")
    private Long purchasePrice;
    @NotNull
    private boolean availability = true;

    //??
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Stock stock;
    private List<Genre> genres;
}
