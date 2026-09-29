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
public class CU03_VisualizarReporteInscripcionEquipos {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportsService reportsService; // Puedes usar el servicio real

    @Test
    public void CP01_ReportHackathonDetailsTest() throws Exception {
        // Simula una solicitud GET a la URL del reporte de inscripción de equipos para un hackathon específico
        mockMvc.perform(get("/api/reports/organizador/hackaton/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actorType").value("organizador"))
                .andExpect(jsonPath("$.actorName").value("Falabella"))
                .andExpect(jsonPath("$.hackatonName").value("HackMIT"))
                .andExpect(jsonPath("$.numberOfRegistrations").value(4))
                .andExpect(jsonPath("$.totalRevenue").value(80000.0))
                .andExpect(jsonPath("$.numberOfTeams").value(2))
                .andExpect(jsonPath("$.membersCountPerTeam").isArray())
                .andExpect(jsonPath("$.membersCountPerTeam[0]").value(2))
                .andExpect(jsonPath("$.membersCountPerTeam[1]").value(2));
    }

    @Test
    public void CP02_ReportHackathonDetailsIncompleteDataTest() throws Exception {
        // Realiza la solicitud GET
        mockMvc.perform(get("/api/reports/organizador/hackaton/3/4")) // Cambia el ID según sea necesario
                .andExpect(status().isOk()) // Verifica que la respuesta sea 200 OK
                .andExpect(content().json("{\"numberOfRegistrations\":0," +
                        "\"actorType\":\"organizador\"," +
                        "\"membersCountPerTeam\":\"Ningun grupo se ha inscrito\"," +
                        "\"actorName\":\"Easy\"," +
                        "\"hackatonName\":\"OrangeHack\"," +
                        "\"totalRevenue\":0.0," +
                        "\"numberOfTeams\":0}"));
    }

}
