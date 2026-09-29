package cl.ufro.dci.hackmaster_api.registration.service;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.repository.AdminRepository;
import cl.ufro.dci.hackmaster_api.registration.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de gestionar las solicitudes de organizaciones, incluyendo
 * su aprobación
 * o rechazo, así como la obtención de solicitudes pendientes.
 */
@Service
@RequiredArgsConstructor
public class AdminService {

  private final OrganizationRepository organizationRepository;
  private final AdminRepository adminRepository;

  /**
   * Obtiene una lista de organizaciones con solicitudes pendientes, aprobadas o
   * rechazadas.
   *
   * @return Una lista de organizaciones con estado PENDING, APPROVED o REJECTED.
   */
  public List<Organization> getPendingRequests() {
    List<Organization.Status> statuses = Arrays.asList(
        Organization.Status.PENDING,
        Organization.Status.APPROVED,
        Organization.Status.REJECTED);
    return adminRepository.findByStatusIn(statuses);
  }

  /**
   * Aprueba una solicitud de organización por su ID.
   *
   * @param organizationId El ID de la organización cuya solicitud se aprobará.
   * @return La organización aprobada.
   * @throws RuntimeException Si no se encuentra la organización con el ID
   *                          proporcionado.
   */
  public Organization approveRequest(Long organizationId) {
    Organization organization = organizationRepository
        .findById(organizationId)
        .orElseThrow(
            () -> new RuntimeException("[AdminService] Organización no encontrada"));

    organization.setStatus(Organization.Status.APPROVED);
    return organizationRepository.save(organization);
  }

  /**
   * Rechaza una solicitud de organización por su ID.
   *
   * @param organizationId El ID de la organización cuya solicitud se rechazará.
   * @return La organización rechazada.
   * @throws RuntimeException Si no se encuentra la organización con el ID
   *                          proporcionado.
   */
  public Organization rejectRequest(Long organizationId) {
    Organization organization = organizationRepository
        .findById(organizationId)
        .orElseThrow(
            () -> new RuntimeException("[AdminService] Organización no encontrada"));

    organization.setStatus(Organization.Status.REJECTED);
    return organizationRepository.save(organization);
  }
}
