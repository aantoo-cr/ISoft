package cl.ufro.dci.hackmaster_api.registration.controller;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para gestionar las operaciones relacionadas con las
 * organizaciones.
 * Proporciona endpoints para registro, actualización, inicio de sesión y
 * búsqueda de organizaciones.
 */
@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

  private final OrganizationService organizationService;

  /**
   * Actualiza los datos de una organización existente.
   *
   * @param organizationDto los datos de la organización a actualizar.
   * @return una respuesta HTTP con los detalles de la organización actualizada
   *         o un mensaje de error si no se encontró la organización.
   */
  @PutMapping("/update")
  public ResponseEntity<?> update(@Valid @RequestBody Organization organizationDto) {
    Optional<Organization> u = organizationService.update(organizationDto);
    if (u.isPresent()) {
      return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(u.get());
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Busca una organización por su correo electrónico.
   *
   * @param email el correo electrónico de la organización a buscar.
   * @return una respuesta HTTP con los detalles de la organización encontrada
   *         o un mensaje de error si no se encuentra la organización.
   */
  @GetMapping("/search/{email}")
  public ResponseEntity<?> searchByEmail(@PathVariable String email) {
    Optional<Organization> o = organizationService.existsByEmail(email);
    if (o.isPresent()) {
      return ResponseEntity.ok(o.get());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("[OrganizationController] No se encontró el participante");
  }
}
