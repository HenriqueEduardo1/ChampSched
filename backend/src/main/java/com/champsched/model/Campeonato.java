package com.champsched.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campeonatos")
@Data
@NoArgsConstructor
public class Campeonato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String esporte;
    
    @Column(nullable = false)
    private LocalDate data;
    
    @ManyToOne
    @JoinColumn(name = "organizador_id", nullable = false)
    private User organizador;
    
    @ManyToMany(mappedBy = "campeonatos")
    private List<Time> times = new ArrayList<>();
    
    @OneToMany(mappedBy = "campeonato")
    private List<PartidaFutebol> partidas = new ArrayList<>();
    
    public Campeonato(String nome, String esporte, LocalDate data, User organizador) {
        this.nome = nome;
        this.esporte = esporte;
        this.data = data;
        this.organizador = organizador;
    }
    
}
