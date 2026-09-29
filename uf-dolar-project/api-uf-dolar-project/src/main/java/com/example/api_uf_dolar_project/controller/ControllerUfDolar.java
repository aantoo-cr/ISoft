package com.example.api_uf_dolar_project.controller;

import com.example.api_uf_dolar_project.model.Valor;
import com.example.api_uf_dolar_project.service.Dolar;
import com.example.api_uf_dolar_project.service.UF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerUfDolar {

    private final UF ufService;
    private final Dolar dolarService;

    public ControllerUfDolar(UF ufService, Dolar dolarService) {
        this.ufService = ufService;
        this.dolarService = dolarService;
    }

    @GetMapping("/uf")
    public ResponseEntity<Map<String, Object>> getUf() {
        try {
            Valor data = ufService.respuesta();
            return ResponseEntity.ok(Map.of(
                    "data", data,
                    "source", "SII/BC",
                    "timestamp", Instant.now().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of(
                    "error", "No se pudo obtener UF",
                    "detail", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        }
    }

    @GetMapping("/dolar")  // ← SOLO UN MÉTODO GETDOLAR
    public ResponseEntity<Map<String, Object>> getDolar() {
        try {
            Valor data = dolarService.respuesta();
            return ResponseEntity.ok(Map.of(
                    "data", data,
                    "source", "SII/BC",
                    "timestamp", Instant.now().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of(
                    "error", "No se pudo obtener dólar",
                    "detail", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        }
    }

    @GetMapping("/debug/dolar")
    public Object debugDolar() {
        return dolarService.debug();
    }

    @GetMapping("/debug/uf")
    public Object debugUf() {
        return ufService.debug();
    }
}