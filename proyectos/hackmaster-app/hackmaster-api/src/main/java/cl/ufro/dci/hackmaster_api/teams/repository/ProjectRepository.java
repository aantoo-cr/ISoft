package cl.ufro.dci.hackmaster_api.teams.repository;

import cl.ufro.dci.hackmaster_api.teams.domain.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de proyectos para acceder y gestionar los datos de la entidad {@link Project}.
 * Esta interfaz extiende {@link CrudRepository} para proporcionar métodos básicos de CRUD
 * y define consultas personalizadas relacionadas con proyectos y equipos.
 */

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    /**
     * Verifica si un proyecto existe en la base de datos por su nombre.
     *
     * @param name El nombre del proyecto a verificar.
     * @return {@code true} si el proyecto con el nombre dado existe, {@code false} en caso contrario.
     */

    boolean existsByProjectName(String name);

    /**
     * Obtiene todos los proyectos ordenados por su nombre de forma ascendente.
     *
     * @return Lista de proyectos ordenados por nombre.
     */

    @Query("SELECT p FROM Project p ORDER BY p.projectName ASC")
    List<Project> findByOrderByProjectNameAsc();

    /**
     * Encuentra el proyecto asociado a un equipo específico.
     *
     * @param teamId El ID del equipo.
     * @return El proyecto asociado al equipo con el ID dado, envuelto en un Optional.
     */

    @Query("SELECT p FROM Project p WHERE p.team.teamId = :teamId")
    Optional<Project> findByTeamId(@Param("teamId") Long teamId);
}