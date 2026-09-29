package main.java.cl.sii.extraccionuf.Service;

import java.time.LocalDate;
import main.java.cl.sii.extraccionuf.Helpers.WebHelpers;
import main.java.cl.sii.extraccionuf.Model.ValorDolar;


public class ValorService {
    private final WebHelpers web;

    // Inyección de dependencia a través del constructor
    public ValorService(WebHelpers web) {
        this.web = web;
    }

    // Método para obtener el valor del dólar para una fecha específica
    public ValorDolar obtenerDolar(LocalDate fecha) throws Exception {
        double valor = web.fetchCurrentDolar();
        return new ValorDolar(fecha, valor);

    }
}