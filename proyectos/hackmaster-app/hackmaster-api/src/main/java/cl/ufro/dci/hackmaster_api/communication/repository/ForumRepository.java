package cl.ufro.dci.hackmaster_api.communication.repository;

import cl.ufro.dci.hackmaster_api.communication.domain.ForumTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumRepository extends CrudRepository<ForumTopic, Long> {
    // Método para encontrar un tema de foro por su slug
    Optional<ForumTopic> findBySlug(String slug);
}
