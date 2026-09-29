package main.java.cl.sii.extraccionuf;

import main.java.cl.sii.extraccionuf.Controller.UFController;
import main.java.cl.sii.extraccionuf.Controller.ValorController;

public class Main {
    public static void main(String[] args) {
        UFController ufController = new UFController();
        ufController.mostrarValorActual();

        ValorController valorController = new ValorController();
        valorController.mostrarDolarDeHoy();
    }
}