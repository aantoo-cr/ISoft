package cl.ufro.dci.hackmaster_api.registration.controller;

import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParticipantControllerTest {

  @MockBean
  private ParticipantService participantService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private Participant newParticipant;
  private Participant existingParticipant;
  private Participant invalidParticipant;
  private Participant updatedParticipant;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    newParticipant = new Participant();
    newParticipant.setUsername("UsuarioPrueba");
    newParticipant.setPassword("ContraseñaPrueba");
    newParticipant.setEmail("prueba@gmail.com");
    newParticipant.setRut("21476732-5");
    newParticipant.setName("Javier");
    newParticipant.setLastname("Alcalde");
    newParticipant.setCity("Temuco");
    newParticipant.setCountry("Chile");
    newParticipant.setPhoneNumber("987654321");

    existingParticipant = new Participant();
    existingParticipant.setUsername("UsuarioPrueba");
    existingParticipant.setPassword("ContraseñaPrueba");
    existingParticipant.setEmail("prueba@gmail.com");
    existingParticipant.setRut("21476732-5");
    existingParticipant.setName("Javier");
    existingParticipant.setLastname("Alcalde");
    existingParticipant.setCity("Temuco");
    existingParticipant.setCountry("Chile");
    existingParticipant.setPhoneNumber("987654321");

    invalidParticipant = new Participant();
    invalidParticipant.setUsername("UsuarioPrueba");
    invalidParticipant.setPassword("ContraseñaPrueba");
    invalidParticipant.setEmail("correo-invalido");
    invalidParticipant.setRut("11111111-1");
    invalidParticipant.setName("Javier123");
    invalidParticipant.setLastname("Alcalde");
    invalidParticipant.setCity("Temuco");
    invalidParticipant.setCountry("Chile");
    invalidParticipant.setPhoneNumber("123");

    updatedParticipant = new Participant();
    updatedParticipant.setUsername("UsuarioActualizado");
    updatedParticipant.setPassword("ContraseñaActualizada");
    updatedParticipant.setEmail("existente@example.com");
    updatedParticipant.setRut("27847694-4");
    updatedParticipant.setName("Jesus");
    updatedParticipant.setLastname("Tapia");
    updatedParticipant.setCity("Temuco");
    updatedParticipant.setCountry("Chile");
    updatedParticipant.setPhoneNumber("987654321");

  }

  @Test
  @DisplayName("[DA-01/CP-01] Participante ingresa datos válidos y el sistema crea la cuenta - POST /api/participants/register")
  public void validInputCreatesParticipant() throws Exception {

    doReturn(newParticipant).when(participantService).register(any(Participant.class));

    String participantJson = objectMapper.writeValueAsString(newParticipant);

    mockMvc.perform(post("/api/participants/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(participantJson))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("UsuarioPrueba"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("prueba@gmail.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.rut").value("21476732-5"));
  }

  @Test
  @DisplayName(" [DA-01/CP-03] Participante ingresa datos inválidos y el sistema rechaza la cuenta - POST /api/participants/register")
  public void invalidInputsRejectRegistration() throws Exception {

    String participantJson = objectMapper.writeValueAsString(invalidParticipant);

    mockMvc.perform(post("/api/participants/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(participantJson))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.rut").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
  }

  /*
  @Test
  @DisplayName(" [DA-01/CP-05] Usuario ingresa un correo ya registrado y el sistema rechaza la creación de la cuenta - POST /api/participants/register")
  public void emailRegisteredRejectRegistration() throws Exception {


    when(participantService.existsByEmail(anyString())).thenReturn(Optional.of(existingParticipant));
    when(participantService.register(any(Participant.class)))
        .thenThrow(new IllegalArgumentException("El correo ya existe"));

    Participant participant = new Participant();
    participant.setUsername("NuevoUsuario");
    participant.setPassword("NuevaContraseña");
    participant.setEmail("existente@example.com");
    participant.setRut("21476732-5");
    participant.setName("Jesus");
    participant.setLastname("Tapia");
    participant.setCity("Temuco");
    participant.setCountry("Chile");
    participant.setPhoneNumber("123456789");

    String participantJson = objectMapper.writeValueAsString(participant);

    mockMvc.perform(post("/api/participants/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(participantJson))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string("El correo ya existe"));
  }*/

  @Test
  @DisplayName(" [DA-03/CP-01] Participante inicia sesión con credenciales correctas - POST /api/participants/login")
  public void loginValidCredentials() throws Exception {
    Participant mockParticipant = new Participant();
    mockParticipant.setEmail("existente@example.com");
    mockParticipant.setUsername("UsuarioExistente");
    mockParticipant.setPassword("ContraseñaExistente");

    when(participantService.login("existente@example.com", "ContraseñaExistente"))
        .thenReturn(mockParticipant);

    Map<String, String> loginRequest = new HashMap<>();
    loginRequest.put("email", "existente@example.com");
    loginRequest.put("password", "ContraseñaExistente");

    String loginRequestJson = objectMapper.writeValueAsString(loginRequest);

    mockMvc.perform(post("/api/participants/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginRequestJson))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("UsuarioExistente"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("existente@example.com"));
  }

  @Test
  @DisplayName(" [DA-03/CP-03] Participante ingresa credenciales no registradas y se indica error - POST /api/participants/login")
  public void noValidCredentialsLogin() throws Exception {
    when(participantService.login("no-registrado@example.com", "ContraseñaValida"))
        .thenThrow(new IllegalArgumentException("El Email no existe"));

    Map<String, String> loginRequest = new HashMap<>();
    loginRequest.put("email", "no-registrado@example.com");
    loginRequest.put("password", "ContraseñaValida");

    String loginRequestJson = objectMapper.writeValueAsString(loginRequest);

    mockMvc.perform(post("/api/participants/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginRequestJson))
        .andExpect(status().isUnauthorized())
        .andExpect(MockMvcResultMatchers.content().string("El Email no existe"));
  }

  @Test
  @DisplayName(" [DA-03/CP-05] Participante ingresa correo no registrado - POST /api/participants/login")
  public void loginNonExistentEmail() throws Exception {
    when(participantService.login("no-registrado@example.com", "ContraseñaIncorrecta"))
        .thenThrow(new IllegalArgumentException("El Email no existe"));

    Map<String, String> request = new HashMap<>();
    request.put("email", "no-registrado@example.com");
    request.put("password", "ContraseñaIncorrecta");

    String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(post("/api/participants/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andExpect(status().isUnauthorized())
        .andExpect(MockMvcResultMatchers.content().string("El Email no existe"));
  }

  @Test
  @DisplayName(" [DA-03/CP-07] Participante ingresa contraseña incorrecta - POST /api/participants/login")
  public void loginIncorrectPassword() throws Exception {
    Participant mockParticipant = new Participant();
    mockParticipant.setEmail("valido@example.com");
    mockParticipant.setPassword("ContraseñaCorrecta");

    when(participantService.login("valido@example.com", "ContraseñaIncorrecta"))
        .thenThrow(new IllegalArgumentException("Contraseña incorrecta"));

    Map<String, String> request = new HashMap<>();
    request.put("email", "valido@example.com");
    request.put("password", "ContraseñaIncorrecta");

    String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(post("/api/participants/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andExpect(status().isUnauthorized())
        .andExpect(MockMvcResultMatchers.content().string("Contraseña incorrecta"));
  }


  @Test
  @DisplayName("[DA-05/CP-01] Participante actualiza sus datos correctamente - PUT /api/participants/update")
  public void testUpdateParticipantSuccess() throws Exception {
    when(participantService.update(any(Participant.class))).thenReturn(Optional.of(updatedParticipant));

    String updatedParticipantJson = objectMapper.writeValueAsString(updatedParticipant);

    mockMvc.perform(put("/api/participants/update")
    .contentType(MediaType.APPLICATION_JSON)
    .content(updatedParticipantJson))
    .andExpect(status().isCreated())
    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("UsuarioActualizado"))
    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jesus"));

  }

  @Test
  @DisplayName("[DA-05/CP-02] Los datos cumplen con el formato, pero la contraeña no coincide - PUT /api/participants/update")
  public void testUpdateParticipantFail() throws Exception {
    when(participantService.update(any(Participant.class))).thenReturn(Optional.empty());

    updatedParticipant.setPassword("ContraseñaIncorrecta");
    String updatedParticipantJson = objectMapper.writeValueAsString(updatedParticipant);

    mockMvc.perform(put("/api/participants/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedParticipantJson))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("[DA-05/CP-03] Los datos cumplen con el formato, pero la contraeña no coincide - PUT /api/participants/update")
  public void testUpdateParticipantInvailFormat() throws Exception {
    updatedParticipant.setEmail("correo-invalido");
    String updatedParticipantJson = objectMapper.writeValueAsString(updatedParticipant);

    mockMvc.perform(put("/api/participants/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedParticipantJson))
        .andExpect(status().isBadRequest());
  }

}
