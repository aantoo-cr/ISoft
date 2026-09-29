package cl.ufro.dci.naive_pay_api.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para gestionar las propiedades relacionadas con la aplicación.
 * En este caso, proporciona la URL base del frontend utilizada para generar links,
 * como los de restablecimiento de contraseña.
 *
 * Actualizaciones:
 * - Se agregó la inyección de la propiedad `frontend.base-url` desde el archivo de configuración.
 *
 * Uso:
 * - La URL base se puede configurar en el archivo `application.properties` o `application.yml` bajo la clave `frontend.base-url`.
 * - Esta configuración es útil para construir URLs dinámicas que dependen del entorno (desarrollo, producción, etc.).
 *
 * Ejemplo de configuración:
 * ```properties
 * frontend.base-url=http://localhost:4200
 * ```
 *
 * @author Marcelo
 */
@Configuration
public class AppConfig {

    /**
     * URL base del frontend.
     * Esta propiedad se inyecta desde el archivo de configuración.
     */
    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    /**
     * Obtiene la URL base configurada para el frontend.
     *
     * @return La URL base del frontend como cadena de texto.
     */
    public String getFrontendBaseUrl() {
        return frontendBaseUrl;
    }
}
