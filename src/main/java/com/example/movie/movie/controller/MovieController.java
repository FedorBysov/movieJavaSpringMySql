package com.example.movie.movie.controller;

import com.example.movie.movie.controller.Dto.MovieDto;
import com.example.movie.movie.entity.Genre;
import com.example.movie.movie.entity.Movie;
import com.example.movie.movie.service.MovieService;
import com.example.movie.movie.util.EType;
import com.example.movie.movie.util.RangePrice;
import com.example.movie.movie.util.ValidFileUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class MovieController {


    @Autowired
    private MovieService movieService;

    @Autowired
    private ValidFileUtil validFileUtil;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = new HashMap<>();

        List<MovieDto> movieList = movieService.findAll()
                .stream().map(movie -> MovieDto.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .posterImage(movie.getPosterImage())
                        .backgroundImage(movie.getBackgroundImage())
                        .popularity(movie.getPopularity())
                        .rentalPrice(movie.getRentalPrice())
                        .purchasePrice(movie.getPurchasePrice())
                        .availability(movie.isAvailability())
                        .stock(movie.getStock())
                        .genres(movie.getGenres())
                        .build()
                ).toList();

        response.put("data", movieList);
        response.put("message", "data Received");

        return ResponseEntity.ok(response);

    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<?> searchByParam(@RequestParam String param) {
        Map<String, Object> response = new HashMap<>();


        if (param.isBlank()) {
            response.put("message", "Param is blank");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        List<MovieDto> movieList = movieService.searchByParam(param)
                .stream().map(movie -> MovieDto.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .posterImage(movie.getPosterImage())
                        .backgroundImage(movie.getBackgroundImage())
                        .popularity(movie.getPopularity())
                        .rentalPrice(movie.getRentalPrice())
                        .purchasePrice(movie.getPurchasePrice())
                        .availability(movie.isAvailability()) // lombok agrega el get de esta forma isAvailability
                        .stock(movie.getStock())
                        .genres(movie.getGenres())
                        .build()
                ).toList();

        response.put("message", "the data received");
        response.put("Data", movieList);
        response.put("filter_type", EType.SEARCH);

        return ResponseEntity.ok(response);

    }
//
//    @PostMapping("/filterByPurchasePrice")
//    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
//    public ResponseEntity<?> filterByPurchasePrice(@RequestBody RangePrice rangePrice) {
//        Map<String, Object> response = new HashMap<>();
//
//        if (rangePrice.maxPrice == null || rangePrice.minPrice == null) {
//            response.put("message", "rangePrice is blank");
//            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        List<MovieDto> movieList = movieService.filterByPurchasePrice(rangePrice.minPrice, rangePrice.maxPrice)
//                .stream().map(movie -> MovieDto.builder()
//                        .id(movie.getId())
//                        .title(movie.getTitle())
//                        .description(movie.getDescription())
//                        .posterImage(movie.getPosterImage())
//                        .backgroundImage(movie.getBackgroundImage())
//                        .popularity(movie.getPopularity())
//                        .rentalPrice(movie.getRentalPrice())
//                        .purchasePrice(movie.getPurchasePrice())
//                        .availability(movie.isAvailability())
//                        .stock(movie.getStock())
//                        .genres(movie.getGenres())
//                        .build()
//                ).toList();
//
//        response.put("message", "the data received");
//        response.put("Data", movieList);
//        response.put("filter_type", EType.PURCHASE);
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/filterByPurchasePrice")
//    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
//    public ResponseEntity<?> filterByRentalPrice(@RequestBody RangePrice rangePrice) {
//        Map<String, Object> response = new HashMap<>();
//
//        if (rangePrice.maxPrice == null || rangePrice.minPrice == null) {
//            response.put("message", "rangePrice is blank");
//            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        List<MovieDto> movieList = movieService.filterByRentalPrice(rangePrice.minPrice, rangePrice.maxPrice)
//                .stream().map(movie -> MovieDto.builder()
//                        .id(movie.getId())
//                        .title(movie.getTitle())
//                        .description(movie.getDescription())
//                        .posterImage(movie.getPosterImage())
//                        .backgroundImage(movie.getBackgroundImage())
//                        .popularity(movie.getPopularity())
//                        .rentalPrice(movie.getRentalPrice())
//                        .purchasePrice(movie.getPurchasePrice())
//                        .availability(movie.isAvailability())
//                        .stock(movie.getStock())
//                        .genres(movie.getGenres())
//                        .build()
//                ).toList();
//
//        response.put("message", "the data received");
//        response.put("Data", movieList);
//        response.put("filter_type", EType.RENTAL);
//
//        return ResponseEntity.ok(response);
//    }


    @PostMapping("/filter-price")
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<?> filterByRangePrice(@RequestBody RangePrice rangePrice){
        Map<String, Object> response = new HashMap<>();
        if(rangePrice.minPrice == null || rangePrice.maxPrice == null){
            response.put("message","Params min price or max price is blank");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        List<MovieDto>movieList = movieService.filterByRangePrice(rangePrice.minPrice, rangePrice.maxPrice, rangePrice.type)
                .stream().map(movie -> MovieDto.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .posterImage(movie.getPosterImage())
                        .backgroundImage(movie.getBackgroundImage())
                        .popularity(movie.getPopularity())
                        .rentalPrice(movie.getRentalPrice())
                        .purchasePrice(movie.getPurchasePrice())
                        .availability(movie.isAvailability()) // lombok agrega el get de esta forma isAvailability
                        .stock(movie.getStock())
                        .genres(movie.getGenres())
                        .build()
                ).toList();

        EType typeFiler = rangePrice.type == EType.PURCHASE ? EType.PURCHASE : EType.RENTAL;

        response.put("message", "Records obtained");
        response.put("Data", movieList);
        response.put("filter_type", typeFiler);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<?> filterByGenre(@RequestParam String genre) {
        Map<String, Object> response = new HashMap<>();

        if (genre.isBlank()) {
            response.put("message", "Param is blank");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        List<MovieDto> movieList = movieService.filterByGenre(genre)
                .stream().map(movie -> MovieDto.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .posterImage(movie.getPosterImage())
                        .backgroundImage(movie.getBackgroundImage())
                        .popularity(movie.getPopularity())
                        .rentalPrice(movie.getRentalPrice())
                        .purchasePrice(movie.getPurchasePrice())
                        .availability(movie.isAvailability()) // lombok agrega el get de esta forma isAvailability
                        .stock(movie.getStock())
                        .genres(movie.getGenres())
                        .build()
                ).toList();

        response.put("message", "the data received");
        response.put("Data", movieList);
        response.put("filter_type", EType.SEARCH);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'INVITED')")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        if (id == null) {
            response.put("message", "ID is null");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> movieOptional = movieService.findById(id);

        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            MovieDto dto = MovieDto.builder()
                    .id(movie.getId())
                    .title(movie.getTitle())
                    .description(movie.getDescription())
                    .posterImage(movie.getPosterImage())
                    .backgroundImage(movie.getBackgroundImage())
                    .popularity(movie.getPopularity())
                    .rentalPrice(movie.getRentalPrice())
                    .purchasePrice(movie.getPurchasePrice())
                    .availability(movie.isAvailability())
                    .stock(movie.getStock())
                    .genres(movie.getGenres())
                    .build();

            response.put("data", dto);
            response.put("message", "the data received");

            return ResponseEntity.ok(response);
        }

        response.put("message", "ID " + id + " incorrect");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody MovieDto movieDTO) {
        Map<String, Object> response = new HashMap<>();

        try {

            if (validFileUtil.isValidField(movieDTO)) {
                response.put("message", "One or more fields are empty");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            movieService.save(
                    Movie.builder()
                            .title(movieDTO.getTitle())
                            .description(movieDTO.getDescription())
                            .posterImage(movieDTO.getPosterImage())
                            .backgroundImage(movieDTO.getBackgroundImage())
                            .popularity(movieDTO.getPopularity())
                            .rentalPrice(movieDTO.getRentalPrice())
                            .purchasePrice(movieDTO.getPurchasePrice())
                            .availability(movieDTO.isAvailability())
                            .stock(movieDTO.getStock())
                            .genres(movieDTO.getGenres())
                            .build()
            );

            response.put("message", "Created correctly");
            response.put("url", new URI("/movie"));

            return ResponseEntity.ok(response);

        } catch (URISyntaxException e) {
            response.put("message", "An internal error has occurred");
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        if (id == null) {
            response.put("message", "ID is null");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> movieOptional = movieService.findById(id);

        if (movieOptional.isPresent()) {
            movieService.deleteById(id);
            response.put("message", "Deleted record");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Movie " + id + " not found");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMovie(@RequestBody MovieDto movieDTO, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        if (id == null) {
            response.put("message", "ID is null");
            new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        if (validFileUtil.isValidField(movieDTO)) {
            response.put("message", "One or more fields are empty");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> movieOptional = movieService.findById(id);

        if (movieOptional.isPresent()) {

            Movie movie = getMovie(movieDTO, movieOptional);

            movieService.save(movie);


            response.put("message", "Updated correctly");

            return ResponseEntity.ok(response);
        }

        response.put("message", "Movie " + id + " not found");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }


    private static Movie getMovie(MovieDto movieDTO, Optional<Movie> movieOptional) {

        Movie movie = movieOptional.get();

        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setPosterImage(movieDTO.getPosterImage());
        movie.setBackgroundImage(movieDTO.getBackgroundImage());
        movie.setPopularity(movieDTO.getPopularity());
        movie.setRentalPrice(movieDTO.getRentalPrice());
        movie.setPurchasePrice(movieDTO.getPurchasePrice());
        movie.setAvailability(movieDTO.isAvailability());
        movie.setStock(movieDTO.getStock());
        movie.setGenres(movieDTO.getGenres());

        return movie;
    }

}
