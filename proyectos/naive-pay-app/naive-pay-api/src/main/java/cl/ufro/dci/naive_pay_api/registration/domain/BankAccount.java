package cl.ufro.dci.naive_pay_api.registration.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Representa una cuenta bancaria en el sistema.
 * Esta clase almacena la información relacionada con el número de cuenta,
 * el balance disponible y una referencia al titular de la cuenta.
 *
 * @author Gerson y Camille
 * @version 2.0
 */
@Entity
@Data
@Table(name = "bank_account")
public class  BankAccount {

    // Atributos

    /**
     * El número de cuenta bancaria único.
     * Este es el identificador principal de la cuenta bancaria.
     */
    @Id
    private String accountNumber;

    private BigInteger balance; // Cambiar a BigDecimal si el saldo puede tener decimales

    /**
     * El titular de la cuenta bancaria.
     * Relaciona la cuenta bancaria con un titular a través del RUT (número de identificación único).
     * Se establece una relación de tipo "uno a uno".
     */
    @OneToOne
    @JoinColumn(name = "rutAccountHolder", referencedColumnName = "rut")
    private AccountHolder rutAccountHolder;  // La relación OneToOne ahora está correctamente representada

    // Constructores

    /**
     * Constructor vacío para la clase BankAccount.
     * Este constructor es utilizado generalmente por JPA para la creación de entidades.
     */
    public BankAccount() {}

    public BankAccount(String accountNumber, BigInteger balance, AccountHolder rutAccountHolder) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.rutAccountHolder = rutAccountHolder;
    }
}
