/* package cl.ufro.dci.naive_pay_api.devices.service;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import cl.ufro.dci.naive_pay_api.devices.domain.Devices;
import cl.ufro.dci.naive_pay_api.devices.repository.DeviceRepository;
import cl.ufro.dci.naive_pay_api.devices.service.DeviceEmailService;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DesvinculacionServiceTest {

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceEmailService deviceEmailService;

    @InjectMocks
    private DesvinculacionService desvinculacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // CP-08: El usuario ingresa un correo inválido para la desvinculación
    @DisplayName("CP-08: El usuario ingresa un correo inválido")
    @Test
    void testEnviarCodigoDesvinculacion_EmailNotFound() {
        String email = "user@example.com";
        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = desvinculacionService.enviarCodigoDesvinculacion(email);
        assertFalse(result);
        verify(accountHolderRepository, times(1)).findByEmail(email);
        verifyNoInteractions(deviceEmailService);
    }

    // CP-09: El usuario ingresa un correo válido para la desvinculación
    @DisplayName("CP-09: El usuario ingresa un correo válido")
    @Test
    void testEnviarCodigoDesvinculacion_Success() throws MessagingException {
        String email = "valid_user@example.com";
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);
        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));

        Devices device = new Devices();
        device.setStatus("Vinculado");
        device.setAccountHolder(accountHolder);
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        boolean result = desvinculacionService.enviarCodigoDesvinculacion(email);
        assertTrue(result);
        verify(accountHolderRepository, times(1)).findByEmail(email);
        verify(deviceEmailService, times(1)).enviarCorreoDesvinculacion(eq(email), anyInt());
        verify(deviceRepository, times(1)).save(device);
    }

    // CP-10: Ingresar un código inválido para la desvinculación
    @DisplayName("CP-10: Ingresa un código inválido para la desvinculación")
    @Test
    void testVerificarCodigoDesvinculacion_InvalidCode() {
        String email = "valid_user@example.com";
        Integer storedCode = 123456; // Código válido en el sistema
        Integer invalidCode = 999999; // Código incorrecto ingresado por el usuario

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        Devices device = new Devices();
        device.setVerificationCode(storedCode);
        device.setAccountHolder(accountHolder);

        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        boolean result = desvinculacionService.verificarCodigoDesvinculacion(email, invalidCode);
        assertFalse(result);
        verify(accountHolderRepository, times(1)).findByEmail(email);
    }

    // CP-11: El usuario ingresa más de 2 veces un código inválido y al tercer intento ingresa correctamente el código para su desvinculación.
    @DisplayName("CP-11: Ingresa un código inválido dos veces y la tercera es correcta")
    @Test
    void testVerificarCodigoDesvinculacion_MultipleAttempts() {
        String email = "valid_user@example.com";
        Integer storedCode = 123456;
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        Devices device = new Devices();
        device.setVerificationCode(storedCode);
        device.setAccountHolder(accountHolder);

        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        Integer invalidCode1 = 999999; // Primer código incorrecto
        Integer invalidCode2 = 888888; // Segundo código incorrecto
        Integer validCode = storedCode; // Tercer código correcto

        // Primer código inválido
        boolean firstAttempt = desvinculacionService.verificarCodigoDesvinculacion(email, invalidCode1);
        assertFalse(firstAttempt);

        // Segundo código inválido
        boolean secondAttempt = desvinculacionService.verificarCodigoDesvinculacion(email, invalidCode2);
        assertFalse(secondAttempt);

        // Tercer intento válido
        boolean thirdAttempt = desvinculacionService.verificarCodigoDesvinculacion(email, validCode);
        assertTrue(thirdAttempt);
        verify(accountHolderRepository, times(3)).findByEmail(email);
    }

    // CP-12: El usuario pide su código y cuando lo ingresa, lo ingresa la primera vez mal y la segunda correctamente en el caso de desvinculación.
    @DisplayName("CP-12: Ingresa un código inválido y después un código correcto")
    @Test
    void testVerificarCodigoDesvinculacion_IncorrectAndCorrect() {
        String email = "valid_user@example.com";
        Integer validCode = 123456; // Código válido en el sistema
        Integer incorrectCode = 654321; // Código incorrecto para el primer intento

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        Devices device = new Devices();
        device.setVerificationCode(validCode);
        device.setAccountHolder(accountHolder);

        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        boolean firstAttempt = desvinculacionService.verificarCodigoDesvinculacion(email, incorrectCode);
        assertFalse(firstAttempt);

        boolean secondAttempt = desvinculacionService.verificarCodigoDesvinculacion(email, validCode);
        assertTrue(secondAttempt);
    }

    // CP-14: Ingresar un código válido para la desvinculación
    @DisplayName("CP-14: Ingresa un código válido para la desvinculación")
    @Test
    void testDesvincularDispositivo_ValidCode() throws MessagingException {
        String email = "valid_user@example.com";
        Integer validCode = 123456; // Código de verificación
        String deviceId = "device123"; // ID del dispositivo
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        Devices device = new Devices();
        device.setVerificationCode(validCode);
        device.setAccountHolder(accountHolder);

        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        // Verificar el código de desvinculación
        boolean isCodeValid = desvinculacionService.verificarCodigoDesvinculacion(email, validCode);
        assertTrue(isCodeValid);

        // Desvincular el dispositivo
        boolean desvinculacionResult = desvinculacionService.desvincularDispositivo(email, validCode);
        assertTrue(desvinculacionResult);

        // Verificar que se haya guardado el titular de la cuenta
        verify(accountHolderRepository, times(1)).save(accountHolder);

        // Verificar que se envió el correo de desvinculación
        verify(deviceEmailService, times(1)).enviarMensajeDesvinculacion(email);
    }

    // CP-13: El usuario no agrega ningún correo
    @DisplayName("CP-13: El usuario no agrega ningún correo")
    @Test
    void testVerificarCodigo_NoEmail() {
        String email = "";
        Integer codigo = 123456;
        boolean result = desvinculacionService.verificarCodigoDesvinculacion(email, codigo);

        assertFalse(result);
        verifyNoInteractions(accountHolderRepository);
        verifyNoInteractions(deviceEmailService);
    }
}
*/