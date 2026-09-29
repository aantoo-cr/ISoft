package cl.ufro.dci.naive_pay_api.registration.service;

import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import cl.ufro.dci.naive_pay_api.registration.repository.BankAccountRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Random;

/**
 * Servicio para gestionar las operaciones relacionadas con las cuentas bancarias.
 * Proporciona métodos para crear cuentas bancarias y generar números de cuenta.

 * \@author Gerson-art
 * \@version 1.0
 */
@Service
public class BankAccountService {
    private final AccountHolderRepository accountHolderRepository;
    private final BankAccountRepository bankAccountRepository;

    /**
     * Constructor para inyectar los repositorios de titulares de cuenta y cuentas bancarias.
     *
     * @param accountHolderRepository el repositorio de titulares de cuenta.
     * @param bankAccountRepository el repositorio de cuentas bancarias.
     */
    public BankAccountService(AccountHolderRepository accountHolderRepository, BankAccountRepository bankAccountRepository) {
        this.accountHolderRepository = accountHolderRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Crea una nueva cuenta bancaria para un titular de cuenta.
     *
     * @param rutAccountHolder el RUT del titular de cuenta.
     * @throws ResponseStatusException si el titular de cuenta no se encuentra o si ocurre un error al crear la cuenta.
     */
    public void createBankAccount(String rutAccountHolder) {
        String numAccount = generateAccountNumber();
        BigInteger balance = BigInteger.ZERO;
        AccountHolder accountHolderFound = getAccountHolderByRut(rutAccountHolder);
        BankAccount bankAccount = new BankAccount(numAccount, balance, accountHolderFound);
        if (accountHolderFound == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found with RUT: " + rutAccountHolder);
        }
        try {
            bankAccountRepository.save(bankAccount);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Holder with RUT already exists: " + rutAccountHolder, e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating account holder", e);
        }
    }

    /**
     * Obtiene un titular de cuenta por su RUT.
     *
     * @param rutAccountHolder el RUT del titular de cuenta.
     * @return el titular de cuenta correspondiente al RUT especificado.
     * @throws ResponseStatusException si el titular de cuenta no se encuentra.
     */
    private AccountHolder getAccountHolderByRut(String rutAccountHolder) {
        return accountHolderRepository.findByRut(rutAccountHolder)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found with RUT: " + rutAccountHolder));
    }

    /**
     * Genera un número de cuenta bancaria aleatorio de 16 dígitos.
     *
     * @return el número de cuenta generado.
     */
    public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Genera cada dígito aleatoriamente para formar un número de 16 dígitos
        for (int i = 0; i < 10; i++) {
            int digito = random.nextInt(10);  // Genera un número entre 0 y 9
            sb.append(digito);
        }

        return sb.toString();
    }

    public Optional<BankAccount> getBankAccountByRut(String rut){
        AccountHolder accountHolder = this.getAccountHolderByRut(rut);
        return bankAccountRepository.findByRutAccountHolder(accountHolder);
    }
}