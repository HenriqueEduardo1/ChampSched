package com.champsched.service;

import com.champsched.dto.CampeonatoRequestDTO;
import com.champsched.dto.CampeonatoResponseDTO;
import com.champsched.model.Campeonato;
import com.champsched.model.Time;
import com.champsched.model.User;
import com.champsched.repository.CampeonatoRepository;
import com.champsched.repository.TimeRepository;
import com.champsched.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final UserRepository userRepository;
    private final TimeRepository timeRepository;

    @Transactional
    public CampeonatoResponseDTO createCampeonato(CampeonatoRequestDTO request) {

        User organizador = userRepository.findUserById(request.getOrganizadorId())
                .orElseThrow(() -> new RuntimeException("Organizador não encontrado com ID: " + request.getOrganizadorId()));
        
        Campeonato campeonato = new Campeonato();

        campeonato.setNome(request.getNome());
        campeonato.setEsporte(request.getEsporte());
        campeonato.setData(request.getData());
        campeonato.setOrganizador(organizador);
        
        if (request.getTimesIds() != null) {

            List<Time> times = request.getTimesIds().stream()
                .map(id -> timeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + id)))
                .collect(Collectors.toList());
            
            campeonato.setTimes(times);
            
            times.forEach(time -> {
                if (!time.getCampeonatos().contains(campeonato)) {
                    time.getCampeonatos().add(campeonato);
                }
            });
        }
        
        Campeonato savedCampeonato = campeonatoRepository.save(campeonato);

        return new CampeonatoResponseDTO(savedCampeonato);
    }
    
    public CampeonatoResponseDTO getCampeonatoById(int id) {
        Campeonato campeonato = campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado com ID: " + id));
                
        return new CampeonatoResponseDTO(campeonato);
    }
    
    public List<CampeonatoResponseDTO> getAllCampeonatos() {

        return campeonatoRepository.findAll().stream()
                .map(CampeonatoResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public CampeonatoResponseDTO updateCampeonato(int id, CampeonatoRequestDTO request) {

        Campeonato campeonato = campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado com ID: " + id));
        
        campeonato.setNome(request.getNome());
        campeonato.setEsporte(request.getEsporte());
        campeonato.setData(request.getData());
        
        if (request.getOrganizadorId() != null) {
            User organizador = userRepository.findUserById(request.getOrganizadorId())
                    .orElseThrow(() -> new RuntimeException("Organizador não encontrado com ID: " + request.getOrganizadorId()));

            campeonato.setOrganizador(organizador);
        }
        
        if (request.getTimesIds() != null) {
        
            campeonato.getTimes().forEach(time -> time.getCampeonatos().remove(campeonato));
            
            List<Time> times = request.getTimesIds().stream()
                .map(timeId -> timeRepository.findById(timeId)
                    .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + timeId)))
                .collect(Collectors.toList());
            
            campeonato.setTimes(times);
            
            times.forEach(time -> {
                if (!time.getCampeonatos().contains(campeonato)) {
                    time.getCampeonatos().add(campeonato);
                }
            });
        }
        
        Campeonato updatedCampeonato = campeonatoRepository.save(campeonato);

        return new CampeonatoResponseDTO(updatedCampeonato);
    }
    
    @Transactional
    public void deleteCampeonato(int id) {

        Campeonato campeonato = campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado com ID: " + id));
        
        campeonato.getTimes().forEach(time -> time.getCampeonatos().remove(campeonato));
        
        campeonatoRepository.delete(campeonato);
    }
    
    @Transactional
    public CampeonatoResponseDTO addTime(int campeonatoId, int timeId) {

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado com ID: " + campeonatoId));
        
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + timeId));
        
        if (!campeonato.getTimes().contains(time)) {

            campeonato.getTimes().add(time);
            time.getCampeonatos().add(campeonato);

            campeonatoRepository.save(campeonato);
        }
        
        return new CampeonatoResponseDTO(campeonato);
    }
    
    @Transactional
    public CampeonatoResponseDTO removeTime(int campeonatoId, int timeId) {

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado com ID: " + campeonatoId));
        
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + timeId));
        
        campeonato.getTimes().remove(time);
        time.getCampeonatos().remove(campeonato);
        
        campeonatoRepository.save(campeonato);
        
        return new CampeonatoResponseDTO(campeonato);
    }
}
