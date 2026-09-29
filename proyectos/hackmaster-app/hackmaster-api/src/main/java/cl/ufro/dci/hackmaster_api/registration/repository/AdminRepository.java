package cl.ufro.dci.hackmaster_api.registration.repository;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends UserRepository<Organization> {
  List<Organization> findByStatusIn(List<Organization.Status> statuses);
}
