package cl.ufro.dci.naive_pay_api.registration.dto;

import lombok.Getter;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para representar la información del titular de la cuenta.
 * Esta clase contiene los datos necesarios para transferir información del titular de cuenta
 * entre las capas de la aplicación.
 *
 * @author Gerson, Camille
 */
@Getter
public class AccountHolderDTO {

    /**
     * Identificación única del titular de cuenta.
     */
    private String rut;

    /**
     * Dígito verificador asociado al RUT del titular.
     */
    private String verificationDigit;

    /**
     * Primer nombre del titular de cuenta.
     */
    private String firstName;

    /**
     * Apellido del titular de cuenta.
     */
    private String lastName;

    /**
     * Correo electrónico del titular de cuenta.
     */
    private String email;

    /**
     * Número de teléfono del titular de cuenta.
     */
    private String phoneNumber;

    /**
     * Número de documento de identificación del titular.
     */
    private String documentNumber;

    /**
     * Dirección física del titular de cuenta.
     */
    private String address;

    /**
     * Ocupación del titular de cuenta.
     */
    private String occupation;

    /**
     * Salt utilizado durante el proceso de cifrado de contraseñas.
     */
    private String salt;

    /**
     * Hash de la contraseña del titular de cuenta.
     */
    private String hash;

    /**
     * Fecha de nacimiento del titular de cuenta.
     */
    private LocalDate dateOfBirth;

    private String role;  // El cliente puede enviar "ADMIN" o "TITULAR"


}
