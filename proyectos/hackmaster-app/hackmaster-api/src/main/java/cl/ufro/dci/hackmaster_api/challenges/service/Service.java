package cl.ufro.dci.hackmaster_api.challenges.service;


import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.challenges.dto.ChallengesDTO;

import java.util.List;
import java.util.Optional;

public interface Service {

    Challenge inscribirChallenge(ChallengesDTO challengeDTO);

    List<Challenge> listarChallenges();

    Optional<Challenge> viewChallenge(Long id);

    Optional<Challenge> publicarChallenge(Long id);

    Optional<Challenge> editarChallenge(Long id, ChallengesDTO challengeDTO);

    boolean eliminarChallenge(Long id);
}