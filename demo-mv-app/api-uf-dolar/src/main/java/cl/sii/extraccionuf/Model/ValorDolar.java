package main.java.cl.sii.extraccionuf.Model;

import java.time.LocalDate;

public class ValorDolar {
    private LocalDate fecha;
    private double valorDolar;

    public ValorDolar(LocalDate fecha, double valorDolar) {
        this.fecha = fecha;
        this.valorDolar = valorDolar;
    }

    public void setValorDolar(double valorDolar) {
        this.valorDolar = valorDolar;
    }

    public double getValorDolar() {
        return valorDolar;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Fecha: " + fecha + " Valor del dolar: $" + String.format("%,.2f", valorDolar);
    }
}
