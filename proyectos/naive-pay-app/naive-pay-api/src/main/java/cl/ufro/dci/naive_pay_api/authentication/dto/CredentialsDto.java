package cl.ufro.dci.naive_pay_api.authentication.dto;

/**
 * DTO que representa las credenciales de un usuario para autenticación.
 * Contiene el correo electrónico y la contraseña del usuario.
 *
 * @author 00jesus
 * @version 1.0
 */
public record CredentialsDto (String email, char[] password) {

}
