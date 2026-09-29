package cl.ufro.dci.naive_pay_api.payments.service;

import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.payments.domain.State;
import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.domain.Credential;
import cl.ufro.dci.naive_pay_api.payments.repository.TransactionRepository;
import cl.ufro.dci.naive_pay_api.registration.repository.BankAccountRepository;
import cl.ufro.dci.naive_pay_api.registration.repository.CredentialRepository;
import cl.ufro.dci.naive_pay_api.payments.util.PasswordHashUtil;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio para manejar las transacciones de la aplicación.z
 */
@Service
public class TransactionService {

	private final TransactionRepository transactionRepository;
	private final BankAccountRepository bankAccountRepository;
	private final CredentialRepository accountHolderRepository;

	public TransactionService(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository,
							  CredentialRepository credentialRepository) {
		this.transactionRepository = transactionRepository;
		this.bankAccountRepository = bankAccountRepository;
		this.accountHolderRepository = credentialRepository;
	}

	/**
	 * Crea una nueva transacción utilizando los datos proporcionados.
	 * @param transactionData Mapa con los datos de la transacción.
	 * @return Un Optional con la transacción creada.
	 */
	public Optional<Transaction> createTransaction(Map<String, String> transactionData) {
		var originAccount = getBankAccount(transactionData.get("originAccountNumber"));
		var destinationAccount = getBankAccount(transactionData.get("destinationAccountNumber"));

		if (originAccount.isEmpty() || destinationAccount.isEmpty()) {
			System.out.println("User not found");
			return Optional.empty();
		}

		Transaction transaction = new Transaction();
		transaction.setToken(transactionData.get("token"));
		transaction.setState(State.PENDING);
		transaction.setAmount(new BigInteger(transactionData.get("amount")));
		transaction.setDate(LocalDate.now());
		transaction.setTime(LocalTime.now().withNano(0));
		transaction.setDescription(transactionData.get("description"));
		transaction.setOriginAccount(originAccount.get());
		transaction.setDestinationAccount(destinationAccount.get());

		return Optional.of(transactionRepository.save(transaction));
	}

	/**
	 * Recupera las transacciones pendientes asociadas a un número de cuenta (Bank Account).
	 * @see BankAccount
	 * @param accountNumber Número de cuenta de origen.
	 * @return Lista de transacciones pendientes.
	 */
	public List<Transaction> getPendingTransactions(String accountNumber) {
		return getBankAccount(accountNumber)
				.map(account -> transactionRepository.findAllByOriginAccountAndState(account, State.PENDING))
				.orElseGet(List::of);
	}

	/**
	 * Recupera una transacción por su token.
	 * @param token Token de la transacción.
	 * @return Un Optional con la transacción recuperada.
	 */
	public Optional<Transaction> getTransaction(String token) {
		return transactionRepository.findByToken(token);
	}

	/**
	 * Actualiza el estado de una transacción utilizando los datos de autorización proporcionados.
	 * @param token Token que identifica la transacción.
	 * @param newState Nuevo estado de la transacción.
	 * @see State
	 * @return true si la transacción se actualizó correctamente, false en caso contrario.
	 */
	public boolean setTransactionStateUpdated(String token, State newState)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return processTransactionStateUpdate(token,  newState);
	}

	/**
	 * Metodo auxiliar para procesar la actualización del estado de una transacción.
	 */
	private boolean processTransactionStateUpdate(String token, State newState)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		Optional<Transaction> transactionOptional = getTransaction(token);

		if (transactionOptional.isEmpty()) {
			System.out.println("Transaction not found");
			return false;
		}

		Transaction transaction = transactionOptional.get();
		Optional<BankAccount> destinationAccountOpt = getBankAccount(transaction.getDestinationAccount().getAccountNumber());
		Optional<BankAccount> originAccountOpt = getBankAccount(transaction.getOriginAccount().getAccountNumber());


		if (destinationAccountOpt.isEmpty() || originAccountOpt.isEmpty()) {
			System.out.println("Banks account not found");
			return false;
		}

		BankAccount originAccount = originAccountOpt.get();
		BankAccount destinationAccount = destinationAccountOpt.get();

		if (newState == State.REJECTED) {
			transaction.setState(State.REJECTED);
		} else if (newState == State.APPROVED) {

			if (!validateSufficientFunds(originAccount, transaction.getAmount())) {
				System.out.println("Insufficient funds");
				return false;
			}

			updateUsersBalances(originAccount, destinationAccount, transaction.getAmount());
			transaction.setState(State.APPROVED);
		} else {
			System.out.println("Invalid state");
			return false;
		}

		transactionRepository.save(transaction);
		System.out.printf("Transaction %s updated correctly to state: %s%n", transaction.getToken(), transaction.getState());
		return true;
	}

	/**
	 * Recupera una cuenta bancaria por su número.
	 * @param accountNumber Número de cuenta.
	 * @return Un Optional con la cuenta bancaria recuperada.
	 */
	private Optional<BankAccount> getBankAccount(String accountNumber) {
		return bankAccountRepository.findById(accountNumber);
	}

	/**
	 * Valida si una cuenta bancaria tiene fondos suficientes para realizar una transacción.
	 * @param originAccount Cuenta de origen.
	 * @param amount Monto de la transacción.
	 * @return true si la cuenta tiene fondos suficientes, false en caso contrario.
	 */
	private boolean validateSufficientFunds(BankAccount originAccount, BigInteger amount) {
		return originAccount.getBalance().compareTo(amount) >= 0;
	}

	/**
	 * Actualiza los saldos de las cuentas bancarias de origen y destino.
	 * @param originAccount Cuenta de origen.
	 * @param destinationAccount Cuenta de destino.
	 * @param amount Monto de la transacción.
	 */
	private void updateUsersBalances(BankAccount originAccount, BankAccount destinationAccount, BigInteger amount) {
		originAccount.setBalance(originAccount.getBalance().subtract(amount));
		destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

		bankAccountRepository.save(originAccount);
		bankAccountRepository.save(destinationAccount);
	}
}
