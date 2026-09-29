package cl.ufro.dci.naive_pay_api.registration.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Representa un titular de cuenta en el sistema.
 * Esta clase contiene la información personal y de contacto del titular de cuenta,
 * así como una referencia a su cuenta bancaria.
 *
 * @author Gerson y Camille
 * @version 2.0
 */
@Setter
@Getter
@Entity
@Table(name = "account_holder")
public class AccountHolder {

    // Atributos
    @Id
    private String rut;  // Identificación única del titular de cuenta (RUT en Chile)

    private String verificationDigit;  // Dígito de verificación del RUT

    private String firstName;  // Nombre del titular de cuenta

    private String lastName;   // Apellido del titular de cuenta

    private String email;      // Correo electrónico del titular de cuenta

    private String phoneNumber; // Número de teléfono del titular de cuenta

    private String documentNumber; // Número de documento de identidad del titular de cuenta

    private String address;    // Dirección del titular de cuenta

    private String occupation; // Ocupación del titular de cuenta

    private String salt;       // Salt utilizado durante el cifrado (para seguridad de contraseñas)

    private String webKey;     // Clave web hasheada para autenticación

    private LocalDate dateOfBirth   ; // Fecha de nacimiento del titular de cuenta

    @Enumerated(EnumType.STRING)
    private Role role = Role.TITULAR;  // Valor por defecto, siempre será TITULAR al registrarse

    // Constructores

    /**
     * Constructor para crear un nuevo titular de cuenta con los detalles especificados.
     *
     * @param rut el RUT del titular de cuenta
     * @param verificationDigit el dígito de verificación del RUT
     * @param firstName el nombre del titular de cuenta
     * @param lastName el apellido del titular de cuenta
     * @param email el correo electrónico del titular de cuenta
     * @param phoneNumber el número de teléfono del titular de cuenta
     * @param documentNumber el número de documento de identidad del titular de cuenta
     * @param address la dirección del titular de cuenta
     * @param occupation la ocupación del titular de cuenta
     * @param dateOfBirth la fecha de nacimiento del titular de cuenta
     */
    public AccountHolder(String rut, String verificationDigit, String firstName,
                         String lastName, String email, String phoneNumber, String documentNumber,
                         String address, String occupation, LocalDate dateOfBirth,
                         String role) {
        this.rut = rut;
        this.verificationDigit = verificationDigit;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.documentNumber = documentNumber;
        this.address = address;
        this.occupation = occupation;
        this.dateOfBirth = dateOfBirth;
        this.role = (role != null) ? Role.valueOf(role) : Role.TITULAR;
    }

    /**
     * Constructor por defecto.
     * Este constructor se usa cuando se crea una instancia vacía del titular de cuenta,
     * por ejemplo, para ser llenada posteriormente desde una base de datos.
     */
    public AccountHolder() {
    }

    public AccountHolder(String number, String number1, String damian, String ramirez, String mail, String number2, String s, String s1, String cajero, LocalDate parse) {
    }
}
