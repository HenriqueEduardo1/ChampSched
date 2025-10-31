package com.champsched.controller;

import com.champsched.dto.CampeonatoRequestDTO;
import com.champsched.dto.CampeonatoResponseDTO;
import com.champsched.service.CampeonatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos")
@RequiredArgsConstructor
public class CampeonatoController {

    private final CampeonatoService campeonatoService;

    @PostMapping
    public ResponseEntity<CampeonatoResponseDTO> createCampeonato(@RequestBody CampeonatoRequestDTO request) {

        CampeonatoResponseDTO response = campeonatoService.createCampeonato(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampeonatoResponseDTO> getCampeonatoById(@PathVariable int id) {

        CampeonatoResponseDTO response = campeonatoService.getCampeonatoById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CampeonatoResponseDTO>> getAllCampeonatos() {

        List<CampeonatoResponseDTO> response = campeonatoService.getAllCampeonatos();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampeonatoResponseDTO> updateCampeonato(@PathVariable int id, @RequestBody CampeonatoRequestDTO request) {

        CampeonatoResponseDTO response = campeonatoService.updateCampeonato(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampeonato(@PathVariable int id) {

        campeonatoService.deleteCampeonato(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{campeonatoId}/times/{timeId}")
    public ResponseEntity<CampeonatoResponseDTO> addTime(@PathVariable int campeonatoId, @PathVariable int timeId) {

        CampeonatoResponseDTO response = campeonatoService.addTime(campeonatoId, timeId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{campeonatoId}/times/{timeId}")
    public ResponseEntity<CampeonatoResponseDTO> removeTime(@PathVariable int campeonatoId, @PathVariable int timeId) {

        CampeonatoResponseDTO response = campeonatoService.removeTime(campeonatoId, timeId);
        
        return ResponseEntity.ok(response);
    }
}
