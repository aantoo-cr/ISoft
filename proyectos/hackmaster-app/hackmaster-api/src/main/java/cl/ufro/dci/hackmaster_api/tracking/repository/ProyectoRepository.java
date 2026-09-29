package cl.ufro.dci.hackmaster_api.tracking.repository;

import cl.ufro.dci.hackmaster_api.tracking.domain.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
}
