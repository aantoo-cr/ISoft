/*package cl.ufro.dci.naive_pay_api.registration.service;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class IdentityVerificationServiceTest {
/*
    private IdentityVerificationService identityVerificationService;

    @BeforeEach
    void setUp() {
        identityVerificationService = new IdentityVerificationService();
    }

    @Test
    @DisplayName("CP-01: Verificación exitosa con PIN correcto")
    public void testValidateCorrectPin() {
        String systemPin = identityVerificationService.generateVerificationCode("camille.elgueta@gmail.com"); // Generar un PIN de 6 dígitos

        boolean result = identityVerificationService.validatePin(systemPin);
        assertTrue(result, "El PIN debe ser correcto.");

        result = identityVerificationService.validatePin(systemPin);
        assertTrue(result, "El PIN debe ser correcto.");
    }

    @Test
    @DisplayName("CP-02: Verificación fallida con PIN incorrecto")
    public void testValidateIncorrectPin() {
        String systemPin = identityVerificationService.generateVerificationCode("camille.elgueta@gmail.com");
        int incorrectPinAsInt = Integer.parseInt(systemPin) + 1;
        String incorrectPin = String.valueOf(incorrectPinAsInt);

        // Intentar validar el PIN incorrecto
        boolean result = identityVerificationService.validatePin(incorrectPin);
        assertFalse(result, "El PIN ingresado debe ser incorrecto.");

        // Ahora probar con el PIN correcto
        result = identityVerificationService.validatePin(systemPin);
        assertTrue(result, "El PIN debe ser correcto.");
    }

    @Test
    @DisplayName("CP-03: Manejo de reenvío de PIN y verificación fallida")
    public void testResendPinAndValidation() {
        String systemPin = identityVerificationService.generateVerificationCode("camille.elgueta@gmail.com"); // Generar un PIN de 6 dígitos
        identityVerificationService.setGeneratedPin(systemPin);

        // Crear un PIN incorrecto sumando 1 al PIN generado
        int incorrectPinAsInt = Integer.parseInt(systemPin) + 1;
        String incorrectPin = String.valueOf(incorrectPinAsInt);

        // Intentar validar el PIN incorrecto
        boolean result = identityVerificationService.validatePin(incorrectPin);
        assertFalse(result, "El PIN ingresado debe ser incorrecto.");

        // Solicitar reenvío de PIN
        systemPin = identityVerificationService.generateVerificationCode("camille.elgueta@gmail.com"); // Generar un nuevo PIN
        identityVerificationService.setGeneratedPin(systemPin);

        // Validar con el PIN correcto
        result = identityVerificationService.validatePin(systemPin);
        assertTrue(result, "El PIN debe ser correcto.");
    }


    @Test
    @DisplayName("CP-04: Manejo de múltiples intentos fallidos")
    public void testMultipleFailedAttempts() {
        String systemPin = "856789"; // Simular la generación de un PIN de 6 dígitos
        identityVerificationService.setGeneratedPin(systemPin);

        // Intentos fallidos
        assertFalse(identityVerificationService.validatePin("111111"), "El primer PIN ingresado debe ser incorrecto.");
        assertFalse(identityVerificationService.validatePin("222222"), "El segundo PIN ingresado debe ser incorrecto.");
        assertFalse(identityVerificationService.validatePin("333333"), "El tercer PIN ingresado debe ser incorrecto.");

        // Verificar si el servicio está bloqueado
        assertFalse(identityVerificationService.validatePin(systemPin), "El servicio debe estar bloqueado después de múltiples intentos fallidos.");
    }

}
*/