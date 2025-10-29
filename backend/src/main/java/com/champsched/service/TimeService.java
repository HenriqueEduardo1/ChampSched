package com.champsched.service;

import java.util.List;
import java.util.stream.Collectors;

import com.champsched.dto.TimeRequestDTO;
import com.champsched.dto.TimeResponseDTO;
import com.champsched.model.Time;
import com.champsched.model.User;
import com.champsched.repository.TimeRepository;
import com.champsched.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;
    private final UserRepository userRepository;

    @Transactional
    public TimeResponseDTO createTime(TimeRequestDTO request) {
        Time time = new Time();
        time.setNome(request.getNome());
        time.setContato(request.getContato());
        
        if (request.getIntegrantesIds() != null) {
            List<User> integrantes = request.getIntegrantesIds().stream()
                .map(id -> userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id)))
                .collect(Collectors.toList());
                
            time.setIntegrantes(integrantes);
        }
        
        Time savedTime = timeRepository.save(time);

        return new TimeResponseDTO(savedTime);
    }
    
    public TimeResponseDTO getTimeById(int id) {
        Time time = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + id));

        return new TimeResponseDTO(time);
    }
    
    public List<TimeResponseDTO> getAllTimes() {
        return timeRepository.findAll().stream()
                .map(TimeResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public TimeResponseDTO updateTime(int id, TimeRequestDTO request) {
        Time time = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + id));
        
        time.setNome(request.getNome());
        time.setContato(request.getContato());
        
        if (request.getIntegrantesIds() != null) {
            List<User> integrantes = request.getIntegrantesIds().stream()
                .map(userId -> userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId)))
                .collect(Collectors.toList());

            time.setIntegrantes(integrantes);
        }
        
        Time updatedTime = timeRepository.save(time);

        return new TimeResponseDTO(updatedTime);
    }
    
    @Transactional
    public void deleteTime(int id) {
        Time time = timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + id));

        timeRepository.delete(time);
    }
    
    @Transactional
    public TimeResponseDTO addIntegrante(int timeId, int userId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + timeId));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
        
        if (!time.getIntegrantes().contains(user)) {
            time.getIntegrantes().add(user);

            timeRepository.save(time);
        }
        
        return new TimeResponseDTO(time);
    }
    
    @Transactional
    public TimeResponseDTO removeIntegrante(int timeId, int userId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + timeId));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
        
        time.getIntegrantes().remove(user);
        
        timeRepository.save(time);
        
        return new TimeResponseDTO(time);
    }
    
}
