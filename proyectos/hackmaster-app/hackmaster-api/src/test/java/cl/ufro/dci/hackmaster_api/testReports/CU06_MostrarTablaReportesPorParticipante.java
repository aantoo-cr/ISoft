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
public class CU06_MostrarTablaReportesPorParticipante {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportsService reportsService; // Puedes usar el servicio real

    @Test
    public void CP01_ReportParticipantListTest() throws Exception {
        // Simula una solicitud GET a la URL del reporte de métricas del participante con un ID
        mockMvc.perform(get("/api/reports/participante/lista/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("participante"))
                .andExpect(jsonPath("$.actorName").value("Felipe Monsalves"))
                .andExpect(jsonPath("$.hackatonesDetails").isArray())
                .andExpect(jsonPath("$.hackatonesDetails[0].hackatonName").value("HackMIT"))
                .andExpect(jsonPath("$.hackatonesDetails[0].hackatonStartDate").value("2024-10-21"))
                .andExpect(jsonPath("$.hackatonesDetails[0].hackatonEndDate").value("2024-11-21"))
                .andExpect(jsonPath("$.hackatonesDetails[1].hackatonName").value("AngelHack"))
                .andExpect(jsonPath("$.hackatonesDetails[1].hackatonStartDate").value("2022-05-21"))
                .andExpect(jsonPath("$.hackatonesDetails[1].hackatonEndDate").value("2022-09-18"))
                .andExpect(jsonPath("$.hackatonesDetails[2].hackatonName").value("MOSIP CREATE"))
                .andExpect(jsonPath("$.hackatonesDetails[2].hackatonStartDate").value("2022-07-12"))
                .andExpect(jsonPath("$.hackatonesDetails[2].hackatonEndDate").value("2022-12-21"));
    }

    @Test
    public void CP02_ReportParticipantListEmptyTest() throws Exception {
        // Realiza la solicitud GET
        mockMvc.perform(get("/api/reports/participante/lista/13")) // Asegúrate de que el ID 13 sea correcto
                .andExpect(status().isOk()) // Verifica que la respuesta sea 200 OK
                .andExpect(content().json("{\"actorType\":\"participante\"," +
                        "\"actorName\":\"Felipe Avello\"," +
                        "\"hackatonesDetails\":\"No ha participado en ningun Hackathon\"}"));
    }

}
