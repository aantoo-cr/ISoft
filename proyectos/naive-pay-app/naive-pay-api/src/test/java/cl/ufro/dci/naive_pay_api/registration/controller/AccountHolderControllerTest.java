package cl.ufro.dci.naive_pay_api.registration.controller;

import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.dto.AccountHolderDTO;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import cl.ufro.dci.naive_pay_api.registration.service.AccountHolderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountHolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountHolderRepository accountHolderRepository; // Inject your repository

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @MockBean
    private AccountHolderService accountHolderService;

    @BeforeEach
    public void setup() {
        AccountHolder usuario = new AccountHolder("17567800", "0", "Damian", "Ramirez", "emailservice976@gmail.com", "56978563412", "789.789.789", "las Camelias 1995", "Cajero", LocalDate.parse("1990-06-12"));
        accountHolderRepository.save(usuario);
    }

    @AfterEach
    public void tearDown() {
        // Clear existing users in the repository
        accountHolderRepository.deleteAll();
    }

    @DisplayName(" CP-01: El usuario ingresa datos validos  -POST /account-holder/create")
    @Test
    public void  testRegisterAccountHolder_CorrectCredentials() throws Exception {
        AccountHolder usuario = new AccountHolder("17567800", "0", "Damian", "Ramirez", "emailservice976@gmail.com", "56978563412", "789.789.789", "las Camelias 1995", "Cajero", LocalDate.parse("1990-06-12"));

        mockMvc.perform(post("/account-holder/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cuenta creada con éxito"))
                .andExpect(jsonPath("$.sessionId").isNotEmpty());

    }

    @DisplayName("CP-02: Error al crear el usuario - POST /account-holder/create")
    @Test
    public void testRegisterAccountHolder_Error() throws Exception {
        /*

        // Simular una excepción lanzada por accountHolderService
        doThrow(new RuntimeException("Error en el servicio")).when(accountHolderService).createAccountHolder(usuarioConError);

        mockMvc.perform(post("/account-holder/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioConError)))
                .andExpect(status().isOk())  // El código de estado debe ser 500
                .andExpect(jsonPath("$.message").value("Ha ocurrido un error en el momento de crear el usuario."));  // Verificar el mensaje de error

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
