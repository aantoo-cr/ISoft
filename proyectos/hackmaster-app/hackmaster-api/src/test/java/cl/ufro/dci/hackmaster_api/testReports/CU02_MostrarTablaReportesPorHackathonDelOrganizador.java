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
public class CU02_MostrarTablaReportesPorHackathonDelOrganizador {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportsService reportsService; // Puedes usar el servicio real

    @Test
    public void CP01_ReportHackathonListTest() throws Exception {

        // Simula una solicitud GET a la URL del reporte de métricas del organizador con un ID
        mockMvc.perform(get("/api/reports/organizador/lista/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("organizador"))
                .andExpect(jsonPath("$.actorName").value("Falabella"))
                .andExpect(jsonPath("$.hackatones").isArray())
                .andExpect(jsonPath("$.hackatones[0].hackatonName").value("HackMIT"))
                .andExpect(jsonPath("$.hackatones[0].fechaInicio").value("2024-10-21"))
                .andExpect(jsonPath("$.hackatones[0].fechaTermino").value("2024-11-21"))
                .andExpect(jsonPath("$.hackatones[1].hackatonName").value("AngelHack"))
                .andExpect(jsonPath("$.hackatones[1].fechaInicio").value("2022-05-21"))
                .andExpect(jsonPath("$.hackatones[1].fechaTermino").value("2022-09-18"))
                .andExpect(jsonPath("$.hackatones[2].hackatonName").value("MOSIP CREATE"))
                .andExpect(jsonPath("$.hackatones[2].fechaInicio").value("2022-07-12"))
                .andExpect(jsonPath("$.hackatones[2].fechaTermino").value("2022-12-21"));
    }
    @Test
    public void CP02_ReportHackathonListEmptyTest() throws Exception {
        // Realiza la solicitud GET
        mockMvc.perform(get("/api/reports/organizador/lista/2")) // Cambia el ID según sea necesario
                .andExpect(status().isOk()) // Verifica que la respuesta sea 200 OK
                .andExpect(content().json("{\"actorType\":\"organizador\",\"actorName\":\"Sodimac\",\"hackatones\":\"No ha organizado ningún Hackathon\"}"));
    }
}
