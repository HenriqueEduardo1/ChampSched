package com.champsched.controller;

import com.champsched.dto.UserRequestDTO;
import com.champsched.dto.UserResponseDTO;
import com.champsched.dto.TimeResponseDTO;
import com.champsched.dto.CampeonatoResponseDTO;
import com.champsched.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
        UserResponseDTO response = userService.getUserById(id);

        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable int id, @Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.updateUser(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/times")
    public ResponseEntity<List<TimeResponseDTO>> getUserTimes(@PathVariable int id) {
        List<TimeResponseDTO> times = userService.getUserTimes(id);
        
        return ResponseEntity.ok(times);
    }
    
    @GetMapping("/{id}/campeonatos")
    public ResponseEntity<List<CampeonatoResponseDTO>> getUserCampeonatos(@PathVariable int id) {
        List<CampeonatoResponseDTO> campeonatos = userService.getUserCampeonatos(id);

        return ResponseEntity.ok(campeonatos);
    }
    
    @GetMapping("/{id}/campeonatos-organizados")
    public ResponseEntity<List<CampeonatoResponseDTO>> getUserCampeonatosOrganizados(@PathVariable int id) {
        List<CampeonatoResponseDTO> campeonatos = userService.getUserCampeonatosOrganizados(id);
        
        return ResponseEntity.ok(campeonatos);
    }
    
}
