package cl.ufro.dci.hackmaster_api.teams.repository;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de equipos para acceder y gestionar los datos de la entidad {@link Team}.
 * Esta interfaz extiende {@link CrudRepository} para proporcionar métodos básicos de CRUD
 * y define consultas personalizadas para recuperar equipos basados en diferentes criterios.
 */


@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    /**
     * Verifica si un equipo existe en la base de datos por su nombre.
     *
     * @param name El nombre del equipo a verificar.
     * @return {@code true} si el equipo con el nombre dado existe, {@code false} en caso contrario.
     */

    boolean existsByTeamName(String name);

    /**
     * Obtiene los equipos ordenados por su fecha de creación.
     *
     * @return Una lista con los equipos ordenados por su fecha de creación.
     */

    @Query("SELECT t FROM Team t ORDER BY t.creationDate DESC")
    List<Team> findTeamsOrderByCreationDateAsc();

    /**
     * Encuentra los equipos cuyo nombre contiene una palabra clave específica.
     *
     * @param keyword La palabra clave que debe aparecer en cualquier parte del nombre del equipo.
     * @return Una lista de equipos cuyos nombres contienen la palabra clave.
     */

    @Query("SELECT t FROM Team t WHERE t.teamName LIKE CONCAT('%', :keyword, '%')")
    List<Team> findTeamsByKeyword(@Param("keyword") String keyword);

    /**
     * Encuentra los equipos a los que pertenece un usuario dado.
     *
     * @param leaderId El ID del líder.
     * @return Una lista de equipos a los que pertenece el usuario con el ID dado.
     */

    @Query("SELECT t FROM Team t WHERE t.leader.id = :leaderId")
    List<Team> findByUserId(@Param("leaderId") Long leaderId);

    /**
     * Encuentra los equipos a los que pertenece un usuario dado y cuyo nombre contiene una palabra clave.
     *
     * @param leaderId El ID del líder de equipo.
     * @param keyword La palabra clave que debe aparecer en cualquier parte del nombre del equipo.
     * @return Una lista de equipos a los que pertenece el usuario con el ID dado y cuyo nombre contiene la palabra clave.
     */

    @Query("SELECT t FROM Team t WHERE t.leader.id = :leaderId AND t.teamName LIKE CONCAT('%', :keyword, '%')")
    List<Team> findByUserIdAndKeyword(@Param("leaderId") Long leaderId, @Param("keyword") String keyword);

    //nuestro
    Team findByChallengeAndLeader(Challenge challenge, Participant participant);

    List<Team> findByChallenge(Challenge challenge);
    List<Team> findByLeader(Participant participant);
    List<Team> findByLeaderId(Long leaderId);
    Optional<Team> findByLeaderIdAndChallengeId(Long leaderId, Long challengeId);





}
