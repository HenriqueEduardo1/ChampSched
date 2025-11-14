package com.champsched.service;

import com.champsched.dto.PartidaDTO;
import com.champsched.model.*;
import com.champsched.repository.CampeonatoRepository;
import com.champsched.repository.PartidaRepository;
import com.champsched.service.chaveamento.ChaveamentoEliminacaoSimples;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final ChaveamentoEliminacaoSimples chaveamento;

    @Transactional
    public void gerarChaveamento(Integer campeonatoId) {

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));
        
        validarCampeonato(campeonato);
        
        List<IPartida> partidas = chaveamento.gerarPartidasIniciais(campeonato);

        List<PartidaFutebol> partidasFutebol = partidas.stream()
                .map(p -> (PartidaFutebol) p)
                .toList();

        partidaRepository.saveAll(partidasFutebol);

        for (PartidaFutebol partida : partidasFutebol) {
            if (partida.getProximaPartida() != null) {
                partida.setProximaPartida(
                    partidaRepository.findById(partida.getProximaPartida().getId())
                        .orElseThrow()
                );
            }
        }

        partidaRepository.saveAll(partidasFutebol);
    }

    public List<PartidaDTO> listarPartidas(Integer campeonatoId) {
        List<PartidaFutebol> partidas = partidaRepository.findByCampeonatoIdOrderByFaseAsc(campeonatoId);
        
        return partidas.stream().map(PartidaDTO::new).toList();
    }

    @Transactional
    public void apagarPartidasDoCampeonato(Integer campeonatoId) {
    
        campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));
        
        long totalPartidas = partidaRepository.countByCampeonatoId(campeonatoId);
        
        if (totalPartidas == 0) {
            throw new IllegalStateException("Não há partidas para apagar neste campeonato");
        }
        
        partidaRepository.deleteByCampeonatoId(campeonatoId);
    }
    
    private void validarCampeonato(Campeonato campeonato) {
    
        if (campeonato.getTimes().size() < 2) {
            throw new IllegalStateException("Campeonato deve ter pelo menos 2 times");
        }
        if (partidaRepository.countByCampeonatoId(campeonato.getId()) > 0) {
            throw new IllegalStateException("Chaveamento já foi gerado");
        }
    }

}
