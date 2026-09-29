package cl.ufro.dci.naive_pay_api.funds.service;


import cl.ufro.dci.naive_pay_api.payments.domain.State;
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.payments.repository.TransactionRepository;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import cl.ufro.dci.naive_pay_api.registration.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Servicio encargado de gestionar las operaciones relacionadas con las cuentas bancarias y transacciones.
 * Incluye operaciones como obtener transacciones, obtener saldo, agregar fondos y obtener el número de cuenta por RUT.
 */
@Service
public class FundService {


    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;





    /**
     * Obtiene todas las transacciones asociadas a una cuenta específica, filtrando duplicados en caso de que el origen y destino sean el mismo.
     *
     * @param accountNumber el número de cuenta bancaria para la cual se desean obtener las transacciones.
     * @return una lista de transacciones correspondientes al número de cuenta.
     * @throws RuntimeException si el número de cuenta es nulo o vacío, o si no se encuentran transacciones.
     */

    public List<Transaction> obtenerTransaccionesPorCuenta(String accountNumber) {
        // Verificar si el RUT es nulo o vacío
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new RuntimeException("El numero de cuenta proporcionado es nulo o vacío");
        }
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber((accountNumber))
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // Buscar todas las transacciones donde 'origen' o 'destino' sea la cuenta
        List<Transaction> transacciones = transactionRepository.findByOriginAccountOrDestinationAccount(bankAccount, bankAccount);

        // Filtrar duplicados cuando origen y destino son el mismo (usuario se envía dinero a sí mismo)
        return transacciones.stream()
                .distinct()  // Elimina duplicados
                .collect(Collectors.toList());
    }


    /**
     * Obtiene el saldo disponible de una cuenta específica.
     *
     * @param accountNumber el número de cuenta bancaria para la cual se desea obtener el saldo disponible.
     * @return los fondos disponibles en la cuenta.
     * @throws RuntimeException si la cuenta no se encuentra, si el fondo es nulo, o si el fondo es negativo.
     */

    public BigInteger obtenerFondosPorCuenta(String accountNumber) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber((accountNumber))
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // Obtener los fondos
        BigInteger fondos = bankAccount.getBalance();

        // Verificar condiciones adicionales
        if (fondos == null) {
            throw new RuntimeException("Fondo nulo");
        }
        BigInteger cero = new BigInteger("0");
        if (fondos.compareTo(cero) < 0) {
            throw new RuntimeException("Fondo negativo");
        }

        // Retornar los fondos si todo está correcto
        return fondos;
    }

    /**
     * Agrega fondos a una cuenta específica, generando una transacción correspondiente para la operación.
     *
     * @param numCuenta el número de cuenta a la cual se desea agregar fondos.
     * @param monto el monto a agregar a la cuenta.
     * @return una transacción representando la operación de "agregar fondos".
     * @throws RuntimeException si el número de cuenta es nulo o vacío, si el monto es nulo o negativo, o si la cuenta no se encuentra.
     */

    @Transactional
    public Transaction agregarFondos(String numCuenta, BigInteger monto) {
        // Validar entradas
        if (numCuenta == null || numCuenta.trim().isEmpty()) {
            throw new RuntimeException("La cuenta proporcionada es nulo o vacío");
        }
        if (monto == null || monto.compareTo(BigInteger.ZERO) < 0) {
            throw new RuntimeException("El monto no puede ser nulo o negativo");
        }

        BankAccount bankAccount = bankAccountRepository.findByAccountNumber((numCuenta))
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // 2. Aumentar los fondos de la cuenta
        BigInteger nuevosFondos = bankAccount.getBalance().add(monto); // Suma directamente con BigInteger
        bankAccount.setBalance(nuevosFondos);
        bankAccountRepository.save(bankAccount);  // Guardar la cuenta actualizada

        // 3. Crear la transacción de "AGREGAR FONDOS"

        Transaction transaccion = new Transaction();
        transaccion.setToken(generateUniqueToken());  // Método para generar un token único
        transaccion.setDate(LocalDate.now());
        transaccion.setAmount(monto);
        transaccion.setTime(LocalTime.now().withNano(0));
        transaccion.setOriginAccount(bankAccount);
        transaccion.setDestinationAccount(bankAccount);  // Origen y destino son el mismo usuario
        transaccion.setState(State.APPROVED);  // Enum definido como COMPLETADO

        // 4. Guardar la transacción en la base de datos
        return transactionRepository.save(transaccion);
    }

    /**
     * Genera un token único para la transacción.
     *
     * @return un token único como cadena.
     */
    private String generateUniqueToken() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * Método para obtener el número de cuenta bancaria a partir de un RUT de titular.
     *
     * @param rut El RUT del titular de la cuenta.
     * @return El número de cuenta bancaria asociado al titular con el RUT especificado.
     * @throws RuntimeException si no se encuentra el titular o la cuenta bancaria asociada.
     */
    public String getAccountNumberByRut(String rut) {
        // Buscar el titular de la cuenta usando el RUT
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByRut(rut);

        // Si no se encuentra el titular, lanzar excepción o devolver null
        if (accountHolder.isEmpty()) {
            throw new RuntimeException("No se encontró el titular con RUT: " + rut);
        }

        // Buscar la cuenta bancaria asociada al titular
        Optional<BankAccount> bankAccount = bankAccountRepository.findByRutAccountHolder(accountHolder.get());

        // Si no se encuentra la cuenta bancaria, lanzar excepción o devolver null
        if (bankAccount.isEmpty()) {
            throw new RuntimeException("No se encontbankAccount.get().getAccountNumber()ró la cuenta bancaria para el titular con RUT: " + rut);
        }

        // Devolver el número de cuenta como un String
        return bankAccount.get().getAccountNumber();  // Aquí aseguramos que se devuelve un String
    }


}
