package cl.ufro.dci.naive_pay_api.authentication.config;

import cl.ufro.dci.naive_pay_api.authentication.config.AppConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * Configuración CORS (Cross-Origin Resource Sharing) para permitir solicitudes desde el frontend a la API.
 * La URL base del frontend se obtiene dinámicamente desde AppConfig.
 *
 * Actualización:
 * - Se eliminó la URL codificada para usar la configuración de AppConfig.
 *
 * @author Jesus
 * @version 1.3
 */
@Configuration
@EnableWebMvc
public class WebConfig {

    private final AppConfig appConfig;

    /**
     * Constructor para inyectar AppConfig.
     *
     * @param appConfig Configuración de la aplicación que contiene la URL base del frontend.
     */
    public WebConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * Configura el filtro CORS para permitir solicitudes de dominios específicos.
     *
     * @return FilterRegistrationBean El filtro de CORS registrado en el contexto de la aplicación.
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(appConfig.getFrontendBaseUrl()); // Se usa la URL del frontend configurada
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()
        ));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
        bean.setOrder(-100);
        return bean;
    }
}