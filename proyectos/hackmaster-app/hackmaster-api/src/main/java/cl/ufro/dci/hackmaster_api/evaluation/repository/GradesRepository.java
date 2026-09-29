package cl.ufro.dci.hackmaster_api.evaluation.repository;
import cl.ufro.dci.hackmaster_api.evaluation.domain.Grades;
import cl.ufro.dci.hackmaster_api.registration.domain.Judge;
import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface GradesRepository extends CrudRepository<Grades, Long> {
    List<Grades> findByJudge(Judge judge);
    List<Grades> findByTeam(Team team);
    List<Grades> findByTeamAndChallenge(Team team, Challenge challenge);
    @Query("SELECT g.team.teamName, AVG(g.averageGradeJudge) AS avgGrade " +
            "FROM Grades g WHERE g.challenge.id = :hackathonId " +
            "GROUP BY g.team.teamName ORDER BY avgGrade DESC")
    List<Object[]> findTeamRankingsByHackathonId(@Param("hackathonId") Long hackathonId);

    Grades findByJudgeIdAndChallengeIdAndTeam_TeamId(Long judgeId, Long challengeId, Long teamId);

    List<Grades> findByChallengeId(Long challengeId);

}