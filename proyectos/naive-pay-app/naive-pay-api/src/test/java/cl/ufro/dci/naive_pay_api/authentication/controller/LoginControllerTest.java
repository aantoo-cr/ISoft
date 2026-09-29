package cl.ufro.dci.naive_pay_api.authentication.controller;

import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountHolderRepository accountHolderRepository; // Inject your repository

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Insert a user into the in-memory database
        //AccountHolder usuario = new AccountHolder("jason.m@gmail.com", "jason123");
        //accountHolderRepository.save(usuario);
    }

    @AfterEach
    public void tearDown() {
        // Clear existing users in the repository
        accountHolderRepository.deleteAll();
    }

    @DisplayName(" CP-01: Login con credenciales correctas  -POST /usuarios/login")
    @Test
    public void testLoginUser_CorrectCredentials() throws Exception {
        //AccountHolder usuario = new AccountHolder("jason.m@gmail.com", "jason123");

        /*mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Credenciales correctas, usuario aceptado"));

         */
    }

    @DisplayName(" CP-02: Login con contraseña incorrecta  -POST  /usuarios/login")
    @Test
    public void testLoginUser_WrongPassword() throws Exception {
        //AccountHolder usuario = new AccountHolder("jason.m@gmail.com", "jason123");

        /*mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Credenciales incorrectas. Contraseña incorrecta"));

         */
    }

    @DisplayName(" CP-03: Login con correo y contraseña incorrectos  -POST /usuarios/login")
    @Test
    public void testLoginUser_WrongCredentials() throws Exception {
        //AccountHolder usuario = new AccountHolder("jasssson.m@gmail.com", "jason123");

        /*mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario no registrado"));

         */
    }

    @DisplayName(" CP-04: Recuperar cuenta con correo registrado  -POST /usuarios/recover")
    @Test
    public void testRecoverUser_CorrectEmail() throws Exception {
        //AccountHolder usuario = new AccountHolder("jason.m@gmail.com", "jason123");

        /*mockMvc.perform(post("/usuarios/recover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Email encontrado, restableciendo contraseña"));

         */
    }

    @DisplayName(" CP-05: Recuperar cuenta con correo no registrado  -POST /usuarios/recover")
    @Test
    public void testRecoverUser_WrongEmail() throws Exception {
        //AccountHolder usuario = new AccountHolder("json213213@mgail.com", "jason123");

        /*mockMvc.perform(post("/usuarios/recover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Correo no registrado en el sistema"));

         */
    }
}
