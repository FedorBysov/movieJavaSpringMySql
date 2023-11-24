package com.example.movie.movie.controller.Dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StockDto {
    private long id;
    private long amount;

}
