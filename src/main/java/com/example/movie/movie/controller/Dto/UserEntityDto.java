package com.example.movie.movie.controller.Dto;

import com.example.movie.movie.entity.Movie;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntityDto {

    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    private String image;

    private Set<String> roles;

    @Builder.Default
    private List<Movie> movies = new ArrayList<>();
}
