package main.java.cl.sii.extraccionuf.Helpers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebHelpers {
    //Hace el trabajo de conectarse a la web del SII y extraer el valor actual del Dolar.
    private static final String BASE_URL= "https://www.sii.cl/valores_y_fechas/dolar/dolar2025.htm";
    private static final Pattern NUM_CL = Pattern.compile("([0-9]{1,3}(?:\\.[0-9]{3})*,\\d{2})");

    //Extrae el valor actual del dolar desde la página web del SII.
    public double fetchCurrentDolar() throws IOException {


        // Conexión a la página usando Jsoup
        Document doc = Jsoup.connect(BASE_URL)
                .timeout(10000)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .get();


        // Busca el primer número con formato chileno
        String text = doc.text();
        Matcher m = NUM_CL.matcher(text);
        if (m.find()) {
            String raw = m.group(1);
            return parseChileanNumber(raw);
        }
        throw new IllegalStateException(
                "No se encontró número con formato chileno en " + BASE_URL);
    }
    // Convierte un número en formato chileno (1.234,56) a double (1234.56)
    private static double parseChileanNumber(String s) {
        String norm = s.replace(".", "")
                .replace(",", ".")
                .replaceAll("[^0-9.]", "");
        return Double.parseDouble(norm);
    }
}