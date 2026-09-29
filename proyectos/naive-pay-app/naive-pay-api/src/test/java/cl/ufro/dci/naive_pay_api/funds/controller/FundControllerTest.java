/*package cl.ufro.dci.naive_pay_api.funds.controller;
import cl.ufro.dci.naive_pay_api.funds.domain.Estado;
import cl.ufro.dci.naive_pay_api.funds.domain.Transaction;
import cl.ufro.dci.naive_pay_api.funds.service.FundService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FundControllerTest {

    @MockBean
    private FundService fundService; // Mock para el servicio

    @Autowired
    private MockMvc mockMvc; // MockMvc inyectado para realizar las pruebas

    @InjectMocks
    private FundController fundController; // Controlador real

    @BeforeEach
    public void setup() {
        // Ya no es necesario configurar MockMvc aquí, ya que Spring lo hace automáticamente
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test obtener saldo - GET /api/funds/saldo/{rutUser}")
    public void obtenerSaldo() throws Exception {
        String rutUser = "12345";
        float saldo = 12342f; // Suponiendo que el saldo es un float

        // Mock del servicio que retorna el saldo
        doReturn(saldo).when(fundService).obtenerFondosPorCuenta(rutUser);

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/funds/saldo/{rutUser}", rutUser))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(String.valueOf(saldo))); // Verifica que el contenido sea el saldo como cadena
    }



    @Test
    @DisplayName("Test obtener saldo - GET /api/funds/saldo/{rutUser} - fallo cuando la cuenta no se encuentra")
    public void obtenerSaldoFalloCuentaNoEncontrada() throws Exception {
        String rutUser = "12345";

        // Simula el comportamiento del servicio para lanzar una excepción
        doThrow(new RuntimeException("Cuenta no encontrada")).when(fundService).obtenerFondosPorCuenta(rutUser);

        // Realiza la solicitud al controlador
        mockMvc.perform(get("/api/funds/saldo/{rutUser}", rutUser))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("Test agregar fondos - POST /api/funds/agregar-fondos")
    public void agregarFondos() throws Exception {
        String rutUser = "12345678-9";
        float monto = 100.00f;
        Transaction transaction = new Transaction("token123", Estado.APPROVED, LocalDateTime.now(), monto, rutUser, rutUser);

        // Mock del servicio que retorna la transacción
        doReturn(transaction).when(fundService).agregarFondos(rutUser, monto); // Cambia fundController por fundService

        // Realizar la solicitud POST
        mockMvc.perform(post("/api/funds/agregar-fondos")
                        .param("rutUser", rutUser)
                        .param("monto", String.valueOf(monto)))
                // Validar respuesta 200 OK y tipo de contenido JSON
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.origen", is(rutUser)))
                .andExpect(jsonPath("$.monto", is((double) monto)))
                .andExpect(jsonPath("$.estado", is(Estado.APPROVED.name())));
    }

    @Test
    @DisplayName("Test agregar fondos - POST /api/funds/agregar-fondos - fallo")
    public void agregarFondosFallo() throws Exception {
        String rutUser = "12345678-9";
        float monto = 100.00f;

        doThrow(new RuntimeException("Error al agregar fondos")).when(fundService).agregarFondos(rutUser, monto); // Cambia fundController por fundService

        mockMvc.perform(post("/api/funds/agregar-fondos")
                        .param("rutUser", rutUser)
                        .param("monto", String.valueOf(monto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Test obtener transacciones - GET /api/funds/transacciones/{rutUser}")
    public void obtenerTransacciones() throws Exception {
        String rutUser = "12345678-9";
        float monto = 100.00f;
        Transaction transaction = new Transaction("token123", Estado.APPROVED, LocalDateTime.now(), monto, rutUser, rutUser);

        List<Transaction> transacciones = Collections.singletonList(transaction);

        doReturn(transacciones).when(fundService).obtenerTransaccionesPorCuenta(rutUser); // Cambia fundController por fundService

        mockMvc.perform(get("/api/funds/transacciones/{rutUser}", rutUser))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].origen", is(rutUser)))
                .andExpect(jsonPath("$[0].monto", is((double) monto)));
    }

    @Test
    @DisplayName("Test obtener transacciones - GET /api/funds/transacciones/{rutUser} - fallo por RUT vacío")
    public void obtenerTransaccionesFalloPorRutVacio() throws Exception {
        String rutUser = ""; // RUT vacío para provocar el error

        // Simula el comportamiento del servicio
        doThrow(new RuntimeException("El RUT proporcionado es nulo o vacío")).when(fundService).obtenerTransaccionesPorCuenta(rutUser);

        // Realiza la solicitud al controlador
        mockMvc.perform(get("/api/funds/transacciones/{rutUser}", rutUser))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Test obtener saldo negativo - GET /api/funds/saldo/{rutUser}")
    public void obtenerSaldoNegativo() throws Exception {
        String rutUser = "12345";
        float saldo = -12342f;

        // Mock del servicio para que lance una excepción al obtener un saldo negativo
        doThrow(new RuntimeException("Fondo negativo")).when(fundService).obtenerFondosPorCuenta(rutUser);

        // Realizar la solicitud GET y verificar que la respuesta es un error con el estado 500
        mockMvc.perform(get("/api/funds/saldo/{rutUser}", rutUser))//
                .andExpect(status().isInternalServerError()) //
                .andExpect(content().contentType("text/plain;charset=UTF-8")) // Verifica el tipo de contenido
                .andExpect(content().string(containsString("Fondo negativo"))); // Verifica que el contenido contenga "Fondo negativo"
    }
    @Test
    @DisplayName("Test obtener saldo nulo - GET /api/funds/saldo/{rutUser}")
    public void obtenerSaldonulo() throws Exception {
        String rutUser = "12345";
        Null saldo = null;

        // Mock del servicio para que lance una excepción al obtener un saldo negativo
        doThrow(new RuntimeException("Fondo nulo")).when(fundService).obtenerFondosPorCuenta(rutUser);

        // Realizar la solicitud GET y verificar que la respuesta es un error con el estado 500
        mockMvc.perform(get("/api/funds/saldo/{rutUser}", rutUser))//
                .andExpect(status().isInternalServerError()) //
                .andExpect(content().contentType("text/plain;charset=UTF-8")) // Verifica el tipo de contenido
                .andExpect(content().string(containsString("Fondo nulo"))); // Verifica que el contenido contenga "Fondo negativo"
    }
    @Test
    @DisplayName("Test agregar fondos - POST /api/funds/agregar-fondos - fallo ya que el fond oes negativo")
    public void agregarFondosNegativo() throws Exception {
        String rutUser = "12345678-9";
        float monto = -12345f;

        doThrow(new RuntimeException("Error al agregar fondos")).when(fundService).agregarFondos(rutUser, monto); // Cambia fundController por fundService

        mockMvc.perform(post("/api/funds/agregar-fondos")
                        .param("rutUser", rutUser)
                        .param("monto", String.valueOf(monto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Error al agregar fondos")));
    }
    @Test
    @DisplayName("Test agregar fondos - POST /api/funds/agregar-fondos - fallo ya que el fondo es nulo")
    public void agregarFondosNulo() throws Exception {
        String rutUser = "12345678-9";

        // Mock del servicio para lanzar una excepción al intentar agregar fondos nulos
        doThrow(new RuntimeException("Error al agregar fondos")).when(fundService).agregarFondos(rutUser, null);

        // Realizar la solicitud POST y verificar que la respuesta es un error con el estado 500
        mockMvc.perform(post("/api/funds/agregar-fondos")
                        .param("rutUser", rutUser)
                        .param("monto", "") // Cambiado a un valor vacío
                )
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Test obtener transacciones - GET /api/funds/transacciones/{rutUser} - mensaje de error cuando no hay transacciones")
    public void NoobtenerTransacciones() throws Exception {
        String rutUser = "12345678-9";

        // Simular que no se encuentran transacciones lanzando una excepción
        doThrow(new RuntimeException("No se encontraron transacciones para el RUT proporcionado")).when(fundService).obtenerTransaccionesPorCuenta(rutUser);

        // Realizar la solicitud GET y verificar que se recibe un mensaje de error
        mockMvc.perform(get("/api/funds/transacciones/{rutUser}", rutUser))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(containsString("No se encontraron transacciones para el RUT proporcionado"))); // Verifica el mensaje de error
    }



}


 */