package cl.ufro.dci.hackmaster_api.challenges.service;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.challenges.dto.ChallengesDTO;
import cl.ufro.dci.hackmaster_api.challenges.repository.ChallengeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceImplementsTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @InjectMocks
    private ServiceImplements serviceImplements;

    private ChallengesDTO challengeDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        challengeDTO = new ChallengesDTO();
        challengeDTO.setNombre("Desafío 1");
        challengeDTO.setDescripcion("Descripción de prueba");
        challengeDTO.setRequisitos_Participantes("Requisito 1");
        challengeDTO.setFechaI(LocalDate.of(2024, 10, 24));
        challengeDTO.setFechaT(LocalDate.of(2024, 10, 24));
        challengeDTO.setCuposParticipantes(10);
    }

    @Test
    public void testInscribirChallenge() {
        when(challengeRepository.save(any(Challenge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Challenge challenge = serviceImplements.inscribirChallenge(challengeDTO);
        assertNotNull(challenge);
        assertEquals("Desafío 1", challenge.getNombre());
        assertEquals(10, challenge.getCuposParticipantes());

        verify(challengeRepository, times(1)).save(any(Challenge.class));
    }

    @Test
    public void testListarChallenges() {
        when(challengeRepository.findAll()).thenReturn(List.of(new Challenge()));

        List<Challenge> challenges = serviceImplements.listarChallenges();
        assertFalse(challenges.isEmpty());

        verify(challengeRepository, times(1)).findAll();
    }

    @Test
    public void testViewChallenge() {
        when(challengeRepository.findById(1L)).thenReturn(Optional.of(new Challenge()));

        Optional<Challenge> challenge = serviceImplements.viewChallenge(1L);
        assertTrue(challenge.isPresent());

        verify(challengeRepository, times(1)).findById(1L);
    }

    @Test
    public void testPublicarChallenge() {
        Challenge challenge = new Challenge();
        challenge.setEstado(false);

        when(challengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

        Optional<Challenge> publicado = serviceImplements.publicarChallenge(1L);
        assertTrue(publicado.isPresent());
        assertTrue(publicado.get().isEstado());

        verify(challengeRepository, times(1)).save(challenge);
    }

    @Test
    public void testEliminarChallenge() {
        when(challengeRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = serviceImplements.eliminarChallenge(1L);
        assertTrue(eliminado);

        verify(challengeRepository, times(1)).deleteById(1L);
    }
}