package cl.ufro.dci.naive_pay_api.devices.controller;

import cl.ufro.dci.naive_pay_api.devices.service.VinculacionService;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para gestionar la vinculación de dispositivos.
 * Este controlador maneja las solicitudes relacionadas con la vinculación de dispositivos,
 * como agregar dispositivos, enviar códigos de verificación, verificar y vincular los dispositivos.
 *
 */

@RestController
@RequestMapping("/vinculacion")
@CrossOrigin(origins = "http://localhost:4200")
public class VinculacionController {

    /**
     * Servicio que contiene la lógica para la vinculación de dispositivos.
     */
    @Autowired
    private VinculacionService vinculacionService;

    /**
     * Repositorio para manejar las operaciones relacionadas con los titulares de cuentas.
     */

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    /**
     * Agrega un dispositivo al correo electrónico proporcionado.
     *
     * @param email El correo electrónico del usuario.
     * @param deviceName El nombre del dispositivo.
     * @param deviceModel El modelo del dispositivo.
     * @return ResponseEntity con un mensaje de éxito o error.
     */

    @GetMapping("/add-device")
    public ResponseEntity<Map<String, Object>> agregarDispositivo(
            @RequestParam String email,
            @RequestParam String deviceName,
            @RequestParam String deviceModel) {
        boolean resultado = vinculacionService.agregarDispositivo(email, deviceName, deviceModel);

        Map<String, Object> response = new HashMap<>();
        response.put("success", resultado);

        if (resultado) {
            return ResponseEntity.ok(response); // 200 OK
        } else {
            response.put("message", "No se pudo agregar el dispositivo");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
        }
    }


    /**
     * Envía un código de verificación al correo electrónico proporcionado.
     *
     * @param email El correo electrónico del usuario.
     * @return ResponseEntity con un mensaje de éxito o error.
     */

    @GetMapping("/enviar-codigo")
    public ResponseEntity<String> enviarCodigo(@RequestParam String email) {
        Map<String, Object> codigoEnviado = vinculacionService.enviarCodigoVinculacion(email);

        String message = (String) codigoEnviado.get("message");

        if (message.contains("Código de vinculación enviado")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(404).body(message);
        }
    }


    /**
     * Verifica el código de verificación proporcionado para completar la vinculación del dispositivo.
     *
     * @param email El correo electrónico del usuario.
     * @param codigo El código de verificación.
     * @return ResponseEntity con un mensaje de éxito o error.
     */

    @GetMapping("/verificar-codigo")
    public ResponseEntity<String> verificarCodigo(@RequestParam String email, @RequestParam Integer codigo) {
        boolean codigoValido = vinculacionService.verificarCodigo(email, codigo);

        if (codigoValido) {
            return ResponseEntity.ok("Código de verificación válido, vinculación de dispositivo exitosa");
        } else {
            return ResponseEntity.status(400).body("Código de verificación inválido o no hay un dispositivo pendiente de confirmación.");
        }
    }
}
