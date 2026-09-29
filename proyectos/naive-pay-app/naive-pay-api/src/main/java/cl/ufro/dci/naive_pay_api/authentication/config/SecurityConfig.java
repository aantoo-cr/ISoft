package cl.ufro.dci.naive_pay_api.authentication.config;


import cl.ufro.dci.naive_pay_api.authentication.utils.UserAuthProviderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación. Este archivo define los filtros y políticas de seguridad,
 * como la autenticación JWT, la protección contra CSRF y las políticas de control de acceso para las rutas.
 *
 * @author Jesus
 * @version 1.3
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthProviderUtils userAuthProviderUtils;

    /**
     * Configura las políticas de seguridad para las solicitudes HTTP, incluyendo la configuración del filtro JWT.
     *
     * @param http La instancia de HttpSecurity que se configura.
     * @return SecurityFilterChain La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthFilterConfig(userAuthProviderUtils), BasicAuthenticationFilter.class)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((request) ->
                        request
                                .requestMatchers("/h2/**").permitAll() // Permitir el acceso a la consola H2
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                                .anyRequest().authenticated()  // Descomentar en produccion, utiliza el JWT como bearer token
                                .anyRequest().permitAll()  // Permitir el acceso a todas las rutas, solo en desarrollo
                )
                .headers().frameOptions().disable();
        return http.build();
    }
}
