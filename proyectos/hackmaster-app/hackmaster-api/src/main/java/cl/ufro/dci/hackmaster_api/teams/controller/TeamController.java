package cl.ufro.dci.hackmaster_api.teams.controller;

import cl.ufro.dci.hackmaster_api.teams.dto.MemberInfoDto;
import cl.ufro.dci.hackmaster_api.teams.dto.TeamInfoDto;
import cl.ufro.dci.hackmaster_api.teams.dto.TeamPreviewDto;
import cl.ufro.dci.hackmaster_api.teams.dto.TeamRequestDto;
import cl.ufro.dci.hackmaster_api.teams.service.TeamService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/teams")

/**
 * Controlador para manejar las operaciones relacionadas con equipos.
 * Permite crear equipos, obtener equipos por filtros específicos,
 * y realizar otras operaciones como añadir miembros o actualizar fotos.
 */

public class TeamController {

    private final TeamService teamService;

    /**
     * Constructor del controlador.
     *
     * @param teamService Servicio para gestionar la lógica de equipos.
     */

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Crea un nuevo equipo.
     *
     * @param newTeam Información del equipo a crear.
     * @return El ID del nuevo equipo creado.
     */

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public long createTeam(@Valid @RequestBody TeamRequestDto newTeam) {
        return teamService.saveTeam(newTeam);
    }

    /**
     * Obtiene una lista de los equipos.
     *
     * @return Lista de equipos con su información.
     */

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TeamPreviewDto> getTeams() {
        return teamService.findTeamPreviews();
    }

    /**
     * Filtra equipos por una palabra clave en su nombre.
     *
     * @param keyword Palabra clave que debe aparecer en cualquier parte del nombre del equipo.
     * @return Lista de equipos que cumplen con el filtro.
     */
    @GetMapping("/filter/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamPreviewDto> getTeamsFilterByKeyword(@PathVariable String keyword) {
        return teamService.findByNameContaining(keyword);
    }

    /**
     * Obtiene equipos asociados a un usuario dado por su ID.
     *
     * @param userId ID del usuario para filtrar los equipos.
     * @return Lista de equipos a los que pertenece el usuario.
     */

    @GetMapping("/by-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamPreviewDto> getTeamsByUserId(@PathVariable("id") Long userId) {
        return teamService.findByUserId(userId);
    }

    /**
     * Filtra los equipos asociados a un usuario por una palabra clave en el nombre del equipo.
     *
     * @param userId ID del usuario para filtrar los equipos.
     * @param keyword Palabra clave que debe aparecer en cualquier parte del nombre del equipo.
     * @return Lista de equipos que cumplen con el filtro.
     */

    @GetMapping("/by-user/{userId}/filter/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamPreviewDto> getTeamsByUserFilterByKeyword(@PathVariable Long userId, @PathVariable String keyword) {
        return teamService.findByUserIdAndNameContaining(userId, keyword);
    }

    /**
     * Obtiene la información detallada de un equipo por su ID.
     *
     * @param id ID del equipo a obtener.
     * @return Información detallada del equipo.
     */

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TeamInfoDto getTeamById(@PathVariable("id") Long id) {
        return teamService.findTeamInfoById(id);
    }

    /**
     * Añade un miembro a un equipo.
     *
     * @param teamId ID del equipo al que se va a añadir el miembro.
     */

    @PostMapping("/add-member")
    @ResponseStatus(HttpStatus.OK)
    public void addMember (@RequestParam Long teamId, @RequestBody MemberInfoDto infoDto) {
        teamService.addMember(teamId, infoDto);
    }

    /**
     * Maneja excepciones generales que puedan ocurrir durante las operaciones de equipo.
     *
     * @param ex Excepción que ocurrió.
     * @return Respuesta de error detallando la excepción.
     */

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class,
            IllegalArgumentException.class})
    public ErrorResponse handleException(Exception ex) {
        var status = ControllerUtils.getHttpStatus(ex);
        return ErrorResponse.builder(ex, status, ex.getMessage())
                .property("error", status.getReasonPhrase())
                .property("timestamp", Instant.now())
                .build();
    }

    /**
     * Procesa los errores de validación y devuelve un mapa de campos con los mensajes de error.
     *
     * @param ex La excepción de validación {@link MethodArgumentNotValidException}.
     * @return Un mapa donde las claves son los nombres de los campos y los valores los mensajes de error.
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ControllerUtils.getValidationErrors(ex);
        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, "Validation failed")
                .property("errors", errors)
                .property("timestamp", Instant.now())
                .build();
    }
}

