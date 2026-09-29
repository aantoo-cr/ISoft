package cl.ufro.dci.naive_pay_api.payments.domain;

import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

/**
  * Clase que representa una transacción entre dos cuentas bancarias registrada en el sistema. <br>
  * - token: identificador único de la transacción.<br>
  * - state: estado de la transacción.<br>
  * - amount: monto de la transacción.<br>
  * - date: fecha de la transacción.<br>
  * - description: descripción de la transacción.<br>
  * - originAccount: cuenta bancaria de origen de la transacción.<br>
  * - destinationAccount: cuenta bancaria de destino de la transacción.<br>
  * @see BankAccount
  * @version 1.0
  */
@Entity
@Data
@Table(name = "transaction")
public class Transaction {

	// Attributes
	@Id
	private String token;
	private State state;
	private BigInteger amount;
	private LocalDate date;
	private LocalTime time;
	private String description;


	@ManyToOne
	@JoinColumn(name="originAccount", referencedColumnName = "accountNumber")
	private BankAccount originAccount;

	@ManyToOne
	@JoinColumn(name="destinationAccount", referencedColumnName = "accountNumber")
	private BankAccount destinationAccount;
}
