package com.champsched.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "times")
@Data
@NoArgsConstructor
public class Time {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String nome;
    
    private String contato;
    
    @ManyToMany
    @JoinTable(
        name = "time_integrantes",
        joinColumns = @JoinColumn(name = "time_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> integrantes = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "time_campeonatos",
        joinColumns = @JoinColumn(name = "time_id"),
        inverseJoinColumns = @JoinColumn(name = "campeonato_id")
    )
    private List<Campeonato> campeonatos = new ArrayList<>();
    
}
