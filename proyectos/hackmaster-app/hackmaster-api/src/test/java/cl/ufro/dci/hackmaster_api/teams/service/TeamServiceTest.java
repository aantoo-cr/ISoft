package cl.ufro.dci.hackmaster_api.teams.service;

import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import cl.ufro.dci.hackmaster_api.teams.dto.TeamRequestDto;
import cl.ufro.dci.hackmaster_api.teams.repository.TeamRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamValidationService teamValidationService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debe lanzar una excepción si el equipo ya existe")
    void testSaveTeamThrowsExceptionWhenTeamExists() {

        var requestDto = new TeamRequestDto();
        requestDto.setName("Equipo Existente");
        requestDto.setLeaderId(1L);

        doThrow(new EntityExistsException("Team already exists."))
                .when(teamValidationService).validateTeamExists("Equipo Existente");


        assertThrows(EntityExistsException.class, () -> teamService.saveTeam(requestDto));
        verify(teamValidationService, times(1)).validateTeamExists("Equipo Existente");
    }

    @Test
    @DisplayName("Debe guardar un equipo correctamente")
    void testSaveTeamSuccess() {

        var requestDto = new TeamRequestDto();
        requestDto.setName("Nuevo Equipo");
        requestDto.setLeaderId(1L);

        var leader = new Participant();
        leader.setId(1L);
        leader.setName("Líder Ejemplo");

        var team = new Team();
        team.setTeamId(100L);
        team.setTeamName("Nuevo Equipo");
        team.setLeader(leader);

        when(participantService.findById(1L)).thenReturn(Optional.of(leader));
        when(teamRepository.save(any(Team.class))).thenAnswer(invocation -> {
            Team savedTeam = invocation.getArgument(0);
            savedTeam.setTeamId(100L);
            return savedTeam;
        });


        long teamId = teamService.saveTeam(requestDto);


        assertThat(teamId).isEqualTo(100L); // Verificar que el ID es el esperado
        verify(teamValidationService, times(1)).validateTeamExists("Nuevo Equipo");
        verify(participantService, times(1)).findById(1L);
        verify(teamRepository, times(1)).save(any(Team.class));
    }



}
