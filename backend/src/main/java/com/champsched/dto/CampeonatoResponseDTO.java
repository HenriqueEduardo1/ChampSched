package com.champsched.dto;

import com.champsched.model.Campeonato;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CampeonatoResponseDTO {
    
    private int id;
    private String nome;
    private String esporte;
    private LocalDate data;
    private UserResponseDTO organizador;
    private List<TimeResponseDTO> times;
    
    public CampeonatoResponseDTO(Campeonato campeonato) {

        this.id = campeonato.getId();
        this.nome = campeonato.getNome();
        this.esporte = campeonato.getEsporte();
        this.data = campeonato.getData();
        this.organizador = new UserResponseDTO(campeonato.getOrganizador());
        this.times = campeonato.getTimes().stream()
                .map(TimeResponseDTO::new)
                .collect(Collectors.toList());

    }
}
