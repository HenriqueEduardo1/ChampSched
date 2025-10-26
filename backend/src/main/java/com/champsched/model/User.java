package com.champsched.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements IParticipante, IOrganizador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String contato;
    
    @OneToMany(mappedBy = "organizador")
    private List<Campeonato> campeonatos = new ArrayList<>();
    

    @Override
    public Campeonato criarCampeonato(String nome, String esporte, LocalDate data) {
        Campeonato campeonato = new Campeonato(nome, esporte, data, this);
        this.campeonatos.add(campeonato);
        return campeonato;
    }
}
