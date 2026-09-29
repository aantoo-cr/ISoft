package cl.ufro.dci.hackmaster_api.rec_management.repository;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.challenges.repository.ChallengeRepository;
import cl.ufro.dci.hackmaster_api.rec_management.domain.Prize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class PrizeRepositoryTest {

    @Autowired
    private PrizeRepository prizeRepository;
    @Autowired
    private ChallengeRepository challengeRepository;

    @Test
    public  void guardarPremio(){
        Prize p1 = new Prize();
        p1.setName("Premio");
        p1.setDescription("d");
        p1.setPosition(1);
        Challenge c = new Challenge();

        c.setEstado(true);
        c.setDescripcion("d");
        c.setNombre("n");
        c.setFechaT(LocalDate.of(2024, 10, 24));
        c.setFechaI(LocalDate.of(2024, 10, 24));
        c.setRequisitos_Participantes("r");
        c.setCuposParticipantes(3);
        challengeRepository.save(c);
        p1.setChallenge(c);

        Prize save = prizeRepository.save(p1);
        System.out.println(save);

    }
}