package com.example.api_uf_dolar_project.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Valor {
    private LocalDate fecha;
    private BigDecimal monto;

    public Valor(LocalDate fecha, BigDecimal monto) {
        this.fecha = fecha;
        this.monto = monto;
    }

    public LocalDate getFecha() { return fecha; }
    public BigDecimal getMonto() { return monto; }
}

