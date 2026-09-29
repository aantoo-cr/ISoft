package cl.ufro.dci.naive_pay_api.authentication.utils;


import cl.ufro.dci.naive_pay_api.authentication.dto.UserDto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

/**
 * Proveedor de autenticación de usuarios mediante tokens JWT. Esta clase se encarga de crear y validar tokens JWT
 * para autenticar a los usuarios en la aplicación.
 *
 * @author 00jesus
 * @version 1.1
 */
@RequiredArgsConstructor
@Component
public class UserAuthProviderUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Inicializa la clave secreta para el algoritmo de firma JWT. Esta clave se obtiene de las propiedades de configuración.
     */
    @PostConstruct
    protected void init () {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Crea un token JWT para un usuario.
     *
     * @param user El objeto que contiene los datos del usuario.
     * @return String El token JWT generado.
     */
    public String createToken (UserDto user, int minutes) {
        Date now = new Date();
        // en esta seccion pueden ajustar la duracion del token
        Date validity = new Date(now.getTime() + 1000L * 60 * minutes); // 5 minutos

        String token = JWT.create()
                .withIssuer(user.getRut())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("email", user.getEmail())
                .withClaim("firstName", user.getFirstName())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC256(secretKey));
        System.out.println(token);
        return token;

    }

    /**
     * Valida un token JWT. Si el token es válido, extrae los detalles del usuario y los convierte en una autenticación.
     *
     * @param token El token JWT a validar.
     * @return Authentication Un objeto de autenticación con los detalles del usuario.
     * @throws RuntimeException Si el token es inválido o ha expirado.
     */
    public Authentication validateToken (String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);

        UserDto user = UserDto.builder()
                .rut(decoded.getIssuer())
                .email(decoded.getClaim("email").asString())
                .firstName(decoded.getClaim("firstName").asString())
                .role(decoded.getClaim("role").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }


}
