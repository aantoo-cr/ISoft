package cl.ufro.dci.hackmaster_api.registration.repository;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface OrganizationRepository extends UserRepository<Organization> {
    List<Organization> findByStatus(Organization.Status status);
    Optional<Organization> findByEmail(String email);


}
