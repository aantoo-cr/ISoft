package main.java.cl.sii.extraccionuf.Controller;

import main.java.cl.sii.extraccionuf.Helpers.WebHelpers;
import main.java.cl.sii.extraccionuf.Model.ValorDolar;
import main.java.cl.sii.extraccionuf.Service.ValorService;

import java.time.LocalDate;

public class ValorController {
    private final ValorService service = new ValorService(new WebHelpers());
    // Método para mostrar el valor del dólar de hoy
    public void mostrarDolarDeHoy() {
        try {
            ValorDolar vd = service.obtenerDolar(LocalDate.now());
            System.out.println(vd);
        } catch (Exception e) {
            System.err.println("Error al obtener dólar: " + e.getMessage());
        }
    }
}
