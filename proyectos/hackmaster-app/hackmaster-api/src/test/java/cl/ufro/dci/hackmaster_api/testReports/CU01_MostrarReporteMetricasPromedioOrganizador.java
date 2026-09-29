package cl.ufro.dci.hackmaster_api.testReports;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import cl.ufro.dci.hackmaster_api.reports.service.ReportsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc // Esto permite que se configure MockMvc automáticamente
public class CU01_MostrarReporteMetricasPromedioOrganizador {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportsService reportsService; // Puedes usar el servicio real

    @Test
    public void CP01_ReportMetricsOrganizerTest() throws Exception {

        // Simula una solicitud GET a la URL del reporte de métricas del organizador con un ID
        mockMvc.perform(get("/api/reports/organizador/metricas/1" ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("organizador"))
                .andExpect(jsonPath("$.actorName").value("Falabella"))
                .andExpect(jsonPath("$.hackatones").isArray())
                .andExpect(jsonPath("$.hackatones[0].hackatonName").value("HackMIT"))
                .andExpect(jsonPath("$.hackatones[0].totalInscripciones").value(4))
                .andExpect(jsonPath("$.hackatones[0].ingresosGenerados").value(80000.0))
                .andExpect(jsonPath("$.hackatones[0].totalPremios").value(150000.0))
                .andExpect(jsonPath("$.hackatones[1].hackatonName").value("AngelHack"))
                .andExpect(jsonPath("$.hackatones[1].totalInscripciones").value(6))
                .andExpect(jsonPath("$.hackatones[1].ingresosGenerados").value(60000.0))
                .andExpect(jsonPath("$.hackatones[1].totalPremios").value(50000.0))
                .andExpect(jsonPath("$.hackatones[2].hackatonName").value("MOSIP CREATE"))
                .andExpect(jsonPath("$.hackatones[2].totalInscripciones").value(4))
                .andExpect(jsonPath("$.hackatones[2].ingresosGenerados").value(20000.0))
                .andExpect(jsonPath("$.hackatones[2].totalPremios").value(57000.0));
    }
    @Test
    public void CP02_ReportMetricsOrganizerEmptyTest() throws Exception {
        // Realiza la solicitud GET
        mockMvc.perform(get("/api/reports/organizador/metricas/2"))
                .andExpect(status().isOk()) // Verifica que la respuesta sea 200 OK
                .andExpect(content().json("{\"actorType\":\"organizador\",\"actorName\":\"Sodimac\",\"hackatones\":\"No ha organizado ningun Hackathon\"}"));
    }
    @Test
    public void CP03_ReportMetricsOrganizerIncompleteDataTest() throws Exception {
        mockMvc.perform(get("/api/reports/organizador/metricas/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("organizador"))
                .andExpect(jsonPath("$.actorName").value("Easy"))
                .andExpect(jsonPath("$.hackatones[0].ingresosGenerados").value(0.0))
                .andExpect(jsonPath("$.hackatones[0].totalPremios").value(150000.0))
                .andExpect(jsonPath("$.hackatones[0].hackatonName").value("OrangeHack"))
                .andExpect(jsonPath("$.hackatones[0].totalInscripciones").value(0));
    }
}
