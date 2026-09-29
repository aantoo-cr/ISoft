package cl.ufro.dci.hackmaster_api.rec_management.service;

import cl.ufro.dci.hackmaster_api.rec_management.domain.Prize;
import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.rec_management.repository.PrizeRepository;
import cl.ufro.dci.hackmaster_api.challenges.repository.ChallengeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PrizeServiceTest {

    @Mock
    private PrizeRepository prizeRepository;

    @Mock
    private ChallengeRepository challengeRepository;

    @InjectMocks
    private PrizeService prizeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para CU-01-CP-01: Validar que se recuperan todos los premios
    @Test
    @DisplayName("Validar que se recuperan todos los premios")
    public void testAllPrizesFound() {
        Prize firstPrize = new Prize();
        firstPrize.setName("Premio Uno");
        firstPrize.setPosition(1);
        firstPrize.setDescription("Descripción del Premio Uno");

        Prize secondPrize = new Prize();
        secondPrize.setName("Premio Dos");
        secondPrize.setPosition(2);
        secondPrize.setDescription("Descripción del Premio Dos");

        List<Prize> prizes = new ArrayList<>();
        prizes.add(firstPrize);
        prizes.add(secondPrize);

        doReturn(prizes).when(prizeRepository).findAllByChallengeId(any(Long.class));

        List<Prize> result = (List<Prize>) prizeService.deployAllPrizes(1L);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Premio Uno");
        assertThat(result.get(0).getDescription()).isEqualTo("Descripción del Premio Uno");
        assertThat(result.get(1).getName()).isEqualTo("Premio Dos");
        assertThat(result.get(1).getDescription()).isEqualTo("Descripción del Premio Dos");
    }

    // Test para CU-01-CP-02: Verificar que se devuelve una lista vacía cuando no hay premios
    @Test
    @DisplayName("Verificar que se devuelve una lista vacía")
    public void testNoPrizesFound() {
        doReturn(new ArrayList<>()).when(prizeRepository).findAllByChallengeId(any(Long.class));

        List<Prize> result = (List<Prize>) prizeService.deployAllPrizes(1L);

        assertThat(result).isEmpty();
    }

    // Test para CU-03-CP-01: Verificar que un premio se crea correctamente
    @Test
    @DisplayName("Verificar que un premio se crea correctamente")
    public void testAssignPrize() {
        Challenge challenge = new Challenge();
        challenge.setId(1L);

        Prize newPrize = new Prize();
        newPrize.setName("Premio Nuevo");
        newPrize.setDescription("Descripción del premio nuevo");
        newPrize.setPosition(1);

        doReturn(Optional.of(challenge)).when(challengeRepository).findById(1L);
        doReturn(false).when(prizeRepository).existsByChallengeIdAndPosition(1L, 1);
        doReturn(newPrize).when(prizeRepository).save(any(Prize.class));

        Prize result = prizeService.assignPrize(1L, newPrize);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Premio Nuevo");
        assertThat(result.getDescription()).isEqualTo("Descripción del premio nuevo");
        assertThat(result.getPosition()).isEqualTo(1);

        verify(prizeRepository).save(newPrize);
    }

    // Test para CU-03-CP-02: Crear premio sin nombre y verificar error
    @Test
    @DisplayName("Crear premio sin nombre y verificar error")
    public void testAssignPrizeWithoutName() {
        Challenge challenge = new Challenge();
        challenge.setId(1L);

        Prize newPrize = new Prize();
        newPrize.setDescription("Descripción sin nombre");
        newPrize.setPosition(1);

        doReturn(Optional.of(challenge)).when(challengeRepository).findById(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            prizeService.assignPrize(1L, newPrize);
        });

        assertThat(exception.getMessage()).isEqualTo("El nombre del premio no puede ser nulo o vacío");
    }
}
