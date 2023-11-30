package com.example.movie.movie.controller.Dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StockDto {
    private Long id;
    private Long amount;

}
