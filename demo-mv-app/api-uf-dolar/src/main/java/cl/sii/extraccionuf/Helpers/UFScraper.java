package main.java.cl.sii.extraccionuf.Helpers;

import main.java.cl.sii.extraccionuf.Model.UF;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

public class UFScraper {
    private static final String URL = "https://www.sii.cl/valores_y_fechas/uf/uf2025.htm";

    public UF extractCurrentUF() throws IOException {
        Document doc = Jsoup.connect(URL)
                .timeout(10000)
                .userAgent("Mozilla/5.0")
                .get();

        Element table = doc.select("table").first();
        if (table == null) {
            throw new RuntimeException("No se encontró la tabla en la página del SII");
        }

        LocalDate hoy = LocalDate.now();
        int diaHoy = hoy.getDayOfMonth();
        YearMonth mes = YearMonth.from(hoy);

        Elements rows = table.select("tr");

        UF ultimaUF = null;

        for (Element row : rows) {
            Elements cells = row.select("td");

            for (int i = 0; i < cells.size(); i++) {
                String valorStr = cells.get(i).text()
                        .replace(".", "")
                        .replace(",", ".")
                        .trim();

                if (valorStr.matches("\\d+(\\.\\d+)?")) {
                    int diaTabla = i + 1; // si no se suma 1 no es un día válido
                    if (diaTabla > mes.lengthOfMonth()) {
                        continue;
                    }

                    LocalDate fecha = LocalDate.of(mes.getYear(), mes.getMonth(), diaTabla);

                    try {
                        double valor = Double.parseDouble(valorStr);
                        UF uf = new UF(fecha, valor);

                        ultimaUF = uf;

                        if (diaTabla == diaHoy) {
                            return uf;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        if (ultimaUF != null) {
            System.out.println("No se encontró el valor exacto de hoy (" + hoy + "), se mostrará último disponible.");
            return ultimaUF;
        }

        throw new RuntimeException("No se encontraron valores UF en la tabla del SII");
    }
}
