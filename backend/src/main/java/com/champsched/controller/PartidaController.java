package com.champsched.controller;

import com.champsched.dto.PartidaDTO;
import com.champsched.service.PartidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/partidas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PartidaController {

    private final PartidaService partidaService;

    
    @PostMapping("/campeonato/{campeonatoId}/gerar-chaveamento")
    public ResponseEntity<?> gerarChaveamento(@PathVariable Integer campeonatoId) {
        try {
            partidaService.gerarChaveamento(campeonatoId);

            List<PartidaDTO> partidas = partidaService.listarPartidas(campeonatoId);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Chaveamento gerado com sucesso",
                "totalPartidas", partidas.size(),
                "partidas", partidas
            ));

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao gerar chaveamento: " + e.getMessage()));
        }
    }

        @GetMapping("/campeonato/{campeonatoId}")
    public ResponseEntity<List<PartidaDTO>> listarPartidas(@PathVariable Integer campeonatoId) {
        List<PartidaDTO> partidas = partidaService.listarPartidas(campeonatoId);
        
        return ResponseEntity.ok(partidas);
    }
}
