/*package cl.ufro.dci.naive_pay_api.devices.service;

import cl.ufro.dci.naive_pay_api.devices.domain.Devices;
import cl.ufro.dci.naive_pay_api.devices.repository.DeviceRepository;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VinculacionServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private DeviceEmailService deviceEmailService;

    @Spy
    @InjectMocks
    private VinculacionService vinculacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("CP-01: ingresar un correo invalido")
    void testEnviarCodigoVinculacion_EmailNotFound() {
        String email = "ejemplo@gmail.com";
        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = vinculacionService.enviarCodigoVinculacion(email);

        assertFalse(result);
        verify(accountHolderRepository, times(1)).findByEmail(email);
        verifyNoInteractions(deviceEmailService);
    }

    @Test
    @DisplayName("CP-02: ingresar un correo correcto para enviar codigo valida para su vinculacion")
    void testEnviarCodigoVinculacion_Success() throws MessagingException {
        // Email del titular de la cuenta
        String email = "ejemplo.naive@gmail.com";

        // Creación del titular de la cuenta
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        // Creación del dispositivo vinculado al titular de la cuenta
        Devices device = new Devices("DispositivoEjemplo", "Activo", "ModeloX");
        device.setAccountHolder(accountHolder); // Vinculamos el dispositivo con el titular

        // Simulación de la búsqueda del titular de la cuenta en el repositorio
        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));

        // Simulación de la búsqueda del dispositivo en el repositorio
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        // Ejecución del método de prueba
        boolean result = vinculacionService.enviarCodigoVinculacion(email);

        // Validaciones
        assertTrue(result); // Verificamos que el método devuelva true indicando éxito
        verify(accountHolderRepository, times(1)).findByEmail(email); // Verificamos la búsqueda del titular
        verify(deviceEmailService, times(1)).enviarCorreo(eq(email), anyInt()); // Verificamos el envío de correo
        verify(deviceRepository, times(1)).save(device); // Guardamos cambios en el dispositivo
    }


    @Test
    @DisplayName("CP-03: ingresar un codigo invalido para su vinculacion")
    void testVerificarCodigo_InvalidCode() {
        // Configuración de datos de prueba
        String email = "ejemplo.naive@gmail.com";
        Integer codigoIngresado = 123456;
        Integer codigoCorrecto = 987654;

        // Crear el titular de la cuenta
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        // Crear el dispositivo asociado con un código de verificación
        Devices device = new Devices();
        device.setAccountHolder(accountHolder);
        device.setVerificationCode(codigoCorrecto);

        // Simular que el repositorio devuelve el titular de la cuenta
        when(accountHolderRepository.findById(email)).thenReturn(Optional.of(accountHolder));

        // Simular que el repositorio de dispositivos devuelve el dispositivo asociado al titular de la cuenta
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        // Ejecutar el método de verificación con un código incorrecto
        boolean result = vinculacionService.verificarCodigo(email, codigoIngresado);

        // Validar el resultado esperado
        assertFalse(result); // Debería ser falso ya que el código es incorrecto
        verify(accountHolderRepository, times(1)).findById(email); // Verificar que se llamó a la búsqueda de cuenta
        verify(deviceRepository, times(1)).findByAccountHolder(accountHolder); // Verificar que se buscó el dispositivo asociado
        verifyNoInteractions(deviceEmailService); // No debería haber interacción con el servicio de email
    }

    @Test
    @DisplayName("CP-04: ingresar codigo valida para su vinculacion")
    void testVerificarCodigo_Success() throws MessagingException {
        // Configuración de datos de prueba
        String email = "ejemplo.naive@gmail.com";
        Integer codigoIngresado = 123456;

        // Crear el titular de la cuenta (AccountHolder)
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        // Crear el dispositivo asociado con un código de verificación
        Devices device = new Devices();
        device.setAccountHolder(accountHolder);
        device.setVerificationCode(codigoIngresado);

        // Simular que el repositorio de AccountHolder devuelve el titular de la cuenta
        when(accountHolderRepository.findById(email)).thenReturn(Optional.of(accountHolder));

        // Simular que el repositorio de Dispositivos devuelve el dispositivo asociado al titular de la cuenta
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        // Ejecutar el método de verificación con un código válido
        boolean result = vinculacionService.verificarCodigo(email, codigoIngresado);

        // Validar el resultado esperado
        assertTrue(result); // Debería ser verdadero ya que el código es correcto

        // Verificar las interacciones esperadas
        verify(accountHolderRepository, times(1)).findById(email); // Verificar que se haya buscado el titular
        verify(deviceRepository, times(1)).findByAccountHolder(accountHolder); // Verificar que se haya buscado el dispositivo
        verify(deviceRepository, times(1)).save(device); // Verificar que el dispositivo haya sido guardado
        verify(deviceEmailService, times(1)).enviarMensajeConfirmacion(email); // Verificar que se haya enviado el mensaje de confirmación
    }

    @Test
    @DisplayName("CP-05: ingresar codigo invalido para la vinculacion")
    void testVerificarCodigo_MultipleInvalidAttempts() {
        // Datos de prueba
        String email = "ejemplo.naive@gmail.com";
        Integer codigo = 123456;

        // Crear el titular de la cuenta
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        // Crear el dispositivo asociado con un código de verificación incorrecto
        Devices device = new Devices();
        device.setAccountHolder(accountHolder);
        device.setVerificationCode(987654); // Código incorrecto

        // Configurar el comportamiento de los repositorios simulados
        when(accountHolderRepository.findById(email)).thenReturn(Optional.of(accountHolder));
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(device));

        // Intentos de verificación con códigos inválidos
        boolean result1 = vinculacionService.verificarCodigo(email, 111111);
        boolean result2 = vinculacionService.verificarCodigo(email, 222222);
        boolean result3 = vinculacionService.verificarCodigo(email, 333333);
        boolean result4 = vinculacionService.verificarCodigo(email, codigo); // Código correcto

        // Verificar que todos los intentos con código incorrecto fallan
        assertFalse(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertFalse(result4);

        // Verificar las interacciones con los repositorios
        verify(accountHolderRepository, times(4)).findById(email); // Se busca el titular de la cuenta 4 veces
        verifyNoInteractions(deviceEmailService); // No se envía correo porque el código nunca es correcto
    }

    @Test
    @DisplayName("CP-06: ingresar codigo de vinculacion para el bloqueo y posterior bloqueo y vinculacion")
    void testVerificarCodigo_VinculaNuevoDispositivoYBloqueaAnterior() throws MessagingException {
        // Arrange
        String email = "ejemplo.naive@gmail.com";
        Integer codigo = 123456;

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        Devices existingDevice = new Devices();
        existingDevice.setId(1L);
        existingDevice.setName("Existing Device");
        existingDevice.setStatus("Vinculado");
        existingDevice.setModelo("Model X");
        existingDevice.setVerificationCode(codigo);
        existingDevice.setAccountHolder(accountHolder);

        Devices newDevice = new Devices();
        newDevice.setId(2L);
        newDevice.setName("New Device");
        newDevice.setStatus("Pendiente de confirmación");
        newDevice.setModelo("Model Y");
        newDevice.setVerificationCode(codigo);
        newDevice.setAccountHolder(accountHolder);

        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(existingDevice, newDevice));

        // Act
        boolean result = vinculacionService.verificarCodigo(email, codigo);

        // Assert
        assertTrue(result);
        verify(accountHolderRepository, times(1)).findByEmail(email);
        verify(deviceRepository, times(1)).findByAccountHolder(accountHolder);
        verify(deviceRepository, times(1)).save(existingDevice);
        verify(deviceRepository, times(1)).save(newDevice);
        verify(deviceEmailService, times(1)).enviarMensajeDesvinculacion(email);
        verify(deviceEmailService, times(1)).enviarMensajeConfirmacion(email);
    }

    @Test
    @DisplayName("CP-07: no ingresa ningun correo")
    void testVerificarCodigo_NoEmail() {
        String email = "";
        Integer codigo = 123456;

        boolean result = vinculacionService.verificarCodigo(email, codigo);

        assertFalse(result);
        verifyNoInteractions(accountHolderRepository);
        verifyNoInteractions(deviceEmailService);
    }

    @Test
    @DisplayName("CP-15: ingresar codigo despues del bloqueo sea incorrecto")
    void testEnviarCodigoVinculacion_CodigoIncorrecto() throws MessagingException {
        String email = "test@example.com";
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        // Simular el código de verificación
        Integer codigo = 123456;

        Devices existingDevice = new Devices();
        existingDevice.setId(1L);
        existingDevice.setName("Existing Device");
        existingDevice.setStatus("Pendiente de confirmación");
        existingDevice.setModelo("Model X");
        existingDevice.setVerificationCode(codigo);
        existingDevice.setAccountHolder(accountHolder);

        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(existingDevice));

        // Crear un spy del servicio VinculacionService
        VinculacionService vinculacionServiceSpy = spy(vinculacionService);
        doReturn(codigo).when(vinculacionServiceSpy).generateVerificationCode();

        // Enviar el código de verificación
        boolean result = vinculacionServiceSpy.enviarCodigoVinculacion(email);

        assertTrue(result);
        verify(deviceEmailService, times(1)).enviarCorreo(eq(email), eq(codigo));

        // Verificar el código ingresado incorrectamente
        boolean verificacionResult = vinculacionServiceSpy.verificarCodigo(email, 654321); // Incorrect code
        assertFalse(verificacionResult);
    }

    @Test
    @DisplayName("CP-16: ingresar codigo despues del bloqueo sea correcto")
    void testEnviarCodigoVinculacion_CodigoCorrecto() throws MessagingException {
        String email = "test@example.com";
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setEmail(email);

        // Simular el código de verificación
        Integer codigo = 123456;

        Devices existingDevice = new Devices();
        existingDevice.setId(1L);
        existingDevice.setName("Existing Device");
        existingDevice.setStatus("Pendiente de confirmación");
        existingDevice.setModelo("Model X");
        existingDevice.setVerificationCode(codigo);
        existingDevice.setAccountHolder(accountHolder);

        when(accountHolderRepository.findByEmail(email)).thenReturn(Optional.of(accountHolder));
        when(deviceRepository.findByAccountHolder(accountHolder)).thenReturn(List.of(existingDevice));

        // Crear un spy del servicio VinculacionService
        VinculacionService vinculacionServiceSpy = spy(vinculacionService);
        doReturn(codigo).when(vinculacionServiceSpy).generateVerificationCode();

        // Enviar el código de verificación
        boolean result = vinculacionServiceSpy.enviarCodigoVinculacion(email);

        assertTrue(result);
        verify(deviceEmailService, times(1)).enviarCorreo(eq(email), eq(codigo));

        // Verificar el código ingresado
        boolean verificacionResult = vinculacionServiceSpy.verificarCodigo(email, codigo);
        assertTrue(verificacionResult);
    }


}

*/

