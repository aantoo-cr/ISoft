package cl.ufro.dci.naive_pay_api.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Configuración de codificación de contraseñas para la aplicación.
 * Proporciona un bean de codificador de contraseñas basado en BCrypt.
 *
 * @author Jesus
 * @version 1.0
 */
@Component
public class PasswordConfig {

    /**
     * Proporciona un bean de tipo PasswordEncoder.
     * Utiliza BCrypt para codificar contraseñas de forma segura.
     *
     * @return Un codificador de contraseñas BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
