package cl.ufro.dci.hackmaster_api.teams.controller;

import cl.ufro.dci.hackmaster_api.teams.dto.ProjectInfoDto;
import cl.ufro.dci.hackmaster_api.teams.dto.ProjectPreviewDto;
import cl.ufro.dci.hackmaster_api.teams.dto.ProjectRequestDto;
import cl.ufro.dci.hackmaster_api.teams.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    @DisplayName("CU01 - CP01 - CreateProjectSuccess")
    void createProjectSuccess() throws Exception {

        var projectRequest = new ProjectRequestDto();
        projectRequest.setName("Proyecto A");
        projectRequest.setDescription("Descripción del proyecto");
        projectRequest.setRepositoryUrl("http://github.com/proyecto");
        projectRequest.setTeamId(1L);

        var jsonRequest = objectMapper.writeValueAsString(projectRequest);


        when(projectService.saveProject(any(ProjectRequestDto.class))).thenReturn(1L);


        mockMvc.perform(post("/api/teams/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());


        verify(projectService, times(1)).saveProject(any(ProjectRequestDto.class));
    }

    @Test
    @DisplayName("CU01 - CP02 - ProjectAlreadyExists")
    void projectAlreadyExists() throws Exception {

        var projectRequest = new ProjectRequestDto();
        projectRequest.setName("Proyecto Repetido");
        projectRequest.setDescription("Descripción del proyecto");
        projectRequest.setRepositoryUrl("http://github.com/proyecto");
        projectRequest.setTeamId(1L);

        var jsonRequest = objectMapper.writeValueAsString(projectRequest);


        doThrow(new EntityExistsException("Project with name 'Proyecto Repetido' already exists."))
                .when(projectService).saveProject(any(ProjectRequestDto.class));


        mockMvc.perform(post("/api/teams/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(result -> {
                    var responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains("Project with name 'Proyecto Repetido' already exists."));
                });


        verify(projectService, times(1)).saveProject(any(ProjectRequestDto.class));
    }

    @Test
    @DisplayName("CU02 - CP01 - GetAllProjectsSuccess")
    void getAllProjectsSuccess() throws Exception {
        var project1 = new ProjectPreviewDto();
        project1.setId(1L);
        project1.setName("Proyecto A");
        project1.setPhotoUrl("http://example.com/photoA.jpg");

        var project2 = new ProjectPreviewDto();
        project2.setId(2L);
        project2.setName("Proyecto B");
        project2.setPhotoUrl("http://example.com/photoB.jpg");

        var projects = List.of(project1, project2);

        when(projectService.findProjectsPreview()).thenReturn(projects);

        mockMvc.perform(get("/api/teams/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Proyecto A"))
                .andExpect(jsonPath("$[0].photoUrl").value("http://example.com/photoA.jpg"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Proyecto B"))
                .andExpect(jsonPath("$[1].photoUrl").value("http://example.com/photoB.jpg"));

        verify(projectService, times(1)).findProjectsPreview();
    }

    @Test
    @DisplayName("CU02 - CP02 - GetAllProjectsNotFound")
    void getAllProjectsNotFound() throws Exception {
        when(projectService.findProjectsPreview()).thenThrow(new EntityNotFoundException("No projects found"));

        mockMvc.perform(get("/api/teams/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    var responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains("No projects found"));
                });

        verify(projectService, times(1)).findProjectsPreview();
    }

    @Test
    @DisplayName("CU03 - CP01 - GetProjectByTeamIdSuccess")
    void getProjectByTeamIdSuccess() throws Exception {
        var projectInfo = new ProjectInfoDto();
        projectInfo.setId(1L);
        projectInfo.setName("Proyecto A");
        projectInfo.setDescription("Descripción del proyecto");
        projectInfo.setPhotoUrl("http://example.com/photo.jpg");
        projectInfo.setRepositoryUrl("http://github.com/proyecto");

        when(projectService.findProjectInfoById(1L)).thenReturn(projectInfo);

        mockMvc.perform(get("/api/teams/projects/by-team/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Proyecto A"))
                .andExpect(jsonPath("$.description").value("Descripción del proyecto"))
                .andExpect(jsonPath("$.photoUrl").value("http://example.com/photo.jpg"))
                .andExpect(jsonPath("$.repositoryUrl").value("http://github.com/proyecto"));

        verify(projectService, times(1)).findProjectInfoById(1L);
    }

    @Test
    @DisplayName("CU03 - CP02 - GetProjectByTeamIdNotFound")
    void getProjectByTeamIdNotFound() throws Exception {
        when(projectService.findProjectInfoById(1L)).thenThrow(new EntityNotFoundException("Project not found"));

        mockMvc.perform(get("/api/teams/projects/by-team/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    var responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains("Project not found"));
                });

        verify(projectService, times(1)).findProjectInfoById(1L);
    }


}
