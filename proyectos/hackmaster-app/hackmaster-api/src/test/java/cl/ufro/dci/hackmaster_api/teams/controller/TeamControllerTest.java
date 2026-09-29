package cl.ufro.dci.hackmaster_api.teams.controller;

import cl.ufro.dci.hackmaster_api.teams.dto.TeamPreviewDto;
import cl.ufro.dci.hackmaster_api.teams.dto.TeamRequestDto;
import cl.ufro.dci.hackmaster_api.teams.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    @DisplayName("CU01 - CP01 - CreateTeamSuccess")
    void createTeamSuccess() throws Exception {
        var teamRequest = new TeamRequestDto();
        teamRequest.setName("Equipo A");
        teamRequest.setLeaderId(1L);

        var jsonRequest = objectMapper.writeValueAsString(teamRequest);

        when(teamService.saveTeam(any(TeamRequestDto.class))).thenReturn(1L);

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());

        verify(teamService, times(1)).saveTeam(any(TeamRequestDto.class));
    }

    @Test
    @DisplayName("CU01 - CP02 - CreateTeamFailureNameEmpty")
    void createTeamFailureNameEmpty() throws Exception {
        var teamRequest = new TeamRequestDto();
        teamRequest.setName(""); // Nombre vacío
        teamRequest.setLeaderId(1L);

        var jsonRequest = objectMapper.writeValueAsString(teamRequest);

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    var responseContent = result.getResponse().getContentAsString();
                    var errors = objectMapper.readTree(responseContent).get("errors");

                    assertNotNull(errors);
                    assertEquals("Team name should not be blank.", errors.get("name").asText());
                });
    }

    @Test
    @DisplayName("CU01 - CP03 - TeamAlreadyExists")
    void teamAlreadyExists() throws Exception {
        var teamRequest = new TeamRequestDto();
        teamRequest.setName("Equipo A");
        teamRequest.setLeaderId(1L);

        var jsonRequest = objectMapper.writeValueAsString(teamRequest);

        doThrow(new EntityExistsException("Team with name 'Equipo A' already exists."))
                .when(teamService).saveTeam(any(TeamRequestDto.class));

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(result -> {
                    var responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains("Team with name 'Equipo A' already exists."));
                });

        verify(teamService, times(1)).saveTeam(any(TeamRequestDto.class));
    }

    @Test
    @DisplayName("CU02 - CP01 - GetAllTeamsSuccess")
    void getAllTeamsSuccess() throws Exception {
        var team1 = new TeamPreviewDto();
        team1.setId(1L);
        team1.setName("Equipo A");

        var team2 = new TeamPreviewDto();
        team2.setId(2L);
        team2.setName("Equipo B");

        var teams = List.of(team1, team2);

        when(teamService.findTeamPreviews()).thenReturn(teams);

        mockMvc.perform(get("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Equipo A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Equipo B"));

        verify(teamService, times(1)).findTeamPreviews();
    }

    @Test
    @DisplayName("CU02 - CP02 - GetAllTeamsNotFound")
    void getAllTeamsNotFound() throws Exception {
        when(teamService.findTeamPreviews()).thenThrow(new EntityNotFoundException("No teams found"));

        mockMvc.perform(get("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    var responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains("No teams found"));
                });

        verify(teamService, times(1)).findTeamPreviews();
    }
}
