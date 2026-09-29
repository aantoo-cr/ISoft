/*package cl.ufro.dci.naive_pay_api.funds.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import cl.ufro.dci.naive_pay_api.funds.repository.*;
import cl.ufro.dci.naive_pay_api.funds.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

class FundServiceTest {

    @AfterEach
    void tearDown() {
    }

    @Mock
    private TransaccionRepository transactionRepository;

    @InjectMocks
    private FundService fundService;

    @Mock
    private CuentaRepository cuentaRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // Test 1: Cuando el RUT es nulo o vacío
    @Test
    public void testObtenerTransaccionesPorRut_CuentaNuloOVacio() {
        // RUT nulo
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fundService.obtenerTransaccionesPorCuenta(null);
        });
        assertEquals("El RUT proporcionado es nulo o vacío", exception.getMessage());

        // RUT vacío
        exception = assertThrows(RuntimeException.class, () -> {
            fundService.obtenerTransaccionesPorCuenta("");
        });
        assertEquals("El RUT proporcionado es nulo o vacío", exception.getMessage());

        // RUT con espacios en blanco
        exception = assertThrows(RuntimeException.class, () -> {
            fundService.obtenerTransaccionesPorCuenta("   ");
        });
        assertEquals("El RUT proporcionado es nulo o vacío", exception.getMessage());
    }

    // Test 2: Cuando no se encuentran transacciones para el RUT proporcionado
    @Test
    public void testObtenerTransaccionesPorRut_SinTransacciones() {
        String rutUser = "12345678-9";

        // Configurar mock para devolver una lista vacía
        when(transactionRepository.findByOrigenOrDestino(rutUser, rutUser)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fundService.obtenerTransaccionesPorCuenta(rutUser);
        });
        assertEquals("No se encontraron transacciones para el RUT proporcionado", exception.getMessage());
    }

    // Test 1: Cuando la cuenta no se encuentra
    @Test
    public void testObtenerFondosPorCuenta_CuentaNoEncontrada() {
        String rutUser = "12345678-9";

        // Simular que el repositorio no encuentra la cuenta
        when(cuentaRepository.findById(rutUser)).thenReturn(Optional.empty());

        // Verificar que se lanza una excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fundService.obtenerFondosPorCuenta(rutUser);
        });

        assertEquals("Cuenta no encontrada", exception.getMessage());
    }

    // Test 2: Cuando los fondos son negativos
    @Test
    public void testObtenerFondosPorRut_FondosNegativos() {
        String rutUser = "12345678-9";

        // Simular que se encuentra una cuenta con fondos negativos
        Cuenta cuentaConFondosNegativos = new Cuenta(rutUser, -500f);
        when(cuentaRepository.findById(rutUser)).thenReturn(Optional.of(cuentaConFondosNegativos));

        // Verificar que se lanza una excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fundService.obtenerFondosPorCuenta(rutUser);
        });

        assertEquals("Fondo negativo", exception.getMessage());
    }

    // Test 3: Cuando los fondos son válidos
    @Test
    public void testObtenerFondosPorRut_FondosValidos() {
        String rutUser = "12345678-9";

        // Simular que se encuentra una cuenta con fondos válidos
        Cuenta cuentaConFondosValidos = new Cuenta(rutUser, 1500f);
        when(cuentaRepository.findById(rutUser)).thenReturn(Optional.of(cuentaConFondosValidos));

        // Verificar que se retorna el valor correcto de los fondos
        Float fondos = fundService.obtenerFondosPorCuenta(rutUser);

        assertNotNull(fondos);
        assertEquals(1500f, fondos);
    }

    // Test 1: RUT nulo o vacío
    @Test
    public void testAgregarFondos_RUTNuloOVacio() {
        // Prueba con RUT nulo
        Exception exceptionNulo = assertThrows(RuntimeException.class, () -> {
            fundService.agregarFondos(null, 100.0f);
        });
        assertEquals("El RUT proporcionado es nulo o vacío", exceptionNulo.getMessage());

        // Prueba con RUT vacío
        Exception exceptionVacio = assertThrows(RuntimeException.class, () -> {
            fundService.agregarFondos("   ", 100.0f);
        });
        assertEquals("El RUT proporcionado es nulo o vacío", exceptionVacio.getMessage());
    }

    // Test 2: Monto nulo o negativo
    @Test
    public void testAgregarFondos_MontoNuloONegativo() {
        String rutUser = "12345678-9";

        // Prueba con monto nulo
        Exception exceptionNulo = assertThrows(RuntimeException.class, () -> {
            fundService.agregarFondos(rutUser, null);
        });
        assertEquals("El monto no puede ser nulo o negativo", exceptionNulo.getMessage());

        // Prueba con monto negativo
        Exception exceptionNegativo = assertThrows(RuntimeException.class, () -> {
            fundService.agregarFondos(rutUser, -100.0f);
        });
        assertEquals("El monto no puede ser nulo o negativo", exceptionNegativo.getMessage());
    }

    // Test 3: Cuenta no encontrada
    @Test
    public void testAgregarFondos_CuentaNoEncontrada() {
        String rutUser = "12345678-9";
        Float monto = 100.0f;

        // Simular que la cuenta no se encuentra en el repositorio
        when(cuentaRepository.findById(rutUser)).thenReturn(Optional.empty());

        // Verificar que se lanza una excepción cuando la cuenta no es encontrada
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fundService.agregarFondos(rutUser, monto);
        });
        assertEquals("Cuenta no encontrada", exception.getMessage());
    }

    // Test 4: Fondos agregados correctamente
    @Test
    public void testAgregarFondos_FondosAgregadosCorrectamente() {
        String rutUser = "12345678-9";
        Float monto = 100.0f;

        // Simular que se encuentra la cuenta en el repositorio
        Cuenta cuentaMock = new Cuenta(rutUser, 500.0f);
        when(cuentaRepository.findById(rutUser)).thenReturn(Optional.of(cuentaMock));

        // Simular la creación de una transacción
        Transaction transaccionMock = new Transaction("token1", Estado.APPROVED, LocalDateTime.now(), monto, rutUser, rutUser);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaccionMock);

        // Llamar al método que estamos probando
        Transaction resultado = fundService.agregarFondos(rutUser, monto);

        // Verificar que los fondos de la cuenta fueron actualizados correctamente
        assertEquals(600.0f, cuentaMock.getFondos());  // 500 + 100

        // Verificar que se creó una transacción con el estado APPROVED
        assertNotNull(resultado);
        assertEquals(Estado.APPROVED, resultado.getEstado());
        assertEquals(monto, resultado.getMonto());
        assertEquals(rutUser, resultado.getOrigen());
        assertEquals(rutUser, resultado.getDestino());

        // Verificar que se guardó la cuenta actualizada
        verify(cuentaRepository).save(cuentaMock);
    }
}

 */