package com.champsched.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "partidas_futebol")
public class PartidaFutebol extends PartidaBase {

    private int golsTimeA = 0;
    private int golsTimeB = 0;

    @Override
    public void iniciar() {
        setStatus(StatusPartida.EM_ANDAMENTO);
    }

    @Override
    public void finalizar() {
        setStatus(StatusPartida.FINALIZADA);
    }

    @Override
    public Time getVencedor() {
        if (getStatus() != StatusPartida.FINALIZADA) {
            return null;
        }
        
        if (golsTimeA > golsTimeB) {
            return getTimeA();
        } else if (golsTimeB > golsTimeA) {
            return getTimeB();
        }
        
        return null;
    }
}
