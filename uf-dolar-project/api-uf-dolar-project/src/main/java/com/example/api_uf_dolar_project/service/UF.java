package com.example.api_uf_dolar_project.service;
import com.example.api_uf_dolar_project.model.Valor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UF {
    private final ScraperUF scraper;

    public UF(ScraperUF scraper) {
        this.scraper = scraper;
    }

    public Valor respuesta() {
        return scraper.obtenerHoy();
    }

    public Map<String, Object> debug() {
        return scraper.debug();
    }
}
