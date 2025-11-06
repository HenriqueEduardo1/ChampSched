package com.champsched.model;

import java.time.LocalDateTime;
import java.util.List;

public interface IPartida {

    Integer getId();

    Campeonato getCampeonato();
    void setCampeonato(Campeonato campeonato);

    List<Time> getTimes();

    StatusPartida getStatus();
    void setStatus(StatusPartida status);

    LocalDateTime getDataHora();
    void setDataHora(LocalDateTime dataHora);

    void iniciar();

    void finalizar();

    Time getVencedor();
}
