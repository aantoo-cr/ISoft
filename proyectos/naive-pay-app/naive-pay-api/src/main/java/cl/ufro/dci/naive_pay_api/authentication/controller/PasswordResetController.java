package cl.ufro.dci.naive_pay_api.authentication.controller;

import cl.ufro.dci.naive_pay_api.authentication.dto.PasswordResetDto;
import cl.ufro.dci.naive_pay_api.authentication.exceptions.AppException;
import cl.ufro.dci.naive_pay_api.authentication.service.ResetPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador que maneja las solicitudes de restablecimiento de contraseña y la generación/decodificación de tokens JWT.
 * Este controlador recibe solicitudes para restablecer contraseñas, valida el RUT del usuario y verifica su existencia en la base de datos antes de proceder con el cambio de contraseña.
 *
 * <p>Actualizaciones:
 * 1. Validación del RUT del usuario (formato sin puntos y con guión).
 * 2. Verificación de la existencia del RUT en la base de datos antes de actualizar la contraseña.
 * 3. Eliminación de la contraseña anterior una vez que se actualiza.</p>
 *
 * @author Marcelo y Jesus
 */
@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    private final ResetPasswordService passwordService;

    /**
     * Constructor para inicializar el controlador con el servicio de restablecimiento de contraseña.
     *
     * @param passwordService Servicio para manejar la lógica de cambio de contraseña
     */
    public PasswordResetController(ResetPasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /**
     * Método que maneja las solicitudes POST para restablecer la contraseña del usuario.
     *
     * @param passwordResetDto Objeto que contiene la nueva contraseña y el token de restablecimiento
     * @return Una respuesta HTTP con el estado de la operación. Si la contraseña se cambia correctamente, devuelve un mensaje de éxito.
     *         Si ocurre un error (por ejemplo, si el correo no está registrado), devuelve un mensaje de error con el código de estado correspondiente.
     */
    @PostMapping("/password-reset")
    public ResponseEntity<?> changePassword(@RequestBody PasswordResetDto passwordResetDto) {
        try {
            // Llamar al servicio para cambiar la contraseña
            passwordService.changePassword(passwordResetDto);
            return ResponseEntity.ok("Contraseña cambiada con éxito.");
        } catch (AppException e) {
            // Si hay un error (por ejemplo, si el correo no está registrado)
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}