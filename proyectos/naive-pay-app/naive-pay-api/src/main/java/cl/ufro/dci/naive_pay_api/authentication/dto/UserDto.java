package cl.ufro.dci.naive_pay_api.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la información básica de un usuario.
 * Este objeto se utiliza para transferir los detalles de un usuario autenticado, como el nombre, RUT y el token JWT.
 *
 * @author 00jesus
 * @version 1.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

        private String firstName; // Nombre del usuario
        private String rut; // RUT del usuario
        private String email; // Correo electrónico del usuario
        // private String webKey; // Clave web hasheada para autenticación, esta se ignora en el mapper, no se envia
        private String token; // Token JWT para autenticación
        private String role; // Rol del usuario

}
