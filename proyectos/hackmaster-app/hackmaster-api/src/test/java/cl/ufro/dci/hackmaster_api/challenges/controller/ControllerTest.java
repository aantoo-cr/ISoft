package cl.ufro.dci.hackmaster_api.challenges.controller;

import cl.ufro.dci.hackmaster_api.challenges.Controller;
import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.challenges.dto.ChallengesDTO;
import cl.ufro.dci.hackmaster_api.challenges.service.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private Service service;

    @InjectMocks
    private Controller controller;

    @MockBean
    private Challenge challenge;

    private ChallengesDTO challengeDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        challengeDTO = new ChallengesDTO();
        challengeDTO.setNombre("Desafío 1");
        challengeDTO.setDescripcion("Descripción de prueba");
        challengeDTO.setRequisitos_Participantes("Requisito 1");
        challengeDTO.setFechaI(LocalDate.of(2024, 10, 24));
        challengeDTO.setFechaT(LocalDate.of(2024, 10, 24));
        challengeDTO.setCuposParticipantes(10);
    }

    @Test
    public void testInscribirChallenge() throws Exception {
        Challenge challenge = new Challenge();
        when(service.inscribirChallenge(challengeDTO)).thenReturn(challenge);

        mockMvc.perform(post("/api/challenges/inscribir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nombre\": \"Desafío 1\", \"descripcion\": \"Descripción de prueba\", " +
                                "\"requisitos_Participantes\": \"Requisito 1\", \"fechaI\": \"2024-10-23\", " +
                                "\"fechaT\": \"2024-10-24\", \"cuposParticipantes\": 10 }"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testListarChallenges() throws Exception {
        when(service.listarChallenges()).thenReturn(Collections.singletonList(new Challenge()));

        mockMvc.perform(get("/api/challenges/listar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testViewChallenge() throws Exception {
        Challenge challenge = new Challenge();
        when(service.viewChallenge(1L)).thenReturn(Optional.of(challenge));

        mockMvc.perform(get("/api/challenges/challenge/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPublicarChallenge() throws Exception {
        Challenge challenge = new Challenge();
        when(service.publicarChallenge(1L)).thenReturn(Optional.of(challenge));

        mockMvc.perform(put("/api/challenges/1/publicar"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEliminarChallenge() throws Exception {
        when(service.eliminarChallenge(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/challenges/1"))
                .andExpect(status().isNoContent());
    }
}