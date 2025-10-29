package com.champsched.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class TimeRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    private String contato;
    
    private List<Integer> integrantesIds;
    
}
