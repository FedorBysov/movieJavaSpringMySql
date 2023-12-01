package com.example.movie.movie.repository;

import com.example.movie.movie.entity.Genre;
import com.example.movie.movie.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.title LIKE %?1% OR m.description LIKE %?1% OR m.popularity LIKE %?1%")
    public List<Movie> searchByParam(String param);


    @Query("SELECT m FROM Movie m WHERE m.purchasePrice BETWEEN ?1 AND ?2")
    public List<Movie> filterByPurchasePrice(BigDecimal minPrice, BigDecimal maxPrice);


    @Query("SELECT m FROM Movie m WHERE m.rentalPrice BETWEEN ?1 AND ?2")
    public List<Movie> filterByRentalPrice(BigDecimal minPrice, BigDecimal maxPrice);

//Спросить CALL
    @Query(value = "{CALL SP_get_movies_by_genre()}", nativeQuery = true)
    public List<Movie> filterByGenre(String genre);
}
