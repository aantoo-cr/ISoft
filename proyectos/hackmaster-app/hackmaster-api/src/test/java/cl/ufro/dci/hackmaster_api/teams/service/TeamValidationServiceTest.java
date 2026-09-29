package cl.ufro.dci.hackmaster_api.teams.service;

import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import cl.ufro.dci.hackmaster_api.teams.repository.TeamRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

class TeamValidationServiceTest {

    @Mock
    private TeamRepository teamRepository;

    private TeamValidationService teamValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teamValidationService = new TeamValidationService(teamRepository);
    }

    @Test
    @DisplayName("Debe lanzar excepción si el equipo ya existe")
    void testValidateTeamExistsThrowsException() {

        when(teamRepository.existsByTeamName("Equipo Existente")).thenReturn(true);


        assertThatExceptionOfType(EntityExistsException.class)
                .isThrownBy(() -> teamValidationService.validateTeamExists("Equipo Existente"))
                .withMessage("Team with name Equipo Existente already exists.");
    }

    @Test
    @DisplayName("Debe lanzar excepción si no hay equipos en la lista")
    void testValidateTeamsThrowsException() {

        List<Team> emptyTeams = Collections.emptyList();


        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> teamValidationService.validateTeams(emptyTeams))
                .withMessage("Not found teams in database.");
    }

    @Test
    @DisplayName("No debe lanzar excepción si el equipo no existe")
    void testValidateTeamExistsSuccess() {

        when(teamRepository.existsByTeamName("Equipo Nuevo")).thenReturn(false);


        teamValidationService.validateTeamExists("Equipo Nuevo");
    }
}
