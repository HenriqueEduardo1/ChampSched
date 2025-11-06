package com.champsched.dto;

import com.champsched.model.Time;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TimeCampeonatoResponseDTO {
    private int id;
    private String nome;
    private String contato;
    private List<Integer> integrantesIds;

    public TimeCampeonatoResponseDTO(Time time) {
        this.id = time.getId();
        this.nome = time.getNome();
        this.contato = time.getContato();
        this.integrantesIds = time.getIntegrantes() != null
            ? time.getIntegrantes().stream()
                .map(user -> user.getId())
                .collect(Collectors.toList())
            : List.of();
    }
}
