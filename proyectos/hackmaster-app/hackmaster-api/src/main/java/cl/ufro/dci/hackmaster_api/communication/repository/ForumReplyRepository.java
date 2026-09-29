package cl.ufro.dci.hackmaster_api.communication.repository;

import cl.ufro.dci.hackmaster_api.communication.domain.ForumReply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumReplyRepository extends CrudRepository<ForumReply, Long> {
}
