package cl.ufro.dci.hackmaster_api.tracking.repository;

import cl.ufro.dci.hackmaster_api.tracking.domain.Avance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvanceRepository extends JpaRepository<Avance, Integer> {
}

