package cl.ufro.dci.hackmaster_api.rec_management.controller;

import cl.ufro.dci.hackmaster_api.rec_management.domain.Prize;
import cl.ufro.dci.hackmaster_api.rec_management.service.PrizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PrizeControllerTest {

    @Mock
    private PrizeService prizeService;

    @InjectMocks
    private PrizeController prizeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(prizeController).build();
    }

    // Test para CU-01-CP-01: Validar que se recuperan todos los premios
    @Test
    @DisplayName("Validar que se recuperan todos los premios")
    public void testAllPrizesFound() throws Exception {
        Prize firstPrize = new Prize();
        firstPrize.setName("Premio Uno");
        firstPrize.setPosition(1);
        firstPrize.setDescription("Descripción del Premio Uno");

        Prize secondPrize = new Prize();
        secondPrize.setName("Premio Dos");
        secondPrize.setPosition(2);
        secondPrize.setDescription("Descripción del Premio Dos");

        List<Prize> prizes = new ArrayList<>();
        prizes.add(firstPrize);
        prizes.add(secondPrize);

        doReturn(prizes).when(prizeService).deployAllPrizes(any(Long.class));

        mockMvc.perform(get("/challenges/1/prizes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].name", is("Premio Uno")))
                .andExpect(jsonPath("$[0].description", is("Descripción del Premio Uno")))
                .andExpect(jsonPath("$[1].name", is("Premio Dos")))
                .andExpect(jsonPath("$[1].description", is("Descripción del Premio Dos")));
    }

    // Test para CU-01-CP-02: Verificar que se devuelve una lista vacía cuando no hay premios
    @Test
    @DisplayName("Verificar que se devuelve una lista vacía")
    public void testNoPrizesFound() throws Exception {
        doReturn(new ArrayList<>()).when(prizeService).deployAllPrizes(any(Long.class));

        mockMvc.perform(get("/challenges/1/prizes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    // Test para CU-03-CP-01: Verificar que un premio se crea correctamente
    @Test
    @DisplayName("Verificar que un premio se crea correctamente")
    public void testAssignPrize() throws Exception {
        Prize newPrize = new Prize();
        newPrize.setName("Premio Nuevo");
        newPrize.setDescription("Descripción del premio nuevo");
        newPrize.setPosition(1);

        doReturn(newPrize).when(prizeService).assignPrize(any(Long.class), any(Prize.class));

        mockMvc.perform(post("/challenges/1/prizes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Premio Nuevo\", \"description\":\"Descripción del premio nuevo\", \"position\":1}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name", is("Premio Nuevo")))
                .andExpect(jsonPath("$.description", is("Descripción del premio nuevo")))
                .andExpect(jsonPath("$.position", is(1)));
    }

    // Test para CU-03-CP-02: Crear premio sin nombre y verificar error
    @Test
    @DisplayName("Crear premio sin nombre y verificar error")
    public void testAssignPrizeWithoutName() throws Exception {
        mockMvc.perform(post("/challenges/1/prizes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"position\":1, \"description\":\"Descripción sin nombre\"}"))
                .andExpect(status().isBadRequest());
    }
}







