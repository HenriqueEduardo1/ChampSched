package com.champsched.model;

import java.time.LocalDate;
import java.util.List;

public interface IOrganizador {
    Campeonato criarCampeonato(String nome, String esporte, LocalDate data);
    List<Campeonato> getCampeonatos();
}
