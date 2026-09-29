package cl.ufro.dci.hackmaster_api.challenges.repository;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ChallengeRepository extends CrudRepository<Challenge, Long> {
    List<Challenge> findByOrganization(Organization organization);
}
