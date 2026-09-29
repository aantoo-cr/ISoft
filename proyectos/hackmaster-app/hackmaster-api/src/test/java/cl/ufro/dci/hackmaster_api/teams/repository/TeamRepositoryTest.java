package cl.ufro.dci.hackmaster_api.teams.repository;

import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Debe devolver equipos ordenados por fecha de creación en orden descendente")
    void testFindTeamsOrderByCreationDateAsc() {
        Participant leader1 = new Participant();
        leader1.setRut("27847694-4");
        leader1.setName("Javier");
        leader1.setLastname("Alcalde");
        leader1.setEmail("j.alcalde01@ufromail.cl");
        leader1.setPassword("12345678");
        leader1.setUsername("bigalcah");
        leader1.setCity("Temuco");
        leader1.setCountry("Chile");
        leader1.setPhoneNumber("123456789");
        entityManager.persist(leader1);

        Participant leader2 = new Participant();
        leader2.setRut("21476732-5");
        leader2.setName("Jesus");
        leader2.setLastname("Tapia");
        leader2.setEmail("j.tapia05@ufromail.cl");
        leader2.setPassword("12345678");
        leader2.setUsername("yisus");
        leader2.setCity("Temuco");
        leader2.setCountry("Chile");
        leader2.setPhoneNumber("987654321");
        entityManager.persist(leader2);

        Team team1 = new Team();
        team1.setTeamName("Equipo Viejo");
        team1.setCreationDate(LocalDate.of(2023, 1, 1));
        team1.setLeader(leader1);
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setTeamName("Equipo Nuevo");
        team2.setCreationDate(LocalDate.of(2024, 1, 1));
        team2.setLeader(leader2);
        teamRepository.save(team2);

        List<Team> teams = teamRepository.findTeamsOrderByCreationDateAsc();

        assertThat(teams).hasSize(2);
        assertThat(teams.get(0).getTeamName()).isEqualTo("Equipo Nuevo");
        assertThat(teams.get(1).getTeamName()).isEqualTo("Equipo Viejo");
    }

    @Test
    @DisplayName("Debe verificar si un equipo existe por su nombre")
    void testExistsByTeamName() {
        Participant leader1 = new Participant();
        leader1.setRut("27847694-4");
        leader1.setName("Javier");
        leader1.setLastname("Alcalde");
        leader1.setEmail("j.alcalde01@ufromail.cl");
        leader1.setPassword("12345678");
        leader1.setUsername("bigalcah");
        leader1.setCity("Temuco");
        leader1.setCountry("Chile");
        leader1.setPhoneNumber("123456789");
        entityManager.persist(leader1);

        Team team1 = new Team();
        team1.setTeamName("Equipo Viejo");
        team1.setCreationDate(LocalDate.of(2023, 1, 1));
        team1.setLeader(leader1);
        teamRepository.save(team1);

        boolean exists = teamRepository.existsByTeamName("Equipo Viejo");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Debe encontrar equipos por palabra clave en su nombre")
    void testFindTeamsByKeyword() {
        Participant leader1 = new Participant();
        leader1.setRut("27847694-4");
        leader1.setName("Javier");
        leader1.setLastname("Alcalde");
        leader1.setEmail("j.alcalde01@ufromail.cl");
        leader1.setPassword("12345678");
        leader1.setUsername("bigalcah");
        leader1.setCity("Temuco");
        leader1.setCountry("Chile");
        leader1.setPhoneNumber("123456789");
        entityManager.persist(leader1);

        Participant leader2 = new Participant();
        leader2.setRut("21476732-5");
        leader2.setName("Jesus");
        leader2.setLastname("Tapia");
        leader2.setEmail("j.tapia05@ufromail.cl");
        leader2.setPassword("12345678");
        leader2.setUsername("yisus");
        leader2.setCity("Temuco");
        leader2.setCountry("Chile");
        leader2.setPhoneNumber("987654321");
        entityManager.persist(leader2);

        Team team1 = new Team();
        team1.setTeamName("Aventura Espacial");
        team1.setCreationDate(LocalDate.now());
        team1.setLeader(leader1);
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setTeamName("Aventura Submarina");
        team2.setCreationDate(LocalDate.now());
        team2.setLeader(leader2);
        teamRepository.save(team2);

        Team team3 = new Team();
        team3.setTeamName("Equipo Común");
        team3.setCreationDate(LocalDate.now());
        team3.setLeader(leader1);
        teamRepository.save(team3);

        List<Team> teams = teamRepository.findTeamsByKeyword("Aventura");

        assertThat(teams).hasSize(2);
        assertThat(teams).extracting(Team::getTeamName).containsExactlyInAnyOrder("Aventura Espacial", "Aventura Submarina");
    }

    @Test
    @DisplayName("Debe encontrar equipos por ID del líder")
    void testFindByUserId() {
        Participant leader = new Participant();
        leader.setRut("27847694-4");
        leader.setName("Javier");
        leader.setLastname("Alcalde");
        leader.setEmail("j.alcalde01@ufromail.cl");
        leader.setPassword("12345678");
        leader.setUsername("bigalcah");
        leader.setCity("Temuco");
        leader.setCountry("Chile");
        leader.setPhoneNumber("123456789");
        entityManager.persist(leader);

        Team team1 = new Team();
        team1.setTeamName("Equipo del Líder");
        team1.setCreationDate(LocalDate.now());
        team1.setLeader(leader);
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setTeamName("Segundo Equipo del Líder");
        team2.setCreationDate(LocalDate.now());
        team2.setLeader(leader);
        teamRepository.save(team2);

        List<Team> teams = teamRepository.findByUserId(leader.getId());

        assertThat(teams).hasSize(2);
        assertThat(teams).extracting(Team::getTeamName).containsExactlyInAnyOrder("Equipo del Líder", "Segundo Equipo del Líder");
    }

    @Test
    @DisplayName("Debe devolver falso si el equipo no existe por su nombre")
    void testExistsByTeamName_NotExists() {
        boolean exists = teamRepository.existsByTeamName("Equipo Inexistente");
        assertThat(exists).isFalse();
    }
}
