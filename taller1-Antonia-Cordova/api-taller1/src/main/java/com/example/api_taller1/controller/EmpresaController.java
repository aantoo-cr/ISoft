package com.example.api_taller1.controller;

import com.example.api_taller1.extractor.Extractor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpresaController {

    private final Extractor extractor = new Extractor();

    @GetMapping("/empresa/{rut}")
    public String obtenerPresidente(@PathVariable String rut) {
        return extractor.obtenerPresidentePorRut(rut);
    }
}