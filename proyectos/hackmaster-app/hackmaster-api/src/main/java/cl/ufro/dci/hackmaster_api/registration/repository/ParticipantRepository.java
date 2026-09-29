
package cl.ufro.dci.hackmaster_api.registration.repository;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Transactional
public interface ParticipantRepository extends UserRepository<Participant> {
    Optional<Participant> findByEmail(String email);
}