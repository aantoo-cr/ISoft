package cl.ufro.dci.hackmaster_api.communication.repository;

import cl.ufro.dci.hackmaster_api.communication.domain.Chat;
import cl.ufro.dci.hackmaster_api.communication.domain.Message;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar las operaciones CRUD de los mensajes.
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    /**
     * Encuentra los mensajes de un chat específico.
     *
     * @param chat El chat del que se obtendrán los mensajes.
     * @return Lista de mensajes del chat.
     */
    List<Message> findByChat(Chat chat);

}
