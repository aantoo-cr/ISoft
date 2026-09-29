package cl.ufro.dci.hackmaster_api.registration.controller;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.service.OrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {

  @InjectMocks
  private OrganizationController organizationController;

  @MockBean
  private OrganizationService organizationService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private Organization organizationValid;
  private Organization organizationInvalid;
  private Map<String, String> validLoginRequest;
  private Map<String, String> invalidLoginRequest;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    organizationValid = new Organization();
    organizationValid.setEmail("existente@example.com");
    organizationValid.setPassword("ContraseñaExistente");
    organizationValid.setSocialNameOrganization("Nombre de Organización Existente");
    organizationValid.setRutOrganization("12345678-9");
    organizationValid.setRutRepresentative("98765432-1");
    organizationValid.setRepresentativeName("Nombre válido");

    organizationInvalid = new Organization();
    organizationInvalid.setEmail("invalid@example.com");
    organizationInvalid.setPassword("123");
    organizationInvalid.setSocialNameOrganization("");
    organizationInvalid.setRutOrganization("invalidRut");
    organizationInvalid.setRutRepresentative("98765432-1");
    organizationInvalid.setRepresentativeName("Nombre inválido");

    validLoginRequest = new HashMap<>();
    validLoginRequest.put("email", "existente@example.com");
    validLoginRequest.put("password", "ContraseñaExistente");

    invalidLoginRequest = new HashMap<>();
    invalidLoginRequest.put("email", "no-registrado@example.com");
    invalidLoginRequest.put("password", "ContraseñaValida");
  }

  /*
  @Test
  @DisplayName(" [DA-01/CP-04] No se envía solicitud de registro por campos en mal formato - POST /api/organizations/register")
  public void invalidRequest() throws Exception {
    String organizationJson = objectMapper.writeValueAsString(organizationInvalid);

    when(organizationService.register(any(Organization.class)))
        .thenThrow(new IllegalArgumentException("Formato inválido en los campos"));

    mockMvc.perform(post("/api/organizations/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(organizationJson))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.socialNameOrganization").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.rutOrganization").exists());
  }*/

  @Test
  @DisplayName(" [DA-03/CP-02] Organización inicia sesión con credenciales correctas - POST /api/organizations/login")
  public void loginValidCredentials() throws Exception {
    when(organizationService.login("existente@example.com", "ContraseñaExistente"))
        .thenReturn(organizationValid);

    String loginRequestJson = objectMapper.writeValueAsString(validLoginRequest);

    mockMvc.perform(post("/api/organizations/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginRequestJson))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.socialNameOrganization").value("Nombre de Organización Existente"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("existente@example.com"));
  }

  @Test
  @DisplayName(" [DA-03/CP-04] Organización ingresa credenciales no registradas y se indica error - POST /api/organizations/login")
  public void noValidCredentialsLogin() throws Exception {
    when(organizationService.login("no-registrado@example.com", "ContraseñaValida"))
        .thenThrow(new IllegalArgumentException("El Email no existe"));

    String loginRequestJson = objectMapper.writeValueAsString(invalidLoginRequest);

    mockMvc.perform(post("/api/organizations/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginRequestJson))
        .andExpect(status().isUnauthorized())
        .andExpect(MockMvcResultMatchers.content().string("El Email no existe"));
  }

  @Test
  @DisplayName(" [DA-03/CP-08] Organización ingresa correo no registrado - POST /api/organizations/login")
  public void loginNonExistentEmail() throws Exception {
    when(organizationService.login("no-registrado@example.com", "ContraseñaIncorrecta"))
        .thenThrow(new IllegalArgumentException("El Email no existe"));

    Map<String, String> request = new HashMap<>();
    request.put("email", "no-registrado@example.com");
    request.put("password", "ContraseñaIncorrecta");

    String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(post("/api/organizations/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andExpect(status().isUnauthorized())
        .andExpect(MockMvcResultMatchers.content().string("El Email no existe"));
  }

  @Test
  @DisplayName(" [DA-03/CP-08] Organización ingresa contraseña incorrecta - POST /api/organizations/login")
  public void loginIncorrectPassword() throws Exception {
    when(organizationService.login("valido@example.com", "ContraseñaIncorrecta"))
        .thenThrow(new IllegalArgumentException("Contraseña incorrecta"));

    Map<String, String> incorrectPasswordRequest = new HashMap<>();
    incorrectPasswordRequest.put("email", "valido@example.com");
    incorrectPasswordRequest.put("password", "ContraseñaIncorrecta");

    String requestJson = objectMapper.writeValueAsString(incorrectPasswordRequest);

    mockMvc.perform(post("/api/organizations/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andExpect(status().isUnauthorized())
        .andExpect(MockMvcResultMatchers.content().string("Contraseña incorrecta"));
  }
}
