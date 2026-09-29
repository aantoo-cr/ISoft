package cl.ufro.dci.naive_pay_api.registration.controller;

import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import cl.ufro.dci.naive_pay_api.registration.service.BankAccountService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para gestionar las cuentas bancarias.
 * Este controlador proporciona endpoints para manejar las operaciones relacionadas con las cuentas bancarias.
 * @author Gerson-art
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    /**
     * Constructor para inyectar el servicio de cuentas bancarias.
     *
     * @param bankAccountService el servicio de cuentas bancarias.
     */
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Crea una nueva cuenta bancaria para un titular de cuenta.
     *
     * @param request un mapa que contiene el RUT del titular de cuenta.
     * @return ResponseEntity con un mensaje indicando si la cuenta fue creada con éxito o si ocurrió un error.
     */
    @PostMapping("/bank-account/create")
    public ResponseEntity<Map<String, String>> createBankAccount(@RequestBody Map<String, String> request) {
        String rutAccountHolder = request.get("rutAccountHolder");
        Map<String, String> responseMap = new HashMap<>();

        try {
            bankAccountService.createBankAccount(rutAccountHolder);
            responseMap.put("message", "Cuenta bancaria creada con éxito");
            return ResponseEntity.ok(responseMap);

        } catch (DataIntegrityViolationException e) {
            // Captura excepción de restricción de integridad (e.g., el RUT ya existe)
            responseMap.put("message", "Ya existe una cuenta bancaria para el titular con RUT: " + rutAccountHolder);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseMap);

        } catch (ResponseStatusException e) {
            // Captura excepciones personalizadas con estado específico
            responseMap.put("message", e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(responseMap);

        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            responseMap.put("message", "Error al crear la cuenta bancaria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }

    /**
     * Obtiene una cuenta bancaria por su número de cuenta.
     *
     * @param rut el rut del número de cuenta de la cuenta bancaria.
     * @return ResponseEntity con la cuenta bancaria correspondiente al número de cuenta especificado.
     */
    @GetMapping("/bank-account/getByRut/{rut}")
    public ResponseEntity<?>getBankAccountByRut(@PathVariable String rut){
        Optional<BankAccount> bankAccountOptional = bankAccountService.getBankAccountByRut(rut);
        if (bankAccountOptional.isPresent()) {
            return ResponseEntity.ok(bankAccountOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account not found with RUT: " + rut);
        }
    }
}