package cl.ufro.dci.naive_pay_api.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) que representa una solicitud de inicio de sesión (login).
 *
 * Este objeto contiene los datos necesarios para autenticar a un usuario, es decir, el correo electrónico
 * y la contraseña del usuario.
 * @author 00jesus
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * El correo electrónico del usuario.
     *
     * Este campo es utilizado para identificar al usuario durante el proceso de inicio de sesión.
     *
     */
    private String email;

    /**
     * La contraseña del usuario.
     *
     * Este campo contiene la contraseña que el usuario proporciona para autenticarse.
     *
     */
    private String password;

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El correo electrónico a establecer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La contraseña a establecer.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
