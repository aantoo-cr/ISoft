package cl.ufro.dci.hackmaster_api.rec_management.repository;

import cl.ufro.dci.hackmaster_api.rec_management.domain.Prize;
import org.springframework.data.jpa.repository.Query;
import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PrizeRepository extends CrudRepository<Prize, Long> {
    List<Prize> findByChallenge(Challenge challenge);

    // Buscar un premio por ID y Challenge
    Prize findPrizeByIdAndChallengeId(Long prizeId, Long challengeId);

    // Obtener todos los premios asociados a un challenge
    Iterable<Prize> findAllByChallengeId(Long challengeId);

    boolean existsByChallengeId(Long challengeId);

    boolean existsByChallengeIdAndPosition(Long challengeId, int position);

    @Query("SELECT MAX(p.position) FROM Prize p WHERE p.challenge.id = :challengeId")
    Integer findMaxPositionByChallengeId(Long challengeId);
}



