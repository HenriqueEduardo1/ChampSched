package com.champsched.controller;

import com.champsched.dto.TimeRequestDTO;
import com.champsched.dto.TimeResponseDTO;
import com.champsched.service.TimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/times")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TimeController {

    private final TimeService timeService;

    @PostMapping
    public ResponseEntity<TimeResponseDTO> createTime(@Valid @RequestBody TimeRequestDTO request) {
        TimeResponseDTO response = timeService.createTime(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeResponseDTO> getTimeById(@PathVariable int id) {
        TimeResponseDTO response = timeService.getTimeById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponseDTO>> getAllTimes() {
        List<TimeResponseDTO> response = timeService.getAllTimes();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeResponseDTO> updateTime(@PathVariable int id,@Valid @RequestBody TimeRequestDTO request) {
        TimeResponseDTO response = timeService.updateTime(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable int id) {
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{timeId}/integrantes/{userId}")
    public ResponseEntity<TimeResponseDTO> addIntegrante(@PathVariable int timeId, @PathVariable int userId) {
        TimeResponseDTO response = timeService.addIntegrante(timeId, userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{timeId}/integrantes/{userId}")
    public ResponseEntity<TimeResponseDTO> removeIntegrante(@PathVariable int timeId, @PathVariable int userId) {
        TimeResponseDTO response = timeService.removeIntegrante(timeId, userId);
        
        return ResponseEntity.ok(response);
    }

}
