package com.example.movie.movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Movie {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "poster_image")
    private String posterImage;

    @Column(name = "background_image")
    private String backgroundImage;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    private double popularity;

    @Column(name = "rental_price")
    private BigDecimal rentalPrice;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    private boolean availability = true;

    @OneToOne(targetEntity = Stock.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_stock")
    private Stock stock;

    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "id_movie"), inverseJoinColumns = @JoinColumn(name = "id_genre"))
    @JsonIgnore
    private List<Genre> genres;
}


