package com.champsched.service.chaveamento;

import com.champsched.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ChaveamentoEliminacaoSimples implements IChaveamento {

    @Override
    public List<IPartida> gerarPartidasIniciais(Campeonato campeonato) {

        List<Time> times = new ArrayList<>(campeonato.getTimes());
        
        if (times.isEmpty() || times.size() < 2) {
            throw new IllegalArgumentException("Campeonato deve ter pelo menos 2 times");
        }
        
        Collections.shuffle(times);
        
        int numTimes = times.size();
        int numFases = (int) Math.ceil(Math.log(numTimes) / Math.log(2));
        
        List<List<PartidaBase>> fases = new ArrayList<>();
        
        for (int fase = 1; fase <= numFases; fase++) {
            int numPartidasNaFase = (int) Math.pow(2, numFases - fase);
            List<PartidaBase> partidasDaFase = new ArrayList<>();
            
            for (int i = 0; i < numPartidasNaFase; i++) {
                PartidaBase partida = (PartidaBase) criarPartida(campeonato, null, null);
                partida.setFase(fase);
                partidasDaFase.add(partida);
            }
            
            fases.add(partidasDaFase);
        }
        
        for (int fase = 0; fase < numFases - 1; fase++) {
            List<PartidaBase> fasesAtual = fases.get(fase);
            List<PartidaBase> proximaFase = fases.get(fase + 1);
            
            for (int i = 0; i < fasesAtual.size(); i++) {
                PartidaBase partida = fasesAtual.get(i);
                int indiceProximaPartida = i / 2;
                
                partida.setProximaPartida(proximaFase.get(indiceProximaPartida));
                partida.setPosicaoNaProximaPartida((i % 2) + 1);
            }
        }
        
        List<PartidaBase> primeiraFase = fases.get(0);
        int indiceTime = 0;
        
        for (int i = 0; i < primeiraFase.size() && indiceTime < times.size(); i++) {
            PartidaBase partida = primeiraFase.get(i);
            
            if (indiceTime < times.size()) {
                partida.setTimeA(times.get(indiceTime++));
            }
            
            if (indiceTime < times.size()) {
                partida.setTimeB(times.get(indiceTime++));
            }
            
            if (partida.getTimeA() != null && partida.getTimeB() == null) {
                partida.setStatus(StatusPartida.FINALIZADA);
                
                if (partida.getProximaPartida() != null) {
                    if (partida.getPosicaoNaProximaPartida() == 1) {
                        partida.getProximaPartida().setTimeA(partida.getTimeA());
                    } else {
                        partida.getProximaPartida().setTimeB(partida.getTimeA());
                    }
                }
            }
        }
        
        List<IPartida> partidasIniciais = new ArrayList<>();

        for (PartidaBase partida : primeiraFase) {
            if (partida.getTimeA() != null && partida.getTimeB() != null) {
                partidasIniciais.add(partida);
            }
        }
        
        for (int i = 1; i < fases.size(); i++) {
            partidasIniciais.addAll(fases.get(i));
        }
        
        return partidasIniciais;
    }

    @Override
    public void atualizarChaveamento(Campeonato campeonato, IPartida partidaFinalizada) {
        if (partidaFinalizada.getStatus() != StatusPartida.FINALIZADA) {
            throw new IllegalArgumentException("Partida deve estar finalizada");
        }
        
        Time vencedor = partidaFinalizada.getVencedor();
        
        if (vencedor == null) {
            throw new IllegalArgumentException("Partida finalizada deve ter um vencedor (empates não são permitidos em eliminação simples)");
        }
        
        if (!(partidaFinalizada instanceof PartidaBase)) {
            throw new IllegalArgumentException("Partida deve ser do tipo PartidaBase");
        }
        
        PartidaBase partidaBase = (PartidaBase) partidaFinalizada;
        PartidaBase proximaPartida = partidaBase.getProximaPartida();
        
        if (proximaPartida == null) {
            return;
        }
        
        Integer posicao = partidaBase.getPosicaoNaProximaPartida();
        
        if (posicao == null) {
            throw new IllegalStateException("Posição na próxima partida não definida");
        }
        
        if (posicao == 1) {
            proximaPartida.setTimeA(vencedor);
        } else if (posicao == 2) {
            proximaPartida.setTimeB(vencedor);
        } else {
            throw new IllegalStateException("Posição inválida: deve ser 1 ou 2");
        }
    }
    
    private IPartida criarPartida(Campeonato campeonato, Time timeA, Time timeB) {
        String esporte = campeonato.getEsporte().toLowerCase();
        
        IPartida partida;

        if (esporte.contains("futebol")) {
            PartidaFutebol partidaFutebol = new PartidaFutebol();

            partidaFutebol.setCampeonato(campeonato);
            partidaFutebol.setTimeA(timeA);
            partidaFutebol.setTimeB(timeB);
            partidaFutebol.setStatus(StatusPartida.AGUARDANDO);

            partida = partidaFutebol;
        } else {
            throw new IllegalArgumentException("Esporte não suportado: " + esporte);
        }
        
        return partida;
    }
}
