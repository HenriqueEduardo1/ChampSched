package com.champsched.model;

import java.util.List;

public interface IChaveamento {
    
    
    List<IPartida> gerarPartidasIniciais(Campeonato campeonato);

    void atualizarChaveamento(Campeonato campeonato, IPartida partidaFinalizada);
}
