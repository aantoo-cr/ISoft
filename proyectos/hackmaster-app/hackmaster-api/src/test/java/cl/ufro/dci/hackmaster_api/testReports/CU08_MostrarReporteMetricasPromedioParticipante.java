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
public class CU08_MostrarReporteMetricasPromedioParticipante {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportsService reportsService; // Puedes usar el servicio real

    @Test
    public void CP01_ReportParticipantMetricsTest() throws Exception {
        // Simula una solicitud GET a la URL del reporte de métricas del participante con un ID
        mockMvc.perform(get("/api/reports/participante/detalles/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("participante"))
                .andExpect(jsonPath("$.numberOfVictories").value(1))
                .andExpect(jsonPath("$.totalHackatonesParticipados").value(3))
                .andExpect(jsonPath("$.collaborators").isArray())
                .andExpect(jsonPath("$.collaborators[0].collaborationsCount").value(2))
                .andExpect(jsonPath("$.collaborators[0].collaboratorName").value("Benjamin Cruzado"))
                .andExpect(jsonPath("$.collaborators[1].collaborationsCount").value(1))
                .andExpect(jsonPath("$.collaborators[1].collaboratorName").value("Luis Contreras"))
                .andExpect(jsonPath("$.actorName").value("Felipe Monsalves"));
    }

    @Test
    public void CP02_ReportParticipantMetricsNoParticipationTest() throws Exception {
        // Realiza la solicitud GET
        mockMvc.perform(get("/api/reports/participante/detalles/13"))
                .andExpect(status().isOk()) // Verifica que la respuesta sea 200 OK
                .andExpect(content().json("{\"actorType\":\"participante\",\"numberOfVictories\":0,\"totalHackatonesParticipados\":0,\"collaborators\":\"no hay colaboradores en común\",\"actorName\":\"Felipe Avello\"}"));
    }

    @Test
    public void CP03_ReportParticipantMetricsIncompleteDataTest() throws Exception {
        mockMvc.perform(get("/api/reports/participante/detalles/14"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("participante"))
                .andExpect(jsonPath("$.numberOfVictories").value(0))
                .andExpect(jsonPath("$.totalHackatonesParticipados").value(1))
                .andExpect(jsonPath("$.collaborators").isArray())
                .andExpect(jsonPath("$.collaborators[0].collaborationsCount").value(1))
                .andExpect(jsonPath("$.collaborators[0].collaboratorName").value("Benjamin Cruzado"))
                .andExpect(jsonPath("$.actorName").value("Edo Caroe"));
    }
}
