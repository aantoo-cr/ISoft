package main.java.cl.sii.extraccionuf.Controller;

import main.java.cl.sii.extraccionuf.Model.UF;
import main.java.cl.sii.extraccionuf.Service.UFService;

public class UFController {
    private final UFService ufService = new UFService();

    public void mostrarValorActual() {
        try {
            UF uf = ufService.getCurrentUF();
            System.out.println("Valor de la UF actual: " + uf.getValor() + " (Fecha: " + uf.getFecha() + ")");
        } catch (Exception e) {
            System.out.println("Error al obtener valor de la UF: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Iniciando extracción de la UF...");
        new UFController().mostrarValorActual();
    }
}
