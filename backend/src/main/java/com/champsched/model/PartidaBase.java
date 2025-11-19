package com.champsched.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class PartidaBase implements IPartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "campeonato_id", nullable = false)
    private Campeonato campeonato;

    @ManyToOne
    @JoinColumn(name = "time_a_id")
    private Time timeA;

    @ManyToOne
    @JoinColumn(name = "time_b_id")
    private Time timeB;

    @Enumerated(EnumType.STRING)
    private StatusPartida status = StatusPartida.AGUARDANDO;

    private LocalDateTime dataHora;

    @Column
    private Integer posicaoNaProximaPartida;

    @Column
    private Integer fase;

    @Override
    public List<Time> getTimes() {
        return List.of(timeA, timeB).stream()
                .filter(time -> time != null)
                .toList();
    }
    
    public abstract PartidaBase getProximaPartida();
    public abstract void setProximaPartida(PartidaBase proximaPartida);
}
