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
public class CU07_VisualizarReporteChallengeEquipo {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportsService reportsService; // Puedes usar el servicio real

    @Test
    public void CP01_ReportHackathonTeamDetailsTest() throws Exception {
        // Simula una solicitud GET a la URL del reporte de detalles del hackathon del equipo
        mockMvc.perform(get("/api/reports/participante/hackaton/4/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("participante"))
                .andExpect(jsonPath("$.actorName").value("Felipe Monsalves"))
                .andExpect(jsonPath("$.teamName").value("anyTeam"))
                .andExpect(jsonPath("$.averageHackathonGrade").value(7.25))
                .andExpect(jsonPath("$.hackathonName").value("HackMIT"))
                .andExpect(jsonPath("$.integrantesGrupo").isArray())
                .andExpect(jsonPath("$.integrantesGrupo[0]").value("Felipe Monsalves"))
                .andExpect(jsonPath("$.integrantesGrupo[1]").value("Benjamin Cruzado"))
                .andExpect(jsonPath("$.notaGrupo").value(8.0))
                .andExpect(jsonPath("$.ranking").isArray())
                .andExpect(jsonPath("$.ranking[0]").value("Team: anyTeam, Position: #1"))
                .andExpect(jsonPath("$.ranking[1]").value("Team: teamEstrella, Position: #2"));
    }

    @Test
    public void CP02_ReportHackathonTeamDetailsIncompleteDataTest() throws Exception {
        // Realiza la solicitud GET para un equipo sin notas ingresadas
        mockMvc.perform(get("/api/reports/participante/hackaton/4/3"))
                .andExpect(status().isOk()) // Verifica que la respuesta sea 200 OK
                .andExpect(content().json("{\"teamName\":\"teamLinux\",\"actorType\":\"participante\",\"averageHackathonGrade\":\"no hay notas ingresadas\",\"hackathonName\":\"MOSIP CREATE\",\"integrantesGrupo\":[\"Felipe Monsalves\",\"Luis Contreras\"],\"actorName\":\"Felipe Monsalves\",\"notaGrupo\":\"no hay notas ingresadas\",\"ranking\":\"no hay notas ingresadas para establecer ranking\"}"));
    }
}
