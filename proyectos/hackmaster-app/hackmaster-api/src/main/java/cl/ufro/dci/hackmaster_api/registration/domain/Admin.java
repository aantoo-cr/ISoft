package cl.ufro.dci.hackmaster_api.registration.domain;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import jakarta.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un administrador del sistema.
 * Extiende la clase base {@link UserReg} y agrega funcionalidades específicas de
 * los administradores.
 *
 * <p>
 * Los administradores tienen la capacidad de gestionar solicitudes de
 * organizaciones.
 * </p>
 */
@Entity
@Data
@Table(name = "Admin")
@EqualsAndHashCode(callSuper = true)
public class Admin extends UserReg {
  public Admin(String username, String email, String password) {
    super(username, email, password);
  }


  /**
   * Lista de solicitudes de organizaciones que el administrador puede gestionar.
   *
   * <p>
   * Se utiliza la anotación {@code @ElementCollection} para mapear la colección
   * como un conjunto de elementos embebidos en la base de datos.
   * </p>
   */
  @ElementCollection
  private List<Organization> requests = new ArrayList<>();

  public Admin() {

  }

  /**
   * Agrega una solicitud de organización a la lista de solicitudes del
   * administrador.
   *
   * @param request la solicitud de organización a agregar.
   */
  public void addRequest(Organization request) {
    requests.add(request);
  }

  /**
   * Obtiene la lista de solicitudes de organizaciones asignadas al administrador.
   *
   * @return la lista de solicitudes de organizaciones.
   */
  public List<Organization> getRequests() {
    return requests;
  }
}
