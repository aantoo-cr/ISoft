package cl.ufro.dci.naive_pay_api.registration.controller;

import cl.ufro.dci.naive_pay_api.registration.domain.Credential;
import cl.ufro.dci.naive_pay_api.registration.dto.CredentialDTO;
import cl.ufro.dci.naive_pay_api.registration.dto.KeyPairResponseDTO;
import cl.ufro.dci.naive_pay_api.registration.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador para gestionar las credenciales de los usuarios.
 * Este controlador proporciona un endpoint para la creación de nuevas credenciales en el sistema.
 *
 * @author Gerson, Camille
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    /**
     * Crea una nueva credencial basada en la información proporcionada en el DTO.
     *
     * @param credentialDTO el objeto que contiene los datos necesarios para crear una credencial.
     * @return ResponseEntity con el objeto Credential recién creado, o un error si hubo un problema al crearla.
     */
    @PostMapping("/credential/create")
    public ResponseEntity<Credential> createCredential(@RequestBody CredentialDTO credentialDTO) {
        try {
            // Intentar guardar la credencial usando el servicio
            Credential newCredential = credentialService.saveCredential(credentialDTO);
            return ResponseEntity.ok(newCredential); // Retorna una respuesta exitosa con la credencial creada
        } catch (IllegalArgumentException e) {
            // Si hay un error, devuelve un badRequest
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Obtiene las credenciales de un usuario en base a su rut.
     *
     * @param rut el rut del usuario del cual se quieren obtener las credenciales.
     * @return ResponseEntity con el objeto Credential correspondiente al rut, o un error si no se encontró.
     */
    @GetMapping("/credential/getByRut/{rut}")
    public ResponseEntity<Map<String, Object>> getCredentials(@PathVariable String rut) {
        Optional<Credential> credentialOptional = credentialService.getCredentials(rut);

        if (credentialOptional.isPresent()) {
            Credential credential = credentialOptional.get();

            Map<String, Object> response = new HashMap<>();
            response.put("public", credential.getPublicKey());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
