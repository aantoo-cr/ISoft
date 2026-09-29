package cl.ufro.dci.naive_pay_api.authentication.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para el restablecimiento de contraseñas.
 * Este objeto encapsula la información necesaria para cambiar la contraseña de un usuario.
 *
 * Contiene:
 *  El correo electrónico del usuario.
 *  La nueva contraseña que se desea establecer.
 *
 * Utilizado por el controlador y el servicio de restablecimiento de contraseñas.
 *
 * @author Jesus
 * @version 1.0
 */
@Getter
@Setter
public class PasswordResetDto {

    /**
     * Correo electrónico del usuario asociado a la cuenta.
     */
    private String email;

    /**
     * Nueva contraseña para la cuenta del usuario.
     */
    private String newPassword;
}
