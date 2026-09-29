package com.example.api_taller1.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Extractor {

    public String obtenerPresidentePorRut(String rutCompleto) {
        try {

            String rut = rutCompleto.split("-")[0];

            String urlConsulta = "https://www.cmfchile.cl/institucional/mercados/consulta.php?mercado=V&Estado=VI&entidad=RVEMI";

            Document docConsulta = Jsoup.connect(urlConsulta).get();

            Elements enlaces = docConsulta.select("a[href*='rut=" + rut + "']");

            if (enlaces.isEmpty()) {
                return "No se encontró la empresa con RUT: " + rutCompleto;
            }

            Element enlaceEmpresa = enlaces.first();
            String href = enlaceEmpresa.attr("href");

            String row = null;
            String[] parametros = href.split("&");
            for (String param : parametros) {
                if (param.startsWith("row=")) {
                    row = param.substring(4);
                    break;
                }
            }

            if (row == null || row.isEmpty()) {
                return "No ha sido posible encontrar el parámetro 'row' para el RUT: " + rutCompleto;
            }

            //construir nueva URL
            String urlDirectores = "https://www.cmfchile.cl/institucional/mercados/entidad.php?mercado=V&rut="
                    + rut + "&grupo=&tipoentidad=RVEMI&row=" + row + "&vig=VI&control=svs&pestania=46";

            Document docDirectores = Jsoup.connect(urlDirectores).get();

            // Buscar información en la tabla de directores (con IA pues no supe ajustar que encontrara el valor del nombre del presidente por mi cuenta)
            Elements filas = docDirectores.select("table tr");

            for (Element fila : filas) {
                Elements celdas = fila.select("td");
                if (celdas.size() >= 4) { // Debe tener al menos 4 columnas: RUT, Nombre, Cargo, Fecha
                    String cargo = celdas.get(2).text().trim(); // Tercera columna es el cargo

                    if (cargo.equalsIgnoreCase("Presidente")) {
                        // Segunda columna es el nombre
                        String nombrePresidente = celdas.get(1).text().trim();
                        return nombrePresidente;
                    }
                }
            }

            return "No se encontró presidente para el RUT: " + rutCompleto;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener datos de la CMF: " + e.getMessage();
        }
    }
}