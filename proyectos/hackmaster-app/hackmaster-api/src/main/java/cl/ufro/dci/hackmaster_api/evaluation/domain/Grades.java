package cl.ufro.dci.hackmaster_api.evaluation.domain;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.registration.domain.Judge;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false) // Relación ManyToOne con Team
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false) // Relación ManyToOne con Challenge
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "judge_id", nullable = false) // Relación ManyToOne con Judge
    private Judge judge;

    private Float averageGradeJudge;
}
