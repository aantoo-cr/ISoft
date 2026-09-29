package cl.ufro.dci.naive_pay_api.registration.controller;

import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.service.AccountHolderService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.ufro.dci.naive_pay_api.registration.dto.AccountHolderDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para gestionar los titulares de cuenta.
 * Este controlador proporciona endpoints para manejar las operaciones CRUD
 * relacionadas con los titulares de cuenta en el sistema.
 *
 * @author Gerson, Camille
 */
@RestController
public class AccountHolderController {

    private final AccountHolderService accountHolderService;

    /**
     * Constructor del controlador. Inicializa el servicio de los titulares de cuenta.
     *
     * @param accountHolderService el servicio que gestiona la lógica de negocio de los titulares de cuenta.
     */
    public AccountHolderController(AccountHolderService accountHolderService) {
        this.accountHolderService = accountHolderService;
    }

    /**
     * Obtiene todos los titulares de cuenta registrados en el sistema.
     *
     * @return un iterable que contiene todos los titulares de cuenta registrados.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<AccountHolder> getAllAccountHolders() {
        return accountHolderService.getAllAccountHolders();
    }

    /**
     * Obtiene un titular de cuenta específico basado en su RUT.
     *
     * @param rut el RUT del titular de cuenta que se desea obtener.
     * @return el titular de cuenta correspondiente al RUT especificado.
     */
    @GetMapping("/{rut}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolder(@PathVariable String rut) {
        return accountHolderService.getAccountHolderByRut(rut);
    }

    /**
     * Crea un nuevo titular de cuenta en el sistema.
     * <p>
     * Este método también genera un `sessionId` único para la nueva cuenta y lo guarda en una cookie.
     *
     * @param accountHolderDTO los datos del titular de cuenta a crear.
     * @param response         objeto HttpServletResponse para agregar la cookie con el sessionId.
     * @return ResponseEntity que contiene un mensaje y el sessionId en caso de éxito o un mensaje de error en caso de falla.
     */
    @PostMapping("/account-holder/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Map<String, String>> createAccountHolder(@RequestBody AccountHolderDTO accountHolderDTO, HttpServletResponse response) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            System.out.println(accountHolderDTO.toString());

            // Lógica para crear el account holder (usuario)
            accountHolderService.createAccountHolder(accountHolderDTO);

            // Generar un sessionId único
            String sessionId = UUID.randomUUID().toString();  // Genera un sessionId aleatorio

            // Añadir el sessionId a la cookie
            Cookie sessionCookie = new Cookie("sessionId", sessionId);
            sessionCookie.setHttpOnly(true); // Para que no se pueda acceder mediante JavaScript
            sessionCookie.setPath("/"); // La cookie estará disponible en toda la aplicación
            sessionCookie.setMaxAge(60 * 60); // Establece un tiempo de vida de una hora (opcional)
            response.addCookie(sessionCookie);

            // Respuesta con éxito
            responseMap.put("message", "Cuenta creada con éxito");
            responseMap.put("sessionId", sessionId);  // Aunque se guarda en la cookie, lo puedes enviar como parte de la respuesta también

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("message", "Ha ocurrido un error en el momento de crear el usuario.");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    /**
     * Asocia una clave web a un titular de cuenta.
     *
     * @param rut el RUT del titular de cuenta al que se le va a asignar la clave web.
     * @param key la clave web que se asociará al titular de cuenta.
     * @return ResponseEntity con un mensaje indicando si la clave fue guardada con éxito o si ocurrió un error.
     */
    @PostMapping("/account-holder/setWebkey")
    @CrossOrigin(origins = "http://localhost:4200")  // Habilita las solicitudes desde Angular (CORS)
    public ResponseEntity<Map<String, String>> setWebKey(
            @RequestParam String rut,
            @RequestParam String salt,
            @RequestParam String hash
    ) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            // Llamar a tu servicio para almacenar la clave web con el salt y el hash
            accountHolderService.setWebKey(rut, salt, hash);  // Asegúrate de que el servicio pueda manejar esto.
            responseMap.put("message", "Clave web guardada con éxito");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("message", "Ha ocurrido un error al guardar la clave web.");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    @GetMapping("/account-holder/exists")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Boolean> existAccountHolder(@RequestParam("rut") String rut, @RequestParam("verificationDigit") String verificationDigit) {
        boolean existsAccountHolder = accountHolderService.existsAccountHolderByRut(rut, verificationDigit);
        return ResponseEntity.ok(existsAccountHolder);
    }



    /**
     * Actualiza la información de un titular de cuenta basado en su RUT.
     *
     * @param rut                  el RUT del titular de cuenta a actualizar.
     * @param updatedAccountHolder el objeto que contiene la información actualizada del titular de cuenta.
     */
    @PutMapping("/{rut}")
    @ResponseStatus(HttpStatus.OK)
    public void updateAccountHolder(@PathVariable String rut, @RequestBody AccountHolder updatedAccountHolder) {
        accountHolderService.updateAccountHolderByRut(rut, updatedAccountHolder);
    }

    /**
     * Elimina un titular de cuenta basado en su RUT.
     *
     * @param rut el RUT del titular de cuenta a eliminar.
     */
    @DeleteMapping("/{rut}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountHolder(@PathVariable String rut) {
        accountHolderService.deleteAccountHolderByRut(rut);
    }
}

