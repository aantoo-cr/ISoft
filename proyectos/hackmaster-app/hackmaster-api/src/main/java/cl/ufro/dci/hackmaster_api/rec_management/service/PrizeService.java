package cl.ufro.dci.hackmaster_api.rec_management.service;

import cl.ufro.dci.hackmaster_api.rec_management.domain.Prize;
import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.rec_management.repository.PrizeRepository;
import cl.ufro.dci.hackmaster_api.challenges.repository.ChallengeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class PrizeService {

    private static final Logger LOGGER = LogManager.getLogger(PrizeService.class);

    private final PrizeRepository prizeRepository;

    private final ChallengeRepository challengeRepository;

    public PrizeService(PrizeRepository prizeRepository, ChallengeRepository challengeRepository) {
        this.prizeRepository = prizeRepository;
        this.challengeRepository = challengeRepository;
    }

    public Prize assignPrize(Long challengeId, Prize prize) {
        LOGGER.info("Guardando premio para challenge ID {}: {}", challengeId, prize.getName());

        if (prize.getName() == null || prize.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del premio no puede ser nulo o vacío");
        }

        Challenge challenge = challengeRepository.findById(challengeId).orElse(null);
        if (challenge == null) {
            LOGGER.error("Challenge con ID {} no encontrado", challengeId);
            return null;
        }

        // Calcular la próxima posición disponible
        Integer maxPosition = prizeRepository.findMaxPositionByChallengeId(challengeId);
        int nextPosition = (maxPosition == null) ? 1 : maxPosition + 1;

        // Asignar la posición calculada y el desafío
        prize.setChallenge(challenge);
        prize.setPosition(nextPosition);

        return prizeRepository.save(prize);
    }


    public Prize updatePrize(Long challengeId, Long prizeId, Prize prizeDetails) {
        LOGGER.info("Actualizando premio con ID: {} para challenge ID: {}", prizeId, challengeId);
        Prize existingPrize = prizeRepository.findPrizeByIdAndChallengeId(prizeId, challengeId);

        if (existingPrize == null) {
            LOGGER.error("Premio con ID {} no encontrado para challenge ID {}", prizeId, challengeId);
            return null;
        }

        existingPrize.setName(prizeDetails.getName());
        existingPrize.setPosition(prizeDetails.getPosition());
        existingPrize.setDescription(prizeDetails.getDescription());

        return prizeRepository.save(existingPrize);
    }

    public Prize getPrizeById(Long challengeId, Long prizeId) {
        LOGGER.info("Buscando premio con ID: {} para challenge ID: {}", prizeId, challengeId);
        return prizeRepository.findPrizeByIdAndChallengeId(prizeId, challengeId);
    }

    public void deletePrize(Long challengeId, Long prizeId) {
        LOGGER.info("Eliminando premio con ID: {} para challenge ID: {}", prizeId, challengeId);
        Prize existingPrize = prizeRepository.findPrizeByIdAndChallengeId(prizeId, challengeId);

        if (existingPrize != null) {
            prizeRepository.delete(existingPrize);
        } else {
            LOGGER.error("Premio con ID {} no encontrado para challenge ID {}", prizeId, challengeId);
        }
    }

    public Iterable<Prize> deployAllPrizes(Long challengeId) {
        return prizeRepository.findAllByChallengeId(challengeId);
    }

    public boolean hasPrizes(Long challengeId) {
        return prizeRepository.existsByChallengeId(challengeId);
    }
}





