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
        int potenciaBase = calcularMaiorPotenciaDe2MenorOuIgual(numTimes);
        int partidasPlayIn = numTimes > potenciaBase ? (numTimes - potenciaBase) : 0;
        int timesComBye = partidasPlayIn > 0 ? (2 * potenciaBase - numTimes) : potenciaBase;
        int partidasPrimeiraFasePrincipal = potenciaBase / 2;

        List<List<PartidaBase>> fases = criarEstruturaDeFases(campeonato, partidasPrimeiraFasePrincipal, partidasPlayIn);

        conectarFases(fases, partidasPlayIn);
        distribuirTimes(times, fases, partidasPlayIn, timesComBye);

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
    
    private int calcularMaiorPotenciaDe2MenorOuIgual(int numTimes) {
        int potencia = 1;

        while (potencia * 2 <= numTimes) {
            potencia *= 2;
        }

        return potencia;
    }
    
    private List<List<PartidaBase>> criarEstruturaDeFases(Campeonato campeonato, int partidasPrimeiraFasePrincipal, int partidasPlayIn) {
        List<List<PartidaBase>> fases = new ArrayList<>();
        
        if (partidasPlayIn > 0) {
            fases.add(criarFasePlayIn(campeonato, partidasPlayIn));
        }
        
        fases.addAll(criarFasesPrincipais(campeonato, partidasPrimeiraFasePrincipal, partidasPlayIn > 0));
        
        return fases;
    }
    
    private List<PartidaBase> criarFasePlayIn(Campeonato campeonato, int partidasPlayIn) {
        List<PartidaBase> fasePlayIn = new ArrayList<>();
        
        for (int i = 0; i < partidasPlayIn; i++) {
            PartidaBase partida = (PartidaBase) criarPartida(campeonato, null, null);
            partida.setFase(0);

            fasePlayIn.add(partida);
        }
        
        return fasePlayIn;
    }
    
    private List<List<PartidaBase>> criarFasesPrincipais(Campeonato campeonato, int partidasIniciais, boolean temPlayIn) {
        List<List<PartidaBase>> fases = new ArrayList<>();

        int numeroFase = 1;
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
    
    private void conectarFases(List<List<PartidaBase>> fases, int partidasPlayIn) {
        for (int fase = 0; fase < fases.size() - 1; fase++) {
            List<PartidaBase> faseAtual = fases.get(fase);
            List<PartidaBase> proximaFase = fases.get(fase + 1);

            if (fase == 0 && partidasPlayIn > 0) {
                int totalPartidasFase1 = proximaFase.size();
                int inicioReservado = totalPartidasFase1 - partidasPlayIn;

                for (int i = 0; i < faseAtual.size(); i++) {
                    PartidaBase partidaPlayIn = faseAtual.get(i);
                    int indiceProximaPartida = inicioReservado + i;

                    if (indiceProximaPartida < proximaFase.size()) {
                        partidaPlayIn.setProximaPartida(proximaFase.get(indiceProximaPartida));
                        partidaPlayIn.setPosicaoNaProximaPartida(2);
                    }
                }

                continue; 
            }

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
    
    private void distribuirTimes(List<Time> times, List<List<PartidaBase>> fases, int partidasPlayIn, int timesComBye) {
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
    
    private void distribuirTimesComBye(List<Time> times, List<List<PartidaBase>> fases, int partidasPlayIn, int indiceTimeInicial) {

        List<PartidaBase> faseAposPlayIn = partidasPlayIn > 0 ? fases.get(1) : fases.get(0);
        int indiceTime = indiceTimeInicial;
        int totalPartidasFase1 = faseAposPlayIn.size();

        int inicioReservado = partidasPlayIn > 0 ? (totalPartidasFase1 - partidasPlayIn) : totalPartidasFase1;

        for (int i = 0; i < totalPartidasFase1 && indiceTime < times.size(); i++) {
            PartidaBase partida = faseAposPlayIn.get(i);

            if (i < inicioReservado) {
                if (partida.getTimeA() == null) {
                    partida.setTimeA(times.get(indiceTime++));
                }

                if (indiceTime < times.size() && partida.getTimeB() == null) {
                    partida.setTimeB(times.get(indiceTime++));
                }
            } else {
                if (partida.getTimeA() == null) {
                    partida.setTimeA(times.get(indiceTime++));
                }
            }
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
