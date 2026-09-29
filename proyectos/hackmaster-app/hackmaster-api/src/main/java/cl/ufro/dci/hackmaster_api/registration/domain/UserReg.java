package cl.ufro.dci.hackmaster_api.registration.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Clase abstracta que representa un usuario en el sistema.
 * Esta clase sirve como base para las entidades que representan tipos específicos de usuarios,
 * como {@link Participant} y {@link Organization}.
 *
 * La clase incluye atributos y métodos comunes para gestionar usuarios, como la generación
 * y validación de tokens de acceso, y el manejo de información básica del usuario.
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "user")
public abstract class UserReg implements UserDetails {

  /**
   * Identificador único del usuario.
   * Generado automáticamente mediante la estrategia {@code GenerationType.AUTO}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long id;

  /**
   * Nombre de usuario único y obligatorio.
   */
  @Column(name = "username", unique = true, nullable = false)
  private String username;

  /**
   * Contraseña del usuario.
   * Debe tener al menos 8 caracteres.
   */
  @Column(name = "password", nullable = false)
  @Size(min = 8, message = "[User] Contraseña debe tener al menos 8 carácteres")
  private String password;

  /**
   * Correo electrónico único y obligatorio.
   * Validado mediante la anotación {@link Email}.
   */
  @Column(name = "email", unique = true, nullable = false)
  @Email(message = "[User] Email debe ser válido")
  private String email;

  public UserReg(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public UserReg() {}

  /**
   * Token temporal para la recuperación de contraseña.
   */
  @Column(name = "reset_password_token")
  private String resetPasswordToken;

  /**
   * Fecha de expiración del token de recuperación de contraseña.
   */
  @Column(name = "reset_password_expires")
  private LocalDateTime resetPasswordExpires;

  /**
   * Token de acceso generado para el usuario tras autenticarse.
   * Este token se utiliza para manejar sesiones y accesos.
   */
  private String tokenAcess = null;

  /**
   * Genera un token de acceso basado en el correo electrónico del usuario y la
   * marca de tiempo actual.
   *
   * @param email El correo electrónico del usuario.
   * @return Un token de acceso codificado en Base64.
   */
  public String generateToken(String email) {
    String tokenData = email + ":" + new Date().getTime();
    return Base64.getEncoder().encodeToString(tokenData.getBytes());
  }

  @Override
  public String getUsername() {
      return this.username;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
