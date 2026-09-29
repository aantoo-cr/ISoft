package cl.ufro.dci.naive_pay_api.funds.controller;

import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.funds.service.FundService;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/funds") // Prefijo común para todas las rutas relacionadas con fondos
public class    FundController {
    
    @Autowired
    private FundService fundService;
    /**
     * Obtiene las transacciones asociadas a una cuenta específica.
     *
     * Este método consulta el servicio de fondos para obtener una lista de
     * transacciones que corresponden a un número de cuenta específico.
     *
     * @param numCuenta el número de cuenta del usuario.
     * @return una lista de transacciones correspondientes al número de cuenta especificado.
     */
    @GetMapping("/transacciones/{numCuenta}")
    public ResponseEntity<List<Transaction>> obtenerTransacciones(@PathVariable String numCuenta) {
        try {
            List<Transaction> transacciones = fundService.obtenerTransaccionesPorCuenta(numCuenta);
            return ResponseEntity.ok(transacciones);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Obtiene el saldo disponible de una cuenta específica.
     *
     * Este método consulta el servicio de fondos para obtener el saldo disponible
     * en una cuenta especificada por su número de cuenta.
     *
     * @param numCuenta el número de cuenta del usuario.
     * @return el saldo disponible en la cuenta especificada.
     */
    @GetMapping("/saldo/{numCuenta}")
    public ResponseEntity<Float> obtenerFondos(@PathVariable String numCuenta) {
        try {
            Float saldo = fundService.obtenerFondosPorCuenta(numCuenta).floatValue();
            return ResponseEntity.ok(saldo);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Agrega fondos a una cuenta específica.
     *
     * Este método permite realizar un depósito a la cuenta de un usuario. El número de cuenta
     * y el monto a agregar se envían como parámetros en el cuerpo de la solicitud.
     *
     * @param datos un mapa que contiene el número de cuenta ("numCuenta") y el monto a agregar ("monto").
     * @return una transacción representando el depósito realizado.
     */
    @PostMapping("/agregar-fondos")
    public ResponseEntity<Transaction> agregarFondos(@RequestBody Map<String, Object> datos) {
        try {
            // Extraer los valores de "numCuenta" y "monto" del JSON recibido
            String numCuenta = (String) datos.get("numCuenta");
            Integer monto = ((Number) datos.get("monto")).intValue();

            Transaction transaccion = fundService.agregarFondos(numCuenta, BigInteger.valueOf(monto));
            return ResponseEntity.ok(transaccion);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Manejador global de excepciones para RuntimeException.
     *
     * Este método captura y maneja cualquier RuntimeException que se pueda lanzar en los métodos del controlador,
     * y devuelve una respuesta con el mensaje de la excepción y un código de estado 500.
     *
     * @param e la excepción lanzada.
     * @return una respuesta de error con el mensaje de la excepción y un código de estado 500.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }


    /**
     * Endpoint para obtener el número de cuenta bancaria dado un RUT.
     *
     * Este método consulta el servicio de fondos para obtener el número de cuenta
     * asociado a un titular de cuenta identificado por su RUT.
     *
     * @param rut El RUT del titular de la cuenta.
     * @return El número de cuenta bancaria.
     */
    @GetMapping("/accountNumber/{rut}")
    public String getAccountNumber(@PathVariable String rut) {
        return fundService.getAccountNumberByRut(rut);
    }
}
