package com.example.movie.movie.controller;

import com.example.movie.movie.controller.Dto.GenreDto;
import com.example.movie.movie.entity.Genre;
import com.example.movie.movie.repository.GenreRepository;
import com.example.movie.movie.service.GenreService;
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
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private ValidFileUtil validFileUtil;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = new HashMap<>();

        List<GenreDto> genreDto = genreService.findAll()
                .stream()
                .map(genre -> GenreDto.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .build())
                .toList();

        response.put("data", genreDto);
        response.put("message", "data received");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@Valid @RequestParam Long id) {
        Map<String, Object> response = new HashMap<>();

        if (id == null) {
            response.put("message", "ID is null");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Genre> genreOptional = genreService.findById(id);

        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();
            GenreDto genreDto = GenreDto.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .build();

            response.put("data", genreDto);
            response.put("message", "the data received");

            return ResponseEntity.ok(response);

        }

        response.put("message", "ID " + id + " incorrect");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestParam GenreDto dto) {
        Map<String, Object> response = new HashMap<>();

        try {

            if (validFileUtil.isValidField(dto)) {
                response.put("message", "One or more fields are empty");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            genreService.save(
                    Genre.builder()
                            .name(dto.getName())
                            .build()
            );

            response.put("message", "Created correctly");
            response.put("url", new URI("/genre"));

            return ResponseEntity.ok(response);

        } catch (URISyntaxException e) {
            response.put("message", "An internal error has occurred");
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@Valid @RequestParam Long id) {
        Map<String, Object> response = new HashMap<>();

        if (id == null) {
            response.put("message", "ID is null");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Genre> genreOptional = genreService.findById(id);

        if (genreOptional.isPresent()) {
            genreService.deleteById(id);
            response.put("message", "Deleted record");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Genre " + id + " not found");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestParam Long id, @RequestBody GenreDto dto) {
        Map<String, Object> response = new HashMap<>();

        if (id == null) {
            response.put("message", "ID is null");
            new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        if (validFileUtil.isValidField(dto)) {
            response.put("message", "One or more fields are empty");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Genre> genreOptional = genreService.findById(id);

        if (genreOptional.isPresent()) {
            Genre genre = genreOptional.get();
            genre.setName(dto.getName());

            genreService.save(genre);

            response.put("message", "Updated correctly");

            return ResponseEntity.ok(response);
        }

        response.put("message", "Genre " + id + " not found");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);

    }

}
