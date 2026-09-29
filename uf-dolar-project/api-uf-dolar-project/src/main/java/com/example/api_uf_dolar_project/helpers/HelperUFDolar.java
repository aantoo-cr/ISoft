package com.example.api_uf_dolar_project.helpers;

import com.example.api_uf_dolar_project.model.Valor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HelperUFDolar {

    private final String url;

    public HelperUFDolar(String url) { this.url = url; }


    private static String norm(String s) {
        if (s == null) return "";
        String n = Normalizer.normalize(s, Normalizer.Form.NFD);
        n = n.replaceAll("\\p{M}+", "");
        return n.trim().toLowerCase();
    }

    private static String onlyDigits(String s) {
        if (s == null) return "";
        return s.replace('\u00A0', ' ')   // NBSP
                .replaceAll("[^0-9]", "");
    }

    private static String fixMoney(String s) {
        if (s == null) return "";
        return s.replace('\u00A0', ' ')
                .replace(".", "")   // miles
                .replace(",", ".")  // decimales
                .trim();
    }

    private static boolean looksLikeMoney(String s) {
        return s.matches("\\d+(?:\\.\\d+)?");
    }


    public Valor extractCurrentValor() throws IOException {
        Document doc = Jsoup.connect(this.url)
                .timeout(15000)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .referrer("https://www.google.com")
                .header("Accept-Language", "es-CL,es;q=0.9")
                .get();

        ZoneId cl = ZoneId.of("America/Santiago");
        LocalDate hoy = LocalDate.now(cl);
        YearMonth ym = YearMonth.from(hoy);
        int diaHoy = hoy.getDayOfMonth();

        BigDecimal ultimoMonto = null;
        LocalDate ultimaFecha = null;


        Elements tablas = doc.select("table");
        for (int t = 0; t < tablas.size(); t++) {
            Element tabla = tablas.get(t);
            Elements filas = tabla.select("tr");

            for (int r = 0; r < filas.size(); r++) {
                Element tr = filas.get(r);

                Elements celdas = tr.select("th, td");

                for (int i = 0; i + 1 < celdas.size(); i++) {

                    String sDia = onlyDigits(celdas.get(i).text());
                    if (!sDia.matches("^(?:[1-9]|[12][0-9]|3[01])$")) continue;


                    String sVal = fixMoney(celdas.get(i + 1).text());
                    if (!looksLikeMoney(sVal)) continue;

                    try {
                        int d = Integer.parseInt(sDia);
                        if (d > ym.lengthOfMonth()) continue;

                        BigDecimal monto = new BigDecimal(sVal).setScale(2, java.math.RoundingMode.HALF_UP);
                        LocalDate fecha = LocalDate.of(ym.getYear(), ym.getMonth(), d);


                        ultimoMonto = monto;
                        ultimaFecha = fecha;


                        if (d == diaHoy) {
                            return new Valor(fecha, monto);
                        }
                    } catch (Exception ignore) {  }


                    i++;
                }
            }
        }

        if (ultimoMonto != null && ultimaFecha != null) {
            return new Valor(ultimaFecha, ultimoMonto);
        }
        throw new RuntimeException("No se encontró un valor válido en la tabla del SII.");
    }


    public Map<String, Object> debugSnapshot() throws IOException {
        Document doc = Jsoup.connect(this.url)
                .timeout(12000)
                .userAgent("Mozilla/5.0")
                .get();

        int tablas = doc.select("table").size();

        List<String> sampleCells = new ArrayList<>();
        Elements cells = doc.select("th, td");
        int limit = Math.min(24, cells.size());
        for (int i = 0; i < limit; i++) sampleCells.add(cells.get(i).text());

        ZoneId cl = ZoneId.of("America/Santiago");
        LocalDate hoy = LocalDate.now(cl);
        Locale esCL = new Locale("es", "CL");
        String nombreMes = hoy.format(java.time.format.DateTimeFormatter.ofPattern("LLLL", esCL));
        nombreMes = nombreMes.substring(0, 1).toUpperCase(esCL) + nombreMes.substring(1);

        boolean h3Ok = false;
        Elements h3s = doc.select("h3");
        for (int i = 0; i < h3s.size(); i++) {
            if (norm(h3s.get(i).text()).equals(norm(nombreMes))) { h3Ok = true; break; }
        }

        return Map.of(
                "url", this.url,
                "tablas", tablas,
                "mes", nombreMes,
                "h3_encontrado", h3Ok,
                "primeras_celdas", sampleCells
        );
    }
}


