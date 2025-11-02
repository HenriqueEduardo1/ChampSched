package com.champsched.service;

import com.champsched.dto.UserRequestDTO;
import com.champsched.dto.UserResponseDTO;
import com.champsched.dto.TimeResponseDTO;
import com.champsched.dto.CampeonatoResponseDTO;
import com.champsched.model.User;
import com.champsched.repository.UserRepository;
import com.champsched.repository.TimeRepository;
import com.champsched.repository.CampeonatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TimeRepository timeRepository;
    private final CampeonatoRepository campeonatoRepository;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request) {
        User user = new User();
        
        user.setNome(request.getNome());
        user.setContato(request.getContato());
        
        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser);
    }
    
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    public UserResponseDTO getUserById(int id) {
        UserResponseDTO user = userRepository.findById(id);

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }

        return user;
    }
    
    @Transactional
    public UserResponseDTO updateUser(int id, UserRequestDTO request) {
        User user = userRepository.findUserById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        user.setNome(request.getNome());
        user.setContato(request.getContato());
        
        User updatedUser = userRepository.save(user);

        return new UserResponseDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(int id) {
        User user = userRepository.findUserById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        userRepository.delete(user);
    }
    
    public List<TimeResponseDTO> getUserTimes(int userId) {
        
        userRepository.findUserById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
        
        return timeRepository.findByIntegrantesId(userId).stream()
                .map(TimeResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    public List<CampeonatoResponseDTO> getUserCampeonatos(int userId) {
        
        userRepository.findUserById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));
        
        return campeonatoRepository.findByParticipanteId(userId).stream()
                .map(CampeonatoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
