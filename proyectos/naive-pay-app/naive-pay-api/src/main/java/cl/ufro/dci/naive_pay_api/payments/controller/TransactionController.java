package cl.ufro.dci.naive_pay_api.payments.controller;

import cl.ufro.dci.naive_pay_api.payments.domain.State;
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.payments.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TransactionController es un controlador REST que maneja solicitudes HTTP relacionadas con una transacción. <br>
 * Proporciona endpoints para crear transacciones, recuperar transacciones por token,
 * recuperar transacciones pendientes por número de cuenta de origen y actualizar el estado de una transacción. <br>
 * <br>
 * Endpoints:<br>
 * <br>
 * - POST /api/transaction/create: Crea una nueva transacción.<br>
 * - GET /api/transaction/get/{token}: Recupera una transacción por su token.<br>
 * - GET /api/transaction/getPending/{originAccountNumber}: Recupera transacciones pendientes asociadas a un número de cuenta de origen.<br>
 * - PUT /api/transaction/update/{token}: Actualiza el estado de una transacción utilizando datos de autorización.<br>
 * <br>
 * Anotaciones:<br>
 *<br>
 * - @RestController: Indica que esta clase es un controlador REST.<br>
 * - @CrossOrigin(origins = "*"): Permite solicitudes de origen cruzado desde cualquier origen.<br>
 * - @RequestMapping("/api/transaction"): Mapea las solicitudes HTTP a /api/transaction a este controlador.<br>
 * - @Autowired: Inyecta la dependencia de TransactionService.<br>
 *<br>
 * Métodos:<br>
 *<br>
 * - createTransaction:
 * - getTransaction:
 * - getPendingTransactions:
 * - setTransactionState:
 *
 * <br>
 * Dependencias:
 * <br>
 * - TransactionService: Un servicio que proporciona métodos para crear, recuperar y actualizar transacciones.
 * @see TransactionService
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	/**
	 * Crea una nueva transacción utilizando los datos proporcionados de la transacción.
	 * @param transactionData Mapa con los datos de la transacción. <br>
	 * {<br>
	 *   "token": "unique-token-12345",<br>
	 *   "state": "PENDING",<br>
	 *   "amount": 20000,<br>
	 *   "description": "Pago de cuentas",<br>
	 *   "originAccountNumber": 11223344556677,<br>
	 *   "destinationAccountNumber": 77665544332211<br>
	 * }<br>
	 * @return ResponseEntity con la transacción creada y el estado HTTP 201 si la transacción se creó correctamente.
	 */
	@PostMapping("create")
	public ResponseEntity<?> createTransaction(@RequestBody Map<String, String> transactionData) {
		Optional<Transaction> transactionOptional = transactionService.createTransaction(transactionData);
		if (transactionOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating transaction");
		}
		return new ResponseEntity<>(transactionOptional.get(), HttpStatus.CREATED);
	}

	/**
	 * Recupera una transacción por su token.
	 * @param token Token de la transacción.
	 * @return ResponseEntity con la transacción recuperada y el estado HTTP 200 si la transacción se recuperó correctamente.
	 */
	@GetMapping("get/{token}")
	public ResponseEntity<?> getTransaction(@PathVariable String token) {
		Optional<Transaction> transactionOptional = transactionService.getTransaction(token);
		if (transactionOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
		}
		return new  ResponseEntity<>(transactionOptional.get(), HttpStatus.OK);
	}

	/**
	 * Recupera las transacciones asociadas a un rut de origen y que estén en estado PENDING
	 * @param originAccountNumber Número de cuenta de origen.
	 * @return ResponseEntity con la lista de transacciones pendientes y el estado HTTP 200
	 */
	@GetMapping("getPending/{originAccountNumber}")
	public ResponseEntity<?> getPendingTransactions(@PathVariable String originAccountNumber) {
		List<Transaction> transactions = transactionService.getPendingTransactions(originAccountNumber);
		if (transactions.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No pending transactions found");
		}
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	/**
	 * Actualiza el estado de una transacción utilizando los datos de autorización proporcionados.
	 * @param token Token que identifica la transacción.
	 * @param authorizationData Mapa con los datos de autorización. <br>
	 * { <br>
	 *      "publicKey": "claveValida", <br>
	 *      "state": "APPROVED" <br>
	 * }
	 * @return ResponseEntity con un mensaje de confirmación y el estado HTTP 200 si la transacción se actualizó correctamente.
	 */
	@PutMapping("update/{token}")
	public ResponseEntity<String> setTransactionState(
			@PathVariable String token,
			@RequestBody Map<String, String> authorizationData) throws NoSuchAlgorithmException, InvalidKeySpecException {

		State newState = State.valueOf(authorizationData.get("state"));

		boolean isApproved = transactionService.setTransactionStateUpdated(token, newState);

		if (isApproved) {
			return ResponseEntity.ok("transaction state updated correctly:");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid transaction or private key");
		}
	}

}