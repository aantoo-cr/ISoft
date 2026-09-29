package cl.ufro.dci.naive_pay_api.authentication.controller;

import cl.ufro.dci.naive_pay_api.authentication.dto.ApiResponse;
import cl.ufro.dci.naive_pay_api.authentication.service.RecoverPasswordService;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para la recuperación de contraseñas.
 * Este controlador gestiona las solicitudes para enviar correos electrónicos con enlaces
 * de restablecimiento de contraseña.
 *
 * @author Marcelo y Jesus
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/")
public class RecoverPasswordController {

    private final RecoverPasswordService recoverPasswordService;

    @Autowired
    public RecoverPasswordController(RecoverPasswordService recoverPasswordService) {
        this.recoverPasswordService = recoverPasswordService;
    }

    /**
     * Endpoint para solicitar un correo de restablecimiento de contraseña.
     *
     * @param request Map que contiene el correo electrónico en la clave "email".
     * @return ResponseEntity con el resultado del proceso.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> sendPasswordResetEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        return recoverPasswordService.handlePasswordResetRequest(email);
    }
}