package cl.ufro.dci.hackmaster_api.registration.controller;

import cl.ufro.dci.hackmaster_api.registration.dto.EmailRequestDTO;
import cl.ufro.dci.hackmaster_api.registration.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para gestionar el restablecimiento de contraseñas.
 * Proporciona endpoints para solicitar un token de restablecimiento
 * y para restablecer la contraseña utilizando el token.
 */

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

  private final PasswordResetService passwordResetService;

  /**
   * Endpoint para solicitar un token de restablecimiento de contraseña.
   *
   * @param requestDTO objeto que contiene el correo electrónico del usuario que solicita el token.
   * @return una respuesta HTTP con un mensaje indicando que el token ha sido enviado.
   */
  @PostMapping("/reset-request")
  public ResponseEntity<Map<String, String>> sendPasswordResetToken(@Valid @RequestBody EmailRequestDTO requestDTO) {
    passwordResetService.sendPasswordResetToken(requestDTO.getEmail());
    Map<String, String> response = new HashMap<>();
    response.put("message", "Token enviado");
    return ResponseEntity.ok(response);
  }


  /**
   * Endpoint para restablecer la contraseña del usuario.
   *
   * @param requestDTO objeto que contiene el correo electrónico, el token de restablecimiento y la nueva contraseña.
   * @return una respuesta HTTP con un mensaje indicando que la contraseña ha sido restablecida.
   */
  @PostMapping("/reset")
  public ResponseEntity<String> resetPassword(@Valid @RequestBody EmailRequestDTO requestDTO) {
    passwordResetService.resetPassword(requestDTO.getEmail(), requestDTO.getToken(), requestDTO.getNewPassword());
    return ResponseEntity.ok("[PasswordResetController] La contraseña ha sido restablecida");
  }
}
