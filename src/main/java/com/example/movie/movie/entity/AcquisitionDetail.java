package com.example.movie.movie.entity;

import com.example.movie.movie.util.EType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="acquisition_detail")
public class AcquisitionDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EType type;

    @OneToOne
    @JoinColumn(name = "id_movie")
    private Movie movie;



}
