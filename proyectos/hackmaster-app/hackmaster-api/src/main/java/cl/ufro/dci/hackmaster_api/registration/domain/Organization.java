package cl.ufro.dci.hackmaster_api.registration.domain;

import cl.ufro.dci.hackmaster_api.registration.domain.validation.ValidRut;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa una organización registrada en el sistema.
 * Extiende la clase base {@link UserReg} para incluir propiedades específicas de
 * organizaciones.
 * La clase incluye validaciones y reglas de negocio relacionadas con la
 * información de una organización,
 * como su RUT, razón social, y datos del representante.
 *
 */
@Entity
@Data
@Table(name = "Organization")
@EqualsAndHashCode(callSuper = true)
public class Organization extends UserReg  {

  /**
   * Razón social de la organización.
   * Debe contener solo letras y ser única en la base de datos.
   */
  @Column(name = "socialNameOrganization", unique = true, nullable = false)
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "[Organization] Razón social de la organización debe contener solo letras")
  private String socialNameOrganization;

  /**
   * RUT único de la organización.
   * Validado con un patrón y una lógica adicional mediante la anotación
   * {@link ValidRut}.
   */
  @Column(name = "rutOrganization", unique = true, nullable = false)
  @Pattern(regexp = "^[0-9]+-[0-9Kk]$", message = "[Organization] Rut inválido")
  @ValidRut
  private String rutOrganization;

  /**
   * RUT único del representante de la organización.
   * Validado con un patrón y una lógica adicional mediante la anotación
   * {@link ValidRut}.
   */
  @Column(name = "rutRepresentative", unique = true, nullable = false)
  @ValidRut
  private String rutRepresentative;

  /**
   * Nombre del representante de la organización.
   * Este campo es obligatorio.
   */
  @Column(name = "representativeName", nullable = false)
  private String representativeName;

  /**
   * Estado actual de la organización.
   * Representado por la enumeración {@link Status}, con valores posibles:
   * - {@code PENDING}: Solicitud pendiente de aprobación.
   * - {@code APPROVED}: Solicitud aprobada.
   * - {@code REJECTED}: Solicitud rechazada.
   */
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;

  public Organization(String username, String email, String password,
      String socialNameOrganization, String rutOrganization, String rutRepresentative,
      String representativeName, Status status) {
    super(username, email, password);
    this.socialNameOrganization = socialNameOrganization;
    this.rutOrganization = rutOrganization;
    this.rutRepresentative = rutRepresentative;
    this.representativeName = representativeName;
    this.status = status;
  }

  public Organization() {
  }

  /**
   * Enumeración que define los estados posibles de una organización en el
   * sistema.
   */
  public enum Status {
    PENDING, APPROVED, REJECTED;
  }
}
