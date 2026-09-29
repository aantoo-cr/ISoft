package cl.ufro.dci.hackmaster_api.teams.service;

import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import cl.ufro.dci.hackmaster_api.teams.repository.TeamRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de validar las operaciones relacionadas con los equipos.
 * Este servicio proporciona métodos para verificar si un equipo ya existe,
 * si el rol del usuario es el adecuado, y si existen equipos en la base de datos.
 */

@Service
public class TeamValidationService {

    private final TeamRepository teamRepository;

    /**
     * Constructor de la clase {@code TeamValidationService}.
     *
     * @param teamRepository el repositorio de equipos que se utilizará para realizar las validaciones
     */

    public TeamValidationService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Valida si un equipo con el nombre dado ya existe.
     *
     * @param teamName el nombre del equipo a verificar si ya existe
     * @throws EntityExistsException si ya existe un equipo con el nombre especificado
     */

    public void validateTeamExists(String teamName) {
        if (teamRepository.existsByTeamName(teamName)) {
            throw new EntityExistsException("Team with name " + teamName + " already exists.");
        }
    }

    /**
     * Valida que la lista de equipos no esté vacía.
     *
     * @param teams la lista de equipos a verificar
     * @throws EntityNotFoundException si no se encuentran equipos en la base de datos
     */

    public void validateTeams(List<Team> teams) {
        if (teams.isEmpty()) {
            throw new EntityNotFoundException("Not found teams in database.");
        }
    }
}
