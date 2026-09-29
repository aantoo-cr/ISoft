package cl.ufro.dci.hackmaster_api.registration.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {

  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  /**
   * Genera un token JWT basado en el email del usuario.
   *
   * @param email El correo electrónico del usuario.
   * @return El token JWT generado.
   */
  public String generateToken(String email) {
    return Jwts
        .builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 3600000))
        .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Obtiene el email del usuario a partir del token JWT.
   *
   * @param token El token JWT.
   * @return El email del usuario.
   */
  public String getEmailFromToken(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  /*
   * Valida si el token es válido y no está expirado.
   *
   * @param token El token JWT.
   * 
   * @return true si el token es válido, de lo contrario false.
   */
  public boolean validateToken(String token) {
    try {
      Jwts
          .parserBuilder()
          .setSigningKey(SECRET_KEY)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
