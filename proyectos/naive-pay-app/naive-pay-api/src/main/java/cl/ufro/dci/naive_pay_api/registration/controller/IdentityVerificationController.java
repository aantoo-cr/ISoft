package cl.ufro.dci.naive_pay_api.registration.controller;

import cl.ufro.dci.naive_pay_api.registration.service.IdentityVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para gestionar la verificación de identidad de los usuarios.
 * Este controlador proporciona endpoints para enviar un código de verificación y validar el PIN enviado.
 *
 * @author Gerson, Camille
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/verification")
public class IdentityVerificationController {

    private final IdentityVerificationService identityVerificationService;

    /**
     * Constructor del controlador para inicializar el servicio de verificación de identidad.
     *
     * @param identityVerificationService el servicio que maneja la generación y validación de códigos.
     */
    public IdentityVerificationController(IdentityVerificationService identityVerificationService) {
        this.identityVerificationService = identityVerificationService;
    }


    /**
     * Envía un código de verificación al correo electrónico proporcionado.
     *
     * @param request un mapa que contiene el correo electrónico para el que se generará el código de verificación.
     * @return ResponseEntity con un mensaje indicando si el código fue enviado con éxito o si hubo un error.
     */
    @PostMapping("/code-request")
    public ResponseEntity<Map<String, String>> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Verificar que el correo electrónico no esté vacío
        if (email == null || email.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "El correo electrónico no puede estar vacío.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            // Intentar generar el código de verificación
            this.identityVerificationService.generateVerificationCode(email);

            // Responder con un objeto JSON
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Código enviado exitosamente");
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            // Responder con un mensaje de error en formato JSON
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al enviar el código de verificación.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


    /**
     * Valida el PIN de verificación recibido.
     *
     * @param request un mapa que contiene el PIN a validar.
     * @return ResponseEntity con el resultado de la validación del código de verificación.
     */
    @PostMapping("/validate-code")
    public ResponseEntity<Void> validateCode(@RequestBody Map<String, String> request) {
        String pin = request.get("pin");

        // Verificar que el PIN no esté vacío
        if (pin == null || pin.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Código 400: Bad Request si el PIN está vacío
        }

        try {
            // Intentar validar el PIN
            boolean response = this.identityVerificationService.validatePin(pin);
            if (response) {
                return ResponseEntity.ok().build(); // Código 200: OK si el PIN es correcto
            } else {
                return ResponseEntity.status(500).build(); // Código 500: Internal Server Error si el PIN es incorrecto
            }
        } catch (Exception e) {
            // Retornar un error en caso de fallo
            return ResponseEntity.status(500).build(); // Código 500 en caso de error interno
        }
    }
}

