package cl.ufro.dci.hackmaster_api.teams.service;

import cl.ufro.dci.hackmaster_api.teams.domain.Project;
import cl.ufro.dci.hackmaster_api.teams.dto.*;
import cl.ufro.dci.hackmaster_api.teams.repository.ProjectRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio encargado de gestionar los proyectos en la aplicación, proporcionando métodos
 * para la creación, búsqueda, actualización y gestión de proyectos de los equipos.
 */

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TeamService teamService;
    private final FileService fileService;

    /**
     * Constructor para inicializar el servicio de proyectos con las dependencias necesarias.
     *
     * @param projectRepository Repositorio de proyectos.
     * @param teamService Servicio de equipos, usado a la hora de crear el proyecto.
     * @param fileService Servicio de archivos, usado a la hora de actualizar la foto del proyecto.
     */

    public ProjectService(ProjectRepository projectRepository,
                          FileService fileService, TeamService teamService) {
        this.projectRepository = projectRepository;
        this.teamService = teamService;
        this.fileService = fileService;
    }

    /**
     * Guarda un proyecto a partir de la información proporcionada en la solicitud.
     *
     * @param request La información del proyecto a crear.
     * @return El ID del proyecto recién creado.
     * @throws EntityExistsException Si ya existe un proyecto con el mismo nombre.
     */

    public long saveProject(ProjectRequestDto request) {
        validateProjectExists(request.getName());
        var project = createProjectFromRequestDto(request);
        projectRepository.save(project);
        return project.getProjectId();
    }

    /**
     * Encuentra y devuelve la información detallada de un proyecto por el ID de su equipo.
     *
     * @param teamId El ID del equipo.
     * @return La información detallada del equipo como un DTO.
     * @throws EntityNotFoundException Si no se encuentra un proyecto asociado al equipo con el ID proporcionado.
     */

    public ProjectInfoDto findProjectInfoById(Long teamId) {
        var project = projectRepository.findByTeamId(teamId).orElseThrow(
                () -> new EntityNotFoundException("Project for team " + teamId + " not found in database.")
        );
        return ProjectInfoDto.fromProject(project);
    }

    /**
     * Encuentra las vistas previas de proyectos ordenados de la A a la Z.
     *
     * @return Un listado de las vistas vistas previas de proyectos.
     * @throws EntityNotFoundException Si no se encuentran proyectos en la base de datos.
     */

    public List<ProjectPreviewDto> findProjectsPreview() {
        var projects = projectRepository.findByOrderByProjectNameAsc();
        validateProjects(projects);
        return projects.stream()
                .map(ProjectPreviewDto::fromProject)
                .toList();
    }

    /**
     * Encuentra las vista previa del proyecto del equipo.
     *
     * @return La vista previa del proyecto.
     * @throws EntityNotFoundException Si no se encuentra el proyecto.
     */

    public ProjectPreviewDto findProjectPreviewByTeamId(Long teamId) {
        var project = findProjectById(teamId);
        return ProjectPreviewDto.fromProject(project);
    }

    /**
     * Actualiza la foto de un proyecto.
     *
     * @param projectId El ID del proyecto.
     * @param photoFile El archivo de la nueva foto.
     * @throws EntityNotFoundException Si el proyecto no se encuentra en la base de datos.
     * @throws IllegalArgumentException Si el archivo de foto es inválido (vacío o no es una imagen PNG/JPG).
     * @throws RuntimeException Si ocurre un error al guardar el archivo o al eliminar la foto anterior.
     */

    public void updateProjectPhoto(Long projectId, MultipartFile photoFile) {
        var project = findProjectById(projectId);

        if (project.getPhotoUrl() != null && !project.getPhotoUrl().isEmpty()) {
            fileService.removePhoto(project.getPhotoUrl());
        }

        var photoUrl = fileService.createPhotoUrl(photoFile);
        project.setPhotoUrl(photoUrl);
        projectRepository.save(project);
    }

    /**
     * Encuentra un proyecto por su ID.
     *
     * @param projectId El ID del proyecto.
     * @return El proyecto correspondiente al ID.
     * @throws EntityNotFoundException Si no se encuentra el proyecto.
     */

    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Not found repository in database."));
    }

    /**
     * Crea un nuevo proyecto a partir de los datos proporcionados en el DTO de solicitud.
     *
     * @param request El DTO con la información del nuevo proyecto.
     * @return El nuevo proyecto creado.
     * @throws EntityNotFoundException Si no se encuentra el equipo correspondiente al ID proporcionado.
     */

    private Project createProjectFromRequestDto(ProjectRequestDto request) {
        var newProject = new Project();
        var team = teamService.findTeamById(request.getTeamId());
        newProject.setProjectName(request.getName());
        newProject.setDescription(request.getDescription());
        newProject.setRepositoryUrl(request.getRepositoryUrl());
        newProject.setCreationDate(LocalDate.now());
        newProject.setTeam(team);
        return newProject;
    }

    /**
     * Valida que la lista de proyectos no esté vacía.
     *
     * @param projects la lista de equipos a verificar
     * @throws EntityNotFoundException si no se encuentran proyectos en la base de datos
     */

    public void validateProjects(List<Project> projects) {
        if (projects.isEmpty()) {
            throw new EntityNotFoundException("Not found projects in database.");
        }
    }

    /**
     * Valida si un proyecto con el nombre dado ya existe.
     *
     * @param projectName el nombre del proyecto a verificar si ya existe
     * @throws EntityExistsException si ya existe un proyecto con el nombre especificado
     */

    public void validateProjectExists(String projectName) {
        if (projectRepository.existsByProjectName(projectName)) {
            throw new EntityExistsException("Project with name " + projectName + " already exists.");
        }
    }
}
