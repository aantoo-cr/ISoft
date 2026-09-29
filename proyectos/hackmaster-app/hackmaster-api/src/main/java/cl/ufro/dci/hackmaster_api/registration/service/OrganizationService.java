package cl.ufro.dci.hackmaster_api.registration.service;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Servicio encargado de la gestión de organizaciones, incluyendo su registro,
 * actualización,
 * inicio de sesión y verificación por correo electrónico.
 * Implementa la interfaz UserService para las operaciones comunes de usuario.
 */
@Service
@RequiredArgsConstructor
public class OrganizationService implements UserRegService<Organization> {

  private final OrganizationRepository organizationRepository;

  /**
   * Verifica si existe una organización con el correo electrónico proporcionado.
   *
   * @param email El correo electrónico de la organización.
   * @return Un {@link Optional} que contiene la organización si existe, o vacío
   *         si no se encuentra.
   */
  public Optional<Organization> existsByEmail(String email) {
    return organizationRepository.findByEmail(email);
  }

  /**
   * Registra una nueva organización.
   * Si el correo electrónico ya existe, se lanza una excepción.
   *
   * @param organization El objeto de organización a registrar.
   * @return La organización registrada.
   * @throws IllegalArgumentException Si el correo electrónico ya existe.
   */

  public void register(Organization organization) {
    if (existsByEmail(organization.getEmail()).isPresent()) {
      throw new IllegalArgumentException("[OrganizationService] El email ya está registrado");
    }
    organizationRepository.save(organization);
  }

  /**
   * Actualiza los detalles de una organización existente.
   * Si la organización no existe o la contraseña no coincide, no se realiza la
   * actualización.
   *
   * @param organizationDto El objeto de organización con los nuevos detalles.
   * @return Un {@link Optional} que contiene la organización actualizada si se
   *         realizó la actualización,
   *         o vacío si la actualización no fue posible.
   */
  @Override
  public Optional<Organization> update(Organization organizationDto) {
    Optional<Organization> o = this.findById(organizationDto.getId());

    if (o.isEmpty()) {
      return Optional.empty();
    }
    Organization organization = o.get();
    if (!organizationDto.getPassword().equals(organization.getPassword())) {
      return Optional.empty();
    }

    BeanUtils.copyProperties(organizationDto, organization, "id", "password");
    Organization updatedOrganization = organizationRepository.save(organization);
    return Optional.of(updatedOrganization);
  }

  /**
   * Busca una organización por su ID.
   *
   * @param id El ID de la organización a buscar.
   * @return Un {@link Optional} que contiene la organización si se encuentra, o
   *         vacío si no se encuentra.
   */
  @Override
  public Optional<Organization> findById(Long id) {
    return organizationRepository.findById(id);
  }

  /**
   * Realiza el inicio de sesión de una organización con su correo electrónico y
   * contraseña.
   * Si la organización está aprobada y la contraseña es correcta, se genera un
   * token de acceso.
   *
   * @param email    El correo electrónico de la organización.
   * @param password La contraseña de la organización.
   * @return La organización con el token de acceso generado.
   * @throws IllegalArgumentException Si el correo electrónico no existe o la
   *                                  contraseña es incorrecta.
   */

  public Organization login(String email, String password) {
    Optional<Organization> organizationOpt = organizationRepository.findByEmail(email);

    if (organizationOpt.isPresent() && organizationOpt.get().getStatus().equals(Organization.Status.APPROVED)) {
      Organization organization = organizationOpt.get();
      if (organization.getPassword().equals(password)) {
        organization.setTokenAcess(organization.generateToken(email));
        return organization;
      } else {
        throw new IllegalArgumentException("[OrganizationService] Contraseña incorrecta");
      }
    } else {
      throw new IllegalArgumentException("[OrganizationService] El Email no existe");
    }
  }
}
