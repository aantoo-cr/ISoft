package cl.ufro.dci.naive_pay_api.registration.repository;

import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD de las cuentas bancarias.
 * Extiende de CrudRepository para proporcionar métodos CRUD básicos.
 * \@author Gerson-art
 * \@version 1.0
 */
public interface BankAccountRepository extends CrudRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    Optional<BankAccount> findByRutAccountHolder(AccountHolder rutAccountHolder);
}