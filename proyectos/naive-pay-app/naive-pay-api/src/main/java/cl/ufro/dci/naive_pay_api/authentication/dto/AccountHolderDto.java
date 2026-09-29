package cl.ufro.dci.naive_pay_api.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO que representa la información de un titular de cuenta.
 * Este objeto se utiliza para transferir los detalles del titular de cuenta a través de las capas de la aplicación.
 *
 * @author 00jesus
 * @version 1.3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountHolderDto {

    private String rut;  // Identificación única del titular de cuenta (RUT en Chile)
    private String verificationDigit;  // Dígito de verificación del RUT
    private String firstName;  // Nombre del titular de cuenta
    private String lastName;   // Apellido del titular de cuenta
    private String email;      // Correo electrónico del titular de cuenta
    private String phoneNumber; // Número de teléfono del titular de cuenta
    private String documentNumber; // Número de documento de identidad del titular de cuenta
    private String address;    // Dirección del titular de cuenta
    private String occupation; // Ocupación del titular de cuenta
//    private String salt;       // Salt utilizado durante el cifrado (para seguridad de contraseñas)
//    private String webKey;     // Clave web hasheada para autenticación
    private LocalDate dateOfBirth   ; // Fecha de nacimiento del titular de cuenta
    private String role;       // Rol del titular de cuenta
}
