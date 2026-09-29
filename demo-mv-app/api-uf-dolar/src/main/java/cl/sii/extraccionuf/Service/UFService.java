package main.java.cl.sii.extraccionuf.Service;

import main.java.cl.sii.extraccionuf.Helpers.UFScraper;
import main.java.cl.sii.extraccionuf.Model.UF;

public class UFService {
    private final UFScraper ufScraper = new UFScraper();

    public UF getCurrentUF() {
        try {
            return ufScraper.extractCurrentUF();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener la UF actual: " + e.getMessage(), e);
        }
    }
}
