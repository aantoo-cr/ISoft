package cl.ufro.dci.naive_pay_api.payments.controller;

import cl.ufro.dci.naive_pay_api.payments.service.PublicKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/public-key")
public class PublicKeyController {

    @Autowired
    private PublicKeyService publicKeyService;
    @PostMapping("validate")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String accountNumber = loginData.get("accountNumber");
        String publicKey = loginData.get("publicKey");

        boolean isAuthenticated = publicKeyService.authenticatePublicKey(accountNumber, publicKey);

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid RUT or password");
        }
    }
}