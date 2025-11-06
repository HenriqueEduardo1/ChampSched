package com.champsched.dto;

import com.champsched.model.Time;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TimeResponseDTO {
    
    private int id;
    private String nome;
    private String contato;
    private List<UserResponseDTO> integrantes;
    
    public TimeResponseDTO(Time time) {
        this.id = time.getId();
        this.nome = time.getNome();
        this.contato = time.getContato();
        this.integrantes = time.getIntegrantes() != null
            ? time.getIntegrantes().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList())
            : List.of();
    }
    
}
