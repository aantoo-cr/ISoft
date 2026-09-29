package main.java.cl.sii.extraccionuf.Model;

import java.time.LocalDate;

public class UF {
    private LocalDate fecha;
    private double valor;

    public UF(LocalDate fecha, double valor) {
        this.fecha = fecha;
        this.valor = valor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "UF{fecha=" + fecha + ", valor=" + valor + '}';
    }
}
