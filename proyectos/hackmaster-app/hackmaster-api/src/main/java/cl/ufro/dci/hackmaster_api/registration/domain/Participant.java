package cl.ufro.dci.hackmaster_api.registration.domain;

import cl.ufro.dci.hackmaster_api.registration.domain.validation.ValidRut;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa un participante registrado en el sistema.
 * Extiende la clase base {@link UserReg} para incluir propiedades específicas de los participantes.
 *
 * La clase incluye validaciones y restricciones relacionadas con la información personal del participante,
 * como el RUT, nombre, apellido, ciudad, país y número de teléfono.
 */
@Data
@Entity
@Table(name = "Participant")
@EqualsAndHashCode(callSuper = true)
public class Participant extends UserReg {

  /**
   * RUT único del participante.
   * Validado con un patrón específico y mediante la anotación {@link ValidRut}.
   */
  @Column(name = "rut", unique = true, nullable = false)
  @ValidRut(message = "Rut inválido")
  private String rut;

  /**
   * Nombre del participante.
   * Debe contener solo letras y es obligatorio.
   */
  @Column(name = "name", nullable = false)
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "[Participant] Nombre debe contener solo letras")
  private String name;

  /**
   * Apellido del participante.
   * Debe contener solo letras y es obligatorio.
   */
  @Column(name = "lastname", nullable = false)
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "[Participant] Apellido debe contener solo letras")
  private String lastname;

  /**
   * Ciudad de residencia del participante.
   * Este campo es opcional.
   */
  @Column(name = "city")
  private String city;

  /**
   * País de residencia del participante.
   * Este campo es opcional.
   */
  @Column(name = "country")
  private String country;

  /**
   * Número de teléfono único del participante.
   * Debe contener solo números y tener una longitud entre 9 y 11 caracteres.
   */
  @Column(name = "phoneNumber", unique = true)
  @Pattern(regexp = "^[0-9]+$", message = "[Participant] Número de teléfono debe contener solo números")
  @Size(min = 9, max = 11, message = "[Participant] Número de teléfono debe estar entre 9 y 11 carácteres")
  private String phoneNumber;


  public Participant(String username, String email, String password, String rut, String name, String lastname,
      String city, String country, String phoneNumber) {
    super(username, email, password);
    this.rut = rut;
    this.name = name;
    this.lastname = lastname;
    this.city = city;
    this.country = country;
    this.phoneNumber = phoneNumber;
  }

  public Participant() {
  }

}
