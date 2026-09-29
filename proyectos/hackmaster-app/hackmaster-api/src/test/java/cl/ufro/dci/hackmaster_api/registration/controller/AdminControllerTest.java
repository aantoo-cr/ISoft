package cl.ufro.dci.hackmaster_api.registration.controller;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    private Organization organization;
    private Organization organizationValid;
    private Organization organizationInvalid;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        organization = new Organization();
        organization.setRutOrganization("12345678-9");
        organization.setRutRepresentative("98765432-1");

        organizationValid = new Organization();
        organizationValid.setSocialNameOrganization("Nombre de Organización Válido");
        organizationValid.setRutOrganization("12345678-9");
        organizationValid.setRutRepresentative("98765432-1");
        organizationValid.setRepresentativeName("Nombre verdadero");
        organizationValid.setStatus(Organization.Status.PENDING);

        organizationInvalid = new Organization();
        organizationInvalid.setSocialNameOrganization("Organización Inválida");
        organizationInvalid.setRutOrganization("12345678-9");
        organizationInvalid.setRutRepresentative("12345678-9");
        organizationInvalid.setRepresentativeName("Nombre falso");
        organizationInvalid.setStatus(Organization.Status.PENDING);
    }

    @Test
    @DisplayName(" [DA-02/CP-01] Postulante cumple la información requerida y el administrador acepta la solicitud - POST /api/admin/approve/{id}")
    public void approveValidRequest() {
        when(adminService.approveRequest(1L)).thenReturn(organizationValid);

        ResponseEntity<?> response = adminController.approveRequest(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organizationValid, response.getBody());
        verify(adminService).approveRequest(1L);
    }

    @Test
    @DisplayName(" [DA-02/CP-02] Postulante tiene información insegura y el administrador rechaza la solicitud - POST /api/admin/reject/{id}")
    public void rejectInvalidRequest() {
        when(adminService.rejectRequest(anyLong()))
                .thenThrow(new IllegalArgumentException("Formato inválido en los campos"));

        ResponseEntity<?> response = adminController.rejectRequest(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Formato inválido en los campos", response.getBody());

        verify(adminService).rejectRequest(1L);
    }
}