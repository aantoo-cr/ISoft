package cl.ufro.dci.hackmaster_api.registration.controller;

import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los
 * participantes.
 * Proporciona endpoints para registro, actualización, inicio de sesión y
 * búsqueda de participantes.
 */

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

  private final ParticipantService participantService;

  /**
   * Actualiza los datos de un participante existente.
   *
   * @param participantDto los datos del participante a actualizar.
   * @return una respuesta HTTP con los detalles del participante actualizado
   *         o un mensaje de error si no se encuentra el participante.
   */
  @PutMapping("/update")
  public ResponseEntity<?> update(@Valid @RequestBody Participant participantDto) {
    Optional<Participant> p = participantService.update(participantDto);
    if (p.isPresent()) {
      return ResponseEntity
          .status(HttpStatus.CREATED)
          .body(p.get());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("[ParticipantController] No se pudo actualizar el participante");
  }

  /**
   * Busca un participante por su correo electrónico.
   *
   * @param email el correo electrónico del participante a buscar.
   * @return una respuesta HTTP con los detalles del participante encontrado
   *         o un mensaje de error si no se encuentra el participante.
   */
  @GetMapping("/search/{email}")
  public ResponseEntity<?> searchByEmail(@PathVariable String email) {
    try {
      Optional<Participant> p = participantService.existsByEmail(email);
      if (p.isPresent()) {
        return ResponseEntity.ok(p.get());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("[ParticipantController] No se encontró el participante");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("[ParticipantController] Error al buscar el participante: " + e.getMessage());
    }
  }
}
