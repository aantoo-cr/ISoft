package com.example.api_uf_dolar_project.service;

import com.example.api_uf_dolar_project.helpers.HelperUFDolar;
import com.example.api_uf_dolar_project.model.Valor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ScraperUF {
    private final HelperUFDolar helper;

    public ScraperUF(@Value("${uf.scraper.url}") String url) {
        this.helper = new HelperUFDolar(url);
    }

    public Valor obtenerHoy() {
        try {
            return helper.extractCurrentValor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Map<String, Object> debug() {
        try {
            return helper.debugSnapshot();
        } catch (IOException e) {
            return Map.of("error", e.getMessage());
        }
    }
}


