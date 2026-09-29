package cl.ufro.dci.hackmaster_api.reports.service;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.challenges.repository.ChallengeRepository;
import cl.ufro.dci.hackmaster_api.evaluation.repository.GradesRepository;
import cl.ufro.dci.hackmaster_api.rec_management.repository.PrizeRepository;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
import cl.ufro.dci.hackmaster_api.evaluation.domain.Grades;
import cl.ufro.dci.hackmaster_api.registration.repository.JudgeRepository;
import cl.ufro.dci.hackmaster_api.registration.repository.OrganizationRepository;
import cl.ufro.dci.hackmaster_api.registration.repository.ParticipantRepository;
import cl.ufro.dci.hackmaster_api.teams.domain.Member;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import cl.ufro.dci.hackmaster_api.teams.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;
import cl.ufro.dci.hackmaster_api.registration.domain.*;
import cl.ufro.dci.hackmaster_api.rec_management.domain.Prize;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ReportsService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private JudgeRepository judgeRepository;

    @Autowired
    private GradesRepository gradesRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public Map<String, Object> getDetailsByOrganizerId(Long actorId) {
        // Obtener la organización por ID
        Optional<Organization> organizationOpt = organizationRepository.findById(actorId);
        if (organizationOpt.isEmpty()) {
            throw new RuntimeException("Organizador no encontrado");
        }

        Organization organization = organizationOpt.get();

        // Crear el mapa para la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("actorId", actorId);
        response.put("actorType", "organizador");
        response.put("actorName", organization.getSocialNameOrganization());

        // Obtener los hackatones organizados por esta organización
        List<Challenge> challenges = challengeRepository.findByOrganization(organization);

        // Si no hay hackatones organizados, devolver el mensaje correspondiente
        if (challenges.isEmpty()) {
            response.put("hackatones", "No ha organizado ningun Hackathon");
        } else {
            // Si hay hackatones, procesarlos como antes
            List<Map<String, Object>> hackatones = challenges.stream()
                    .map(challenge -> {
                        Map<String, Object> hackaton = new HashMap<>();
                        hackaton.put("hackatonName", challenge.getNombre());

                        // Calcular ingresos generados (sumando el challengePrice de los equipos)
                        List<Team> teams = teamRepository.findByChallenge(challenge);
                        Double ingresosGenerados = teams.stream()
                                .mapToDouble(team -> team.getChallenge().getChallengePrice())
                                .sum();
                        hackaton.put("ingresosGenerados", ingresosGenerados);

                        // Calcular total de premios
                        List<Prize> prizes = prizeRepository.findByChallenge(challenge);
                        Double totalPremios = prizes.stream()
                                .mapToDouble(Prize::getValueReward)
                                .sum();
                        hackaton.put("totalPremios", totalPremios);

                        // Calcular total de inscripciones (número de equipos inscritos)
                        hackaton.put("totalInscripciones", teams.size());

                        return hackaton;
                    })
                    .collect(Collectors.toList());

            response.put("hackatones", hackatones);
        }

        return response;
    }

    public Map<String, Object> getDetailsListByOrganizerId(Long actorId) {
        // Obtener la organización por ID
        Optional<Organization> organizationOpt = organizationRepository.findById(actorId);
        if (organizationOpt.isEmpty()) {
            throw new RuntimeException("Organizador no encontrado");
        }

        Organization organization = organizationOpt.get();

        // Crear el mapa para la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("actorType", "organizador");
        response.put("actorName", organization.getSocialNameOrganization());

        // Obtener los hackatones organizados por esta organización
        List<Challenge> challenges = challengeRepository.findByOrganization(organization);

        // Si no hay hackatones organizados, devolver el mensaje correspondiente
        if (challenges.isEmpty()) {
            response.put("hackatones", "No ha organizado ningun Hackathon");
        } else {
            // Si hay hackatones, procesarlos
            List<Map<String, Object>> hackatones = challenges.stream()
                    .map(challenge -> {
                        Map<String, Object> hackaton = new HashMap<>();
                        hackaton.put("hackatonName", challenge.getNombre());
                        hackaton.put("fechaInicio", challenge.getFechaI().toString());
                        hackaton.put("fechaTermino", challenge.getFechaT().toString());
                        hackaton.put("hackatonId", challenge.getId());
                        return hackaton;
                    })
                    .collect(Collectors.toList());

            response.put("hackatones", hackatones);
        }

        return response;
    }

    public Map<String, Object> getHackathonDetailsOrganizer(Long actorId, Long hackathonId) {
        // Obtener la organización por ID
        Optional<Organization> organizationOpt = organizationRepository.findById(actorId);
        if (organizationOpt.isEmpty()) {
            throw new RuntimeException("Organizador no encontrado");
        }
        Organization organization = organizationOpt.get();

        // Obtener el hackathon por ID
        Optional<Challenge> challengeOpt = challengeRepository.findById(hackathonId);
        if (challengeOpt.isEmpty() || !challengeOpt.get().getOrganization().equals(organization)) {
            throw new RuntimeException("Hackathon no encontrado o no pertenece a este organizador");
        }
        Challenge challenge = challengeOpt.get();

        // Crear el mapa para la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("actorType", "organizador");
        response.put("actorName", organization.getSocialNameOrganization());
        response.put("hackatonName", challenge.getNombre());

        // Obtener los equipos registrados en este hackathon
        List<Team> teams = teamRepository.findByChallenge(challenge);

        // Número de inscripciones: cantidad de equipos
        response.put("numberOfRegistrations", teams.size());

        // Miembros por equipo: lista con el número de miembros por cada equipo o mensaje si no hay equipos
        if (teams.isEmpty()) {
            response.put("membersCountPerTeam", "Ningun grupo se ha inscrito");
        } else {
            List<Integer> membersCountPerTeam = teams.stream()
                    .map(team -> team.getMembers().size())
                    .collect(Collectors.toList());
            response.put("membersCountPerTeam", membersCountPerTeam);
        }

        // Número de equipos
        response.put("numberOfTeams", teams.size());

        // Calcular ingresos generados: suma del challengePrice de cada equipo
        Double totalRevenue = teams.stream()
                .mapToDouble(team -> team.getChallenge().getChallengePrice())
                .sum();
        response.put("totalRevenue", totalRevenue);

        return response;
    }

    public Map<String, Object> getDetailsListByJudgeId(Long judgeId) {
        // Obtener el juez por ID
        Optional<Judge> judgeOpt = judgeRepository.findById(judgeId);
        if (judgeOpt.isEmpty()) {
            throw new RuntimeException("Juez no encontrado");
        }
        Judge judge = judgeOpt.get();

        // Crear el mapa para la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("actorType", "juez");
        response.put("actorName", judge.getUsername());

        // Obtener las calificaciones del juez
        List<Grades> grades = gradesRepository.findByJudge(judge);

        // Usar un Set para eliminar duplicados
        List<Map<String, Object>> hackatones = grades.stream()
                .map(grade -> {
                    Map<String, Object> hackaton = new HashMap<>();
                    Challenge challenge = grade.getChallenge();
                    hackaton.put("hackatonName", challenge.getNombre());
                    hackaton.put("fechaInicio", challenge.getFechaI());
                    hackaton.put("fechaTermino", challenge.getFechaT());
                    hackaton.put("hackatonId", challenge.getId());
                    return hackaton;
                })
                .distinct()  // Eliminar duplicados
                .collect(Collectors.toList());

        // Si no hay hackatones asociados, devolver el mensaje como string
        if (hackatones.isEmpty()) {
            response.put("hackatones", "El juez no esta asignado a ningun Hackathon");
        } else {
            response.put("hackatones", hackatones);
        }

        return response;
    }

    public Map<String, Object> getHackathonDetailsJudge(Long judgeId, Long challengeId) {
        Map<String, Object> response = new HashMap<>();

        // Buscar el juez por ID
        Participant judge = judgeRepository.findById(judgeId).orElse(null);
        if (judge == null) {
            response.put("error", "Actor not found");
            return response;
        }
        String categoria = "Juez";
        String actorName = judge.getName() + " " + judge.getLastname();

        response.put("actorType", categoria);
        response.put("actorName", actorName);

        // Buscar el hackathon por ID
        Challenge challenge = challengeRepository.findById(challengeId).orElse(null);
        if (challenge == null) {
            response.put("error", "Hackaton not found");
            return response;
        }

        response.put("hackatonName", challenge.getNombre());

        // Inicializar estructuras para manejar las calificaciones
        List<Map<String, Object>> gradesList = new ArrayList<>();
        Map<Long, Map<String, Object>> teamDetailsMap = new HashMap<>();
        Map<Long, Double> teamGradeSum = new HashMap<>();
        Map<Long, Integer> teamJudgeCount = new HashMap<>();

        // Filtrar calificaciones por challengeId
        Iterable<Grades> allGrades = gradesRepository.findAll();
        for (Grades grade : allGrades) {
            if (Objects.equals(grade.getChallenge().getId(), challengeId)) {
                // Verificar si la calificación del juez es nula
                if (grade.getAverageGradeJudge() == null) {
                    continue; // Ignorar calificaciones nulas
                }

                // Buscar el equipo por teamId
                Team team = teamRepository.findById(grade.getTeam().getTeamId()).orElse(null);
                if (team != null) {
                    // Inicializar el mapa del equipo si no existe
                    teamDetailsMap.putIfAbsent(team.getTeamId(), new HashMap<>());
                    Map<String, Object> gradeDetails = teamDetailsMap.get(team.getTeamId());

                    // Establecer el nombre del equipo solo si aún no está establecido
                    if (!gradeDetails.containsKey("teamName")) {
                        gradeDetails.put("teamName", team.getTeamName());
                    }

                    // Acumular las calificaciones y contar los jueces
                    double currentGrade = grade.getAverageGradeJudge().doubleValue();
                    teamGradeSum.merge(team.getTeamId(), currentGrade, Double::sum);
                    teamJudgeCount.merge(team.getTeamId(), 1, Integer::sum);
                }
            }
        }

        // Calcular el promedio y obtener notaJuez para cada equipo
        for (Map.Entry<Long, Map<String, Object>> entry : teamDetailsMap.entrySet()) {
            Long teamId = entry.getKey();
            Map<String, Object> gradeDetails = entry.getValue();

            // Obtener la suma total de las calificaciones y el conteo de jueces
            Double totalScore = teamGradeSum.get(teamId);
            Integer judgeCount = teamJudgeCount.get(teamId);

            // Calcular el promedio solo si hay calificaciones y jueces
            if (totalScore != null && judgeCount != null && judgeCount > 0) {
                double averageGrade = totalScore / judgeCount;
                gradeDetails.put("promedioNotaGrupo", averageGrade);
            } else {
                gradeDetails.put("promedioNotaGrupo", 0.0); // Asignar 0 si no hay calificaciones
            }

            // Obtener la nota del juez para este equipo
            Grades judgeGrade = gradesRepository.findByJudgeIdAndChallengeIdAndTeam_TeamId(judgeId, challengeId, teamId);
            if (judgeGrade != null) {
                gradeDetails.put("notaJuez", judgeGrade.getAverageGradeJudge());
            } else {
                gradeDetails.put("notaJuez", null); // Si no hay calificación, asignar null
            }

            gradesList.add(gradeDetails); // Añadir detalles del equipo a la lista
        }

        // Calcular el ranking de equipos
        List<Map<String, Object>> rankingList = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Object>> entry : teamDetailsMap.entrySet()) {
            Long teamId = entry.getKey();
            Map<String, Object> gradeDetails = entry.getValue();

            // Añadir solo si hay un promedioNotaGrupo calculado
            if (gradeDetails.containsKey("promedioNotaGrupo")) {
                Map<String, Object> rankingDetails = new HashMap<>();
                rankingDetails.put("teamName", gradeDetails.get("teamName"));
                rankingDetails.put("promedioNotaGrupo", gradeDetails.get("promedioNotaGrupo"));
                rankingList.add(rankingDetails);
            }
        }

        // Ordenar el ranking de equipos de mayor a menor según el promedioNotaGrupo
        rankingList.sort((a, b) -> Double.compare((Double) b.get("promedioNotaGrupo"), (Double) a.get("promedioNotaGrupo")));

        // Manejar casos de ranking vacío
        if (rankingList.isEmpty()) {
            response.put("ranking", "no hay notas ingresadas para generar ranking");
        } else {
            response.put("ranking", rankingList);
        }

        // Manejar casos de gradesList vacío
        if (gradesList.isEmpty()) {
            response.put("error", "no hay notas ingresadas");
        } else {
            response.put("grades", gradesList);
        }

        return response;
    }

    public Map<String, Object> getDetailsListbyParticipantId(Long participantId) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> challengesDetails = new ArrayList<>();

        // Buscar al participante
        Participant participant = participantRepository.findById(participantId).orElse(null);
        if (participant == null) {
            response.put("error", "Actor not found");
            return response;
        }

        // Agregar información del participante a la respuesta
        response.put("actorType", "Participante");
        String actorName = participant.getName() + " " + participant.getLastname();
        response.put("actorName", actorName);

        // Obtener los equipos donde el participante es líder
        List<Team> teamsLedByParticipant = teamRepository.findByLeaderId(participantId);

        // Si el participante no lidera equipos, retorna mensaje
        if (teamsLedByParticipant.isEmpty()) {
            response.put("hackatonesDetails", "No ha participado en ningun Hackathon");
            return response;
        }

        // Recorrer los equipos para obtener los desafíos asociados
        for (Team team : teamsLedByParticipant) {
            Challenge challenge = team.getChallenge();
            if (challenge != null) {
                Map<String, Object> challengeDetails = new HashMap<>();
                challengeDetails.put("hackatonName", challenge.getNombre());
                challengeDetails.put("hackatonStartDate", challenge.getFechaI());
                challengeDetails.put("hackatonEndDate", challenge.getFechaT());
                challengeDetails.put("hackatonId", challenge.getId()); //
                challengesDetails.add(challengeDetails);
            }
        }

        // Verificar si hay desafíos encontrados
        if (challengesDetails.isEmpty()) {
            response.put("hackatonesDetails", "No ha participado en ningun Hackathon");
        } else {
            response.put("hackatonesDetails", challengesDetails);
        }

        return response;
    }

    public Map<String, Object> getHackathonDetailsParticipant(Long participantId, Long challengeId) {
        Map<String, Object> response = new HashMap<>();
        List<String> integrantesGrupo = new ArrayList<>();
        double notaGrupo = 0;
        int totalGrades = 0;
        List<Map<String, Object>> calificacionesGruposdeHackaton = new ArrayList<>();

        // Obtener al participante por ID
        Participant participant = participantRepository.findById(participantId).orElse(null);
        if (participant == null) {
            response.put("error", "Actor not found");
            return response;
        }

        // Agregar información del participante a la respuesta
        response.put("actorType", "participante");
        response.put("actorName", participant.getName() + " " + participant.getLastname());

        // Obtener el desafío por ID
        Challenge challenge = challengeRepository.findById(challengeId).orElse(null);
        if (challenge == null) {
            response.put("error", "Hackathon not found");
            return response;
        }
        response.put("hackathonName", challenge.getNombre());

        // Obtener el equipo donde el participante es líder y está asociado al desafío
        Team userTeam = teamRepository.findByChallengeAndLeader(challenge, participant);
        if (userTeam == null) {
            response.put("message", "Actor is not part of any team in this hackathon");
            return response;
        }

        // Agregar información del equipo a la respuesta
        response.put("teamName", userTeam.getTeamName());
        for (Member member : userTeam.getMembers()) {
            integrantesGrupo.add(member.getName());
        }
        response.put("integrantesGrupo", integrantesGrupo);

        // Calcular la nota del grupo
        List<Grades> gradesList = gradesRepository.findByChallengeId(challengeId);
        for (Grades grade : gradesList) {
            if (grade.getTeam().equals(userTeam) && grade.getAverageGradeJudge() != null) {
                notaGrupo += grade.getAverageGradeJudge();
                totalGrades++;
            }
        }

        if (totalGrades > 0) {
            response.put("notaGrupo", notaGrupo / totalGrades);
        } else {
            response.put("notaGrupo", "no hay notas ingresadas");
        }

        // Calcular promedio de calificaciones del hackathon
        Map<Long, Double> teamGradeSum = new HashMap<>();
        Map<Long, Integer> teamJudgeCount = new HashMap<>();
        for (Grades grade : gradesList) {
            if (grade.getAverageGradeJudge() != null) {
                teamGradeSum.merge(grade.getTeam().getTeamId(), grade.getAverageGradeJudge().doubleValue(), Double::sum);
                teamJudgeCount.merge(grade.getTeam().getTeamId(), 1, Integer::sum);
            }
        }

        double totalHackathonGrade = 0.0;
        int totalGroups = 0;
        for (Map.Entry<Long, Double> entry : teamGradeSum.entrySet()) {
            Long teamId = entry.getKey();
            Double totalScore = entry.getValue();
            Integer judgeCount = teamJudgeCount.get(teamId);

            if (totalScore != null && judgeCount != null && judgeCount > 0) {
                double averageGrade = totalScore / judgeCount;
                Map<String, Object> gradeDetails = new HashMap<>();
                gradeDetails.put("teamId", teamId);
                gradeDetails.put("groupGrade", averageGrade);
                calificacionesGruposdeHackaton.add(gradeDetails);

                totalHackathonGrade += averageGrade;
                totalGroups++;
            }
        }

        if (totalGroups > 0) {
            response.put("averageHackathonGrade", totalHackathonGrade / totalGroups);
        } else {
            response.put("averageHackathonGrade", "no hay notas ingresadas");
        }

        // Calcular el ranking de equipos
        calificacionesGruposdeHackaton.sort((a, b) -> Double.compare((Double) b.get("groupGrade"), (Double) a.get("groupGrade")));
        List<String> ranking = new ArrayList<>();
        for (int i = 0; i < calificacionesGruposdeHackaton.size() && i < 3; i++) {
            Map<String, Object> details = calificacionesGruposdeHackaton.get(i);
            Long teamId = (Long) details.get("teamId");
            Team rankedTeam = teamRepository.findById(teamId).orElse(null);
            if (rankedTeam != null) {
                ranking.add("Team: " + rankedTeam.getTeamName() + ", Position: #" + (i + 1));
            }
        }

        if (!ranking.isEmpty()) {
            response.put("ranking", ranking);
        } else {
            response.put("ranking", "no hay notas ingresadas para establecer ranking");
        }

        return response;
    }

    public Map<String, Object> getDetailsParticipantbyId(Long actorId) {
        // Inicializamos la respuesta que vamos a devolver
        Map<String, Object> response = new HashMap<>();

        // Obtener el participante (actor)
        Optional<Participant> participantOptional = participantRepository.findById(actorId);
        if (participantOptional.isEmpty()) {
            throw new IllegalArgumentException("Participante no encontrado");
        }
        Participant participant = participantOptional.get();

        // 1. actorType es "participante"
        response.put("actorType", "participante");

        // 2. actorName es el nombre del participante
        response.put("actorName", participant.getName() + " " + participant.getLastname());

        // 3. totalHackatonesParticipados
        Set<Long> uniqueHackathons = new HashSet<>();
        List<Team> teams = teamRepository.findByLeaderId(actorId);
        for (Team team : teams) {
            uniqueHackathons.add(team.getChallenge().getId()); // Se asegura que se cuente un hackatón una sola vez por participante
        }
        response.put("totalHackatonesParticipados", uniqueHackathons.size());

        // 4. numberOfVictories (promedio de grades)
        AtomicInteger victories = new AtomicInteger(0); // Usamos AtomicInteger para poder modificarlo dentro de la lambda
        boolean isSingleChallenge = teams.size() == 1; // Verificamos si está en un solo desafío

        for (Team team : teams) {
            List<Grades> gradesList = gradesRepository.findByTeamAndChallenge(team, team.getChallenge());
            if (gradesList.isEmpty()) continue;

            // Calculamos el promedio de las calificaciones de los jueces por equipo
            Optional<Float> maxAvgGrade = gradesList.stream()
                    .map(Grades::getAverageGradeJudge)
                    .filter(Objects::nonNull) // Filtramos los valores null
                    .max(Comparator.naturalOrder());

            // Si hay una calificación máxima, comparamos y sumamos la victoria
            maxAvgGrade.ifPresent(avg -> {
                if (avg != null && avg.equals(Collections.max(gradesList.stream()
                        .map(Grades::getAverageGradeJudge)
                        .filter(Objects::nonNull) // Filtramos los valores null
                        .collect(Collectors.toList())))) {
                    victories.incrementAndGet(); // Usamos incrementAndGet para modificar la variable
                }
            });
        }

        // Si está en un solo desafío y no se ha encontrado ninguna calificación, se asigna 0 victorias
        if (isSingleChallenge && victories.get() == 0) {
            victories.set(0);
        }

        response.put("numberOfVictories", victories.get());

        // 5. colaboradores
        Map<String, Integer> collaborators = new HashMap<>();
        for (Team team : teams) {
            for (Member member : team.getMembers()) {
                if (!member.getEmail().equals(participant.getEmail())) {
                    collaborators.put(member.getName(), collaborators.getOrDefault(member.getName(), 0) + 1);
                }
            }
        }

        // Si no hay colaboradores, se agrega el mensaje "no hay colaboradores en común"
        if (collaborators.isEmpty()) {
            response.put("collaborators", "no hay colaboradores en común");
        } else {
            List<Map<String, Object>> collaboratorList = collaborators.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> collaboratorData = new HashMap<>();
                        collaboratorData.put("collaboratorName", entry.getKey());
                        collaboratorData.put("collaborationsCount", entry.getValue());
                        return collaboratorData;
                    })
                    .collect(Collectors.toList());
            response.put("collaborators", collaboratorList);
        }

        return response;
    }


    public Long getIdByEmailAndActorType(String email, String actorType) {
        switch (actorType.toLowerCase()) {
            case "organization":
                return getIdFromOrganization(email);
            case "participant":
                return getIdFromParticipant(email);
            case "judge":
                return getIdFromJudge(email);
            default:
                throw new IllegalArgumentException("Tipo de actor desconocido: " + actorType);
        }
    }

    private Long getIdFromOrganization(String email) {
        Optional<Organization> organization = organizationRepository.findByEmail(email);
        return organization.map(Organization::getId).orElse(null);
    }

    private Long getIdFromParticipant(String email) {
        Optional<Participant> participant = participantRepository.findByEmail(email);
        return participant.map(Participant::getId).orElse(null);
    }

    private Long getIdFromJudge(String email) {
        Optional<Judge> judge = judgeRepository.findByEmail(email);
        return judge.map(Judge::getId).orElse(null);
    }
}

