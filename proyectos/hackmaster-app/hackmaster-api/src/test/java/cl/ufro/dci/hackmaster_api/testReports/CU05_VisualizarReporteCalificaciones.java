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
public class CU05_VisualizarReporteCalificaciones {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportsService reportsService; // Puedes usar el servicio real

    @Test
    public void CP01_ReportJudgeScoresTest() throws Exception {
        // Simula una solicitud GET a la URL del reporte de calificaciones del juez con un ID
        mockMvc.perform(get("/api/reports/juez/hackaton/9/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("juez"))
                .andExpect(jsonPath("$.actorName").value("Esteban Dido"))
                .andExpect(jsonPath("$.hackatonName").value("HackMIT"))
                .andExpect(jsonPath("$.ranking").isArray())
                .andExpect(jsonPath("$.ranking[0].teamName").value("anyTeam"))
                .andExpect(jsonPath("$.ranking[0].promedioNotaGrupo").value(8.0))
                .andExpect(jsonPath("$.ranking[1].teamName").value("teamEstrella"))
                .andExpect(jsonPath("$.ranking[1].promedioNotaGrupo").value(6.5))
                .andExpect(jsonPath("$.grades").isArray())
                .andExpect(jsonPath("$.grades[0].teamName").value("anyTeam"))
                .andExpect(jsonPath("$.grades[0].promedioNotaGrupo").value(8.0))
                .andExpect(jsonPath("$.grades[0].notaJuez").value(9))
                .andExpect(jsonPath("$.grades[1].teamName").value("teamEstrella"))
                .andExpect(jsonPath("$.grades[1].promedioNotaGrupo").value(6.5))
                .andExpect(jsonPath("$.grades[1].notaJuez").value(7));
    }

    @Test
    public void CP02_ReportJudgeScoresNoScoresTest() throws Exception {
        // Realiza la solicitud GET
        mockMvc.perform(get("/api/reports/juez/hackaton/8/3")) // Asegúrate de que el ID 8 y 3 sean los correctos
                .andExpect(status().isOk()) // Verifica que la respuesta sea 200 OK
                .andExpect(content().json("{\"actorType\":\"juez\"," +
                        "\"actorName\":\"Benjamin Hermosilla\"," +
                        "\"ranking\":\"no hay notas ingresadas para generar ranking\"," +
                        "\"hackatonName\":\"MOSIP CREATE\"," +
                        "\"error\":\"no hay notas ingresadas\"}"));
    }
}
