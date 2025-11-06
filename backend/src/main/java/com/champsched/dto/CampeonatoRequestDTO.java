package com.champsched.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CampeonatoRequestDTO {

    private String nome;
    private String esporte;
    private LocalDate data;
    private Integer organizadorId;
    private List<Integer> timesIds;
    
}
