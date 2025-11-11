package com.champsched.dto;

import com.champsched.model.PartidaFutebol;
import lombok.Data;

@Data
public class PartidaDTO {
    
    private Integer id;
    private Integer fase;
    private String timeA;
    private String timeB;
    private Integer proximaPartidaId;
    private Integer posicaoNaProximaPartida;
    
    public PartidaDTO(PartidaFutebol partida) {
        this.id = partida.getId();
        this.fase = partida.getFase();
        this.timeA = partida.getTimeA() != null ? partida.getTimeA().getNome() : null;
        this.timeB = partida.getTimeB() != null ? partida.getTimeB().getNome() : null;
        this.proximaPartidaId = partida.getProximaPartida() != null ? partida.getProximaPartida().getId() : null;
        this.posicaoNaProximaPartida = partida.getPosicaoNaProximaPartida();
    }
}
