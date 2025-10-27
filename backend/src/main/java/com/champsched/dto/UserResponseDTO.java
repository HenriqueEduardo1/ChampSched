package com.champsched.dto;

import com.champsched.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    
    private int id;
    private String nome;
    private String contato;
    
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.contato = user.getContato();
    }
}
