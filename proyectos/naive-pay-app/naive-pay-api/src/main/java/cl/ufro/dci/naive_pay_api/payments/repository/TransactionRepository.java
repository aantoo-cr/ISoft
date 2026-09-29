package cl.ufro.dci.naive_pay_api.payments.repository;

import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import cl.ufro.dci.naive_pay_api.payments.domain.State;
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.registration.domain.Credential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
	Optional<Transaction> findByToken(String token);
	List<Transaction> findAllByOriginAccountAndState(BankAccount originAccount, State state);
	List<Transaction> findByOriginAccountOrDestinationAccount(BankAccount originAccount, BankAccount destinationAccount);
}
