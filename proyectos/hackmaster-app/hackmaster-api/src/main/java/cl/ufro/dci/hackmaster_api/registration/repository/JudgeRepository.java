package cl.ufro.dci.hackmaster_api.registration.repository;

import cl.ufro.dci.hackmaster_api.registration.domain.Judge;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Transactional
public interface JudgeRepository extends UserRepository<Judge> {
    Optional<Judge> findByEmail(String email);

}