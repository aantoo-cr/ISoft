package cl.ufro.dci.hackmaster_api.registration.controller;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para manejar las acciones administrativas relacionadas con las solicitudes de organizaciones.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  /**
   * Obtiene una lista de organizaciones pendientes de aprobación.
   *
   * @return una lista de organizaciones pendientes.
   */
  @GetMapping("/requests")
  public ResponseEntity<List<Organization>> getPendingRequests() {
    return ResponseEntity.ok(adminService.getPendingRequests());
  }

  /**
   * Aprueba una solicitud de organización específica.
   *
   * @param id ID de la organización a aprobar.
   * @return la organización aprobada o un mensaje de error.
   */
  @PostMapping("/approve/{id}")
  public ResponseEntity<?> approveRequest(@PathVariable Long id) {
    try {
      Organization approvedOrganization = adminService.approveRequest(id);
      return ResponseEntity.ok(approvedOrganization);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * Rechaza una solicitud de organización específica.
   *
   * @param id ID de la organización a rechazar.
   * @return la organización rechazada o un mensaje de error.
   */
  @PostMapping("/reject/{id}")
  public ResponseEntity<?> rejectRequest(@PathVariable Long id) {
    try {
      Organization rejectedOrganization = adminService.rejectRequest(id);
      return ResponseEntity.ok(rejectedOrganization);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
