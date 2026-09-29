package cl.ufro.dci.hackmaster_api.teams.service;

import cl.ufro.dci.hackmaster_api.teams.domain.Project;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import cl.ufro.dci.hackmaster_api.teams.dto.ProjectRequestDto;
import cl.ufro.dci.hackmaster_api.teams.repository.ProjectRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TeamService teamService;

    @Mock
    private FileService fileService;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debe lanzar una excepción si el proyecto ya existe")
    void testSaveProjectThrowsExceptionWhenProjectExists() {

        var requestDto = new ProjectRequestDto();
        requestDto.setName("Proyecto Existente");
        requestDto.setDescription("Descripción");
        requestDto.setRepositoryUrl("http://repo.com");
        requestDto.setTeamId(1L);

        when(projectRepository.existsByProjectName("Proyecto Existente")).thenReturn(true);


        assertThrows(EntityExistsException.class, () -> projectService.saveProject(requestDto));
        verify(projectRepository, times(1)).existsByProjectName("Proyecto Existente");
    }

    @Test
    @DisplayName("Debe guardar un proyecto correctamente")
    void testSaveProjectSuccess() {

        var requestDto = new ProjectRequestDto();
        requestDto.setName("Nuevo Proyecto");
        requestDto.setDescription("Descripción");
        requestDto.setRepositoryUrl("http://repo.com");
        requestDto.setTeamId(1L);

        var team = new Team();
        team.setTeamId(1L);
        team.setTeamName("Equipo Ejemplo");

        var project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Nuevo Proyecto");
        project.setDescription("Descripción");
        project.setRepositoryUrl("http://repo.com");
        project.setCreationDate(LocalDate.now());
        project.setTeam(team);

        when(projectRepository.existsByProjectName("Nuevo Proyecto")).thenReturn(false);
        when(teamService.findTeamById(1L)).thenReturn(team);
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> {
            Project savedProject = invocation.getArgument(0);
            savedProject.setProjectId(1L);
            return savedProject;
        });


        long projectId = projectService.saveProject(requestDto);


        assertThat(projectId).isEqualTo(1L);
        verify(projectRepository, times(1)).save(any(Project.class));
    }


    @Test
    @DisplayName("Debe encontrar un proyecto por ID del equipo")
    void testFindProjectInfoById() {

        var project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Proyecto 1");
        project.setDescription("Descripción");

        when(projectRepository.findByTeamId(1L)).thenReturn(Optional.of(project));


        var projectInfo = projectService.findProjectInfoById(1L);


        assertThat(projectInfo.getId()).isEqualTo(1L);
        assertThat(projectInfo.getName()).isEqualTo("Proyecto 1");
        verify(projectRepository, times(1)).findByTeamId(1L);
    }

    @Test
    @DisplayName("Debe lanzar una excepción si no encuentra el proyecto por ID del equipo")
    void testFindProjectInfoByIdThrowsException() {

        when(projectRepository.findByTeamId(1L)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> projectService.findProjectInfoById(1L));
        verify(projectRepository, times(1)).findByTeamId(1L);
    }

}
