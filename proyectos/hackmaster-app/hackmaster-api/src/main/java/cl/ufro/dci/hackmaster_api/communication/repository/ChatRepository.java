package cl.ufro.dci.hackmaster_api.communication.repository;

import cl.ufro.dci.hackmaster_api.communication.domain.Chat;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
//import cl.ufro.dci.hackmaster_api.registration.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar las operaciones CRUD de los chats.
 */
@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {

    /**
     * Encuentra los chats de un usuario específico.
     *
     * @param user1 El usuario del que se obtendrán los chats.
     * @return Lista de chats del usuario.
     */
    List<Chat> findByUsers(Optional<Participant> user1);
}

