package cl.ufro.dci.hackmaster_api.teams.service;

import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import cl.ufro.dci.hackmaster_api.teams.domain.*;
import cl.ufro.dci.hackmaster_api.teams.dto.MemberInfoDto;
import cl.ufro.dci.hackmaster_api.teams.dto.TeamInfoDto;
import cl.ufro.dci.hackmaster_api.teams.dto.TeamPreviewDto;
import cl.ufro.dci.hackmaster_api.teams.dto.TeamRequestDto;
import cl.ufro.dci.hackmaster_api.teams.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cl.ufro.dci.hackmaster_api.teams.dto.MemberInfoDto.toMember;

/**
 * Servicio encargado de gestionar los equipos en la aplicación, proporcionando métodos
 * para la creación, búsqueda, actualización y gestión de equipos y sus miembros.
 */

@Service
public class TeamService {
    private final ParticipantService participantService;
    private final TeamRepository teamRepository;
    private final TeamValidationService teamValidationService;

    /**
     * Constructor para inicializar el servicio de equipo con las dependencias necesarias.
     *
     * @param teamRepository Repositorio de equipos.
     * @param teamValidationService Servicio para la validación de los equipos.
     * @param participantService Servicio de participantes, usado a la hora de crear un equipo.
     */

    public TeamService(ParticipantService participantService,
                       TeamRepository teamRepository,
                       TeamValidationService teamValidationService) {
        this.participantService = participantService;
        this.teamRepository = teamRepository;
        this.teamValidationService = teamValidationService;
    }

    /**
     * Guarda un equipo a partir de la información proporcionada en la solicitud.
     *
     * @param request La información del equipo a crear.
     * @return El ID del equipo recién creado.
     */

    public long saveTeam(TeamRequestDto request) {
        teamValidationService.validateTeamExists(request.getName());
        var team = createTeamFromRequestDto(request);
        teamRepository.save(team);
        return team.getTeamId();
    }

    /**
     * Encuentra y devuelve la información detallada de un equipo por su ID.
     *
     * @param teamId El ID del equipo.
     * @return La información detallada del equipo como un DTO.
     */

    public TeamInfoDto findTeamInfoById(Long teamId) {
        var team = findTeamById(teamId);
        return TeamInfoDto.fromTeam(team);
    }

    /**
     * Encuentra los equipos a los que pertenece un usuario y devuelve un listado de vistas previas de esos equipos.
     *
     * @param userId El ID del usuario.
     * @return Un listado de vistas previas de los equipos del usuario.
     */

    public List<TeamPreviewDto> findByUserId(Long userId) {
        var teams = teamRepository.findByUserId(userId);
        return findTeamsPreview(teams);
    }

    /**
     * Encuentra un equipo por su ID.
     *
     * @param teamId El ID del equipo.
     * @return El equipo correspondiente al ID.
     * @throws EntityNotFoundException Si no se encuentra el equipo.
     */

    public Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Not found team in database."));
    }

    /**
     * Encuentra las vistas previas de equipos ordenadas por su fecha de creación.
     *
     * @return Un listado de las vistas previas de equipos.
     */

    public List<TeamPreviewDto> findTeamPreviews() {
        var teams = teamRepository.findTeamsOrderByCreationDateAsc();
        return findTeamsPreview(teams);
    }

    /**
     * Encuentra equipos cuyo nombre contiene la palabra clave dada.
     *
     * @param keyword La palabra clave que debe aparecer en cualquier parte del nombre del equipo.
     * @return Un listado de vistas previas de equipos cuyo nombre contiene la palabra clave.
     */
    public List<TeamPreviewDto> findByNameContaining(String keyword) {
        var teams = teamRepository.findTeamsByKeyword(keyword);
        return findTeamsPreview(teams);
    }

    /**
     * Encuentra equipos a los que pertenece un usuario y cuyo nombre contiene la palabra clave dada.
     *
     * @param userId El ID del usuario.
     * @param keyword La palabra clave que debe aparecer en cualquier parte del nombre del equipo.
     * @return Un listado de vistas previas de los equipos del usuario cuyo nombre contiene la palabra clave.
     */
    public List<TeamPreviewDto> findByUserIdAndNameContaining(Long userId, String keyword) {
        var teams = teamRepository.findByUserIdAndKeyword(userId, keyword);
        return findTeamsPreview(teams);
    }

    /**
     * Añade un miembro a un equipo.
     *
     * @param teamId El ID del equipo al que el usuario será añadido.
     */

        public void addMember(Long teamId, MemberInfoDto memberInfo) {
        var team = findTeamById(teamId);
        validateNotFullMembers(team);
        var currentMembers = team.getMembers();
        var updatedMembers = Stream.concat(currentMembers.stream(), Stream.of(toMember(memberInfo)))
                .collect(Collectors.toList());
        team.setMembers(updatedMembers);
        teamRepository.save(team);
    }

    /**
     * Crea un nuevo equipo a partir de los datos proporcionados en el DTO de solicitud.
     *
     * @param request El DTO con la información del nuevo equipo.
     * @return El nuevo equipo creado.
     */

    private Team createTeamFromRequestDto(TeamRequestDto request) {
        var newTeam = new Team();
        var leader = participantService.findById(request.getLeaderId()).orElseThrow(() -> new EntityNotFoundException("Not found leader in database."));;

        newTeam.setTeamName(request.getName());
        newTeam.setLeader(leader);
        newTeam.setCreationDate(LocalDate.now());
        return newTeam;
    }

    /**
     * Transforma una lista de equipos en vistas previas de equipo, con la opción de incluir información adicional
     * sobre el equipo si el rol del usuario es `ADMINISTRATOR`.
     *
     * @param teams La lista de equipos que se transformarán en vistas previas.
     * @return Una lista de objetos {@link TeamPreviewDto} que representan las vistas previas de los equipos.
     *         Si el rol es `ADMINISTRATOR`, las vistas previas incluirán información adicional sobre el equipo.
     * @throws EntityNotFoundException Si la lista de equipos no contiene elementos.
     */

    private List<TeamPreviewDto> findTeamsPreview(List<Team> teams) {
        teamValidationService.validateTeams(teams);
        return teams.stream()
                .map(TeamPreviewDto::fromTeam)
                .toList();
    }

    private void validateNotFullMembers(Team team) {
        if (team.getMembers().size() >= Team.MEMBER_LIMIT) {
            throw new IllegalArgumentException("Cannot add more members. The team has reached the maximum limit of " + Team.MEMBER_LIMIT + " members.");
        }
    }
 }
