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
        List<Time> times = prepararTimes(campeonato);
        
        int numTimes = times.size();
        int proximaPotenciaDe2 = calcularProximaPotenciaDe2(numTimes);
        int partidasPlayIn = calcularPartidasPlayIn(numTimes, proximaPotenciaDe2);
        int timesNaPrimeiraFasePrincipal = partidasPlayIn > 0 ? proximaPotenciaDe2 / 2 : proximaPotenciaDe2;
        
        List<List<PartidaBase>> fases = criarEstruturaDeFases(campeonato, timesNaPrimeiraFasePrincipal, partidasPlayIn);

        conectarFases(fases);
        distribuirTimes(times, fases, partidasPlayIn);
        
        return converterParaListaDePartidas(fases);
    }
    
    private List<Time> prepararTimes(Campeonato campeonato) {
        List<Time> times = new ArrayList<>(campeonato.getTimes());
        
        if (times.isEmpty() || times.size() < 2) {
            throw new IllegalArgumentException("Campeonato deve ter pelo menos 2 times");
        }
        
        Collections.shuffle(times);
        return times;
    }
    
    private int calcularProximaPotenciaDe2(int numTimes) {
        int proximaPotenciaDe2 = 1;

        while (proximaPotenciaDe2 < numTimes) {
            proximaPotenciaDe2 *= 2;
        }

        return proximaPotenciaDe2;
    }
    
    private int calcularPartidasPlayIn(int numTimes, int proximaPotenciaDe2) {
        int timesComBye = proximaPotenciaDe2 - numTimes;
        int timesNoPlayIn = numTimes - timesComBye;
        
        return timesNoPlayIn / 2;
    }
    
    private List<List<PartidaBase>> criarEstruturaDeFases(Campeonato campeonato, int timesNaPrimeiraFasePrincipal, int partidasPlayIn) {
        List<List<PartidaBase>> fases = new ArrayList<>();
        
        if (partidasPlayIn > 0) {
            fases.add(criarFasePlayIn(campeonato, partidasPlayIn));
        }
        
        int partidasNaPrimeiraFasePrincipal = timesNaPrimeiraFasePrincipal / 2;
        
        fases.addAll(criarFasesPrincipais(campeonato, partidasNaPrimeiraFasePrincipal, partidasPlayIn > 0));
        
        return fases;
    }
    
    private List<PartidaBase> criarFasePlayIn(Campeonato campeonato, int partidasPlayIn) {
        List<PartidaBase> fasePlayIn = new ArrayList<>();
        
        for (int i = 0; i < partidasPlayIn; i++) {
            PartidaBase partida = (PartidaBase) criarPartida(campeonato, null, null);

            partida.setFase(1);
            fasePlayIn.add(partida);
        }
        
        return fasePlayIn;
    }
    
    private List<List<PartidaBase>> criarFasesPrincipais(Campeonato campeonato, int partidasIniciais, boolean temPlayIn) {
        List<List<PartidaBase>> fases = new ArrayList<>();

        int numeroFase = temPlayIn ? 2 : 1;
        int numPartidas = partidasIniciais;
        
        while (numPartidas >= 1) {
            List<PartidaBase> partidasDaFase = new ArrayList<>();
            
            for (int i = 0; i < numPartidas; i++) {
                PartidaBase partida = (PartidaBase) criarPartida(campeonato, null, null);
                partida.setFase(numeroFase);
                partidasDaFase.add(partida);
            }
            
            fases.add(partidasDaFase);
            
            numPartidas /= 2;
            numeroFase++;
        }
        
        return fases;
    }
    
    private void conectarFases(List<List<PartidaBase>> fases) {
        for (int fase = 0; fase < fases.size() - 1; fase++) {
            List<PartidaBase> faseAtual = fases.get(fase);
            List<PartidaBase> proximaFase = fases.get(fase + 1);
            
            for (int i = 0; i < faseAtual.size(); i++) {
                PartidaBase partida = faseAtual.get(i);
                int indiceProximaPartida = i / 2;
                
                if (indiceProximaPartida < proximaFase.size()) {
                    partida.setProximaPartida(proximaFase.get(indiceProximaPartida));
                    partida.setPosicaoNaProximaPartida((i % 2) + 1);
                }
            }
        }
    }
    
    private void distribuirTimes(List<Time> times, List<List<PartidaBase>> fases, int partidasPlayIn) {
        int indiceTime = 0;
        
        if (partidasPlayIn > 0) {
            indiceTime = distribuirTimesNoPlayIn(times, fases.get(0), partidasPlayIn);
        }
        
        distribuirTimesComBye(times, fases, partidasPlayIn, indiceTime);
    }
    
    private int distribuirTimesNoPlayIn(List<Time> times, List<PartidaBase> fasePlayIn, int partidasPlayIn) {
        int indiceTime = 0;
        
        for (int i = 0; i < partidasPlayIn && indiceTime < times.size() - 1; i++) {
            PartidaBase partida = fasePlayIn.get(i);
            partida.setTimeA(times.get(indiceTime++));
            partida.setTimeB(times.get(indiceTime++));
        }
        
        return indiceTime;
    }
    
    private void distribuirTimesComBye(List<Time> times, List<List<PartidaBase>> fases, 
                                        int partidasPlayIn, int indiceTimeInicial) {
        List<PartidaBase> faseAposPlayIn = partidasPlayIn > 0 ? fases.get(1) : fases.get(0);
        int indiceTime = indiceTimeInicial;
        int indicePartida = 0;
        
        while (indiceTime < times.size() && indicePartida < faseAposPlayIn.size()) {
            PartidaBase partida = faseAposPlayIn.get(indicePartida);
            
            if (partida.getTimeA() == null && partida.getTimeB() == null) {
                partida.setTimeA(times.get(indiceTime++));
                
                if (indiceTime < times.size()) {
                    partida.setTimeB(times.get(indiceTime++));
                }
            }
            
            indicePartida++;
        }
    }
    
    private List<IPartida> converterParaListaDePartidas(List<List<PartidaBase>> fases) {
        List<IPartida> todasPartidas = new ArrayList<>();

        for (List<PartidaBase> fase : fases) {
            todasPartidas.addAll(fase);
        }
        
        return todasPartidas;
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
