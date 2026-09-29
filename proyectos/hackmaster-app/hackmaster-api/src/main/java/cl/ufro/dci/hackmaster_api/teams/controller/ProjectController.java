package cl.ufro.dci.hackmaster_api.teams.controller;

import cl.ufro.dci.hackmaster_api.teams.dto.*;
import cl.ufro.dci.hackmaster_api.teams.service.ProjectService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("api/teams/projects")

/**
 * Controlador para manejar las operaciones relacionadas con proyectos.
 * Permite crear proyectos, obtener proyectos por el ID de un equipo,
 * y realizar otras operaciones como actualizar fotos.
 */

public class ProjectController {

    private final ProjectService projectService;

    /**
     * Constructor del controlador.
     *
     * @param projectService Servicio para gestionar la lógica de proyectos.
     */

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Crea un nuevo proyecto.
     *
     * @param newProject Información del proyecto a crear.
     * @return El ID del nuevo equipo creado.
     */

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public long createProject(@Valid @RequestBody ProjectRequestDto newProject) {
        return projectService.saveProject(newProject);
    }

    /**
     * Obtiene una lista con todos los proyectos, ordenados de la A a la Z.
     *
     * @return Lista de proyectos con su información.
     */

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectPreviewDto> getProjects() {
        return projectService.findProjectsPreview();
    }

    /**
     * Obtiene proyectos asociados a un equipo dado por su ID.
     *
     * @param teamId ID del equipo para filtrar los proyectos.
     * @return El proyecto al que pertenece el equipo.
     */

    @GetMapping("/by-team/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectInfoDto getProjectByTeamId(@PathVariable("id") Long teamId) {
        return projectService.findProjectInfoById(teamId);
    }

    /**
     * Obtiene el proyecto asociado a un equipo dado por su ID.
     *
     * @param teamId ID del equipo del que queremos su proyecto.
     * @return El proyecto al que pertenece el equipo.
     */

    @GetMapping("preview/by-team/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectPreviewDto getProjectPreviewByTeamId(@PathVariable("id") Long teamId) {
        return projectService.findProjectPreviewByTeamId(teamId);
    }

    /**
     * Actualiza la foto de un proyecto.
     *
     * @param projectId ID del proyecto cuya foto se va a actualizar.
     * @param photoFile Archivo de foto para actualizar.
     */

    @PutMapping("/{id}/photo")
    @ResponseStatus(HttpStatus.OK)
    public void updateProjectPhoto(@PathVariable("id") Long projectId, @RequestParam("file") MultipartFile photoFile) {
        projectService.updateProjectPhoto(projectId, photoFile);
    }

    /**
     * Maneja excepciones generales que puedan ocurrir durante las operaciones de proyecto.
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
