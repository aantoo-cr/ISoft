package cl.ufro.dci.naive_pay_api.devices.controller;

import cl.ufro.dci.naive_pay_api.devices.service.DesvinculacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador para gestionar la desvinculación de dispositivos.
 */
@RestController
@RequestMapping("/desvinculacion")
@CrossOrigin(origins = "http://localhost:4200")
public class DesvinculacionController {

    @Autowired
    private DesvinculacionService desvinculacionService;

    /**
     * Envía un código de desvinculación al correo electrónico proporcionado
     * y devuelve detalles del dispositivo vinculado.
     *
     * @param email El correo electrónico del usuario.
     * @return ResponseEntity con información del dispositivo o un mensaje de error.
     */
    @GetMapping("/enviar-codigo")
    public ResponseEntity<?> enviarCodigo(@RequestParam String email) {
        Map<String, Object> response = desvinculacionService.enviarCodigoDesvinculacion(email);

        if (response != null) {
            return ResponseEntity.ok(response); // Devuelve detalles del dispositivo
        } else {
            return ResponseEntity.status(404).body("El correo electrónico no está registrado o no tiene dispositivos vinculados.");
        }
    }

    /**
     * Desvincula el dispositivo asociado al correo electrónico y código proporcionados.
     *
     * @param email El correo electrónico del usuario.
     * @param codigo El código de desvinculación.
     * @return ResponseEntity con un mensaje de éxito o error.
     */
    @GetMapping("/desvincular-dispositivo")
    public ResponseEntity<String> desvincularDispositivo(@RequestParam String email, @RequestParam Integer codigo) {
        boolean dispositivoDesvinculado = desvinculacionService.desvincularDispositivo(email, codigo);

        if (dispositivoDesvinculado) {
            return ResponseEntity.ok("Dispositivo desvinculado con éxito.");
        } else {
            return ResponseEntity.status(400).body("No se pudo desvincular el dispositivo. Verifica el código ingresado.");
        }
    }
}
