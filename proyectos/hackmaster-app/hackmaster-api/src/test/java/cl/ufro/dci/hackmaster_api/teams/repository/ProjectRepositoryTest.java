package cl.ufro.dci.hackmaster_api.teams.repository;

import cl.ufro.dci.hackmaster_api.teams.domain.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("Debe guardar y recuperar un proyecto por nombre")
    void testExistsByProjectName() {
        // Arrange
        Project project = new Project();
        project.setProjectName("Proyecto 1");
        project.setDescription("Descripción del proyecto");
        project.setRepositoryUrl("http://repo.com");
        project.setCreationDate(LocalDate.now());
        project.setPhotoUrl("http://example.com/photo.jpg"); // Asignar un valor válido para photoUrl
        projectRepository.save(project);

        // Act
        boolean exists = projectRepository.existsByProjectName("Proyecto 1");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Debe devolver los proyectos ordenados alfabéticamente por nombre")
    void testFindByOrderByProjectNameAsc() {
        // Arrange
        Project project1 = new Project();
        project1.setProjectName("Alpha");
        project1.setDescription("Descripción Alpha");
        project1.setRepositoryUrl("http://repo.com/alpha");
        project1.setPhotoUrl("http://example.com/alpha.jpg");
        project1.setCreationDate(LocalDate.now());
        projectRepository.save(project1);

        Project project2 = new Project();
        project2.setProjectName("Beta");
        project2.setDescription("Descripción Beta");
        project2.setRepositoryUrl("http://repo.com/beta");
        project2.setPhotoUrl("http://example.com/beta.jpg");
        project2.setCreationDate(LocalDate.now());
        projectRepository.save(project2);


        var projects = projectRepository.findByOrderByProjectNameAsc();

        assertThat(projects).hasSize(2);
        assertThat(projects.get(0).getProjectName()).isEqualTo("Alpha");
        assertThat(projects.get(1).getProjectName()).isEqualTo("Beta");
    }

}
