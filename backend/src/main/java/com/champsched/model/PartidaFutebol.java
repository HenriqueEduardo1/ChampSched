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
    public ResultadoPartida getResultado() {
        if (getStatus() != StatusPartida.FINALIZADA) {
            return ResultadoPartida.NAO_FINALIZADA;
        }

        if (golsTimeA > golsTimeB) {
            return ResultadoPartida.TIME_A_VENCEU;
        } else if (golsTimeB > golsTimeA) {
            return ResultadoPartida.TIME_B_VENCEU;
        } else {
            return ResultadoPartida.EMPATE;
        }
    }

    @Override
    public Time getVencedor() {
        return switch (getResultado()) {
            case TIME_A_VENCEU -> getTimeA();
            case TIME_B_VENCEU -> getTimeB();
            default -> null;
        };
    }

}
