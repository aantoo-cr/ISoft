package cl.ufro.dci.hackmaster_api.reports.controller;

import cl.ufro.dci.hackmaster_api.reports.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "localhost:4200")
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping("/getId/{email}/{actorType}")
    public Long getId(@PathVariable String email,@PathVariable String actorType){
        return reportsService.getIdByEmailAndActorType(email, actorType);
    }

    @GetMapping("/organizador/metricas/{actorId}")
    public Map<String, Object> getHackatonDetails(@PathVariable Long actorId) {
        return reportsService.getDetailsByOrganizerId(actorId);
    }

    @GetMapping("/organizador/lista/{actorId}")
    public Map<String, Object> getHackatonesByOrganizerId(@PathVariable Long actorId) {
        return reportsService.getDetailsListByOrganizerId(actorId);
    }

    @GetMapping("/organizador/hackaton/{actorId}/{hackathonId}")
    public Map<String, Object> getHackathonReport(@PathVariable Long actorId, @PathVariable Long hackathonId) {
        return reportsService.getHackathonDetailsOrganizer(actorId, hackathonId);
    }

    @GetMapping("/juez/lista/{judgeId}")
    public Map<String, Object> getHackathonsByJudgeId(@PathVariable Long judgeId) {
        return reportsService.getDetailsListByJudgeId(judgeId);
    }


    @GetMapping("/juez/hackaton/{judgeId}/{hackathonId}")
    public Map<String, Object> getHackathonReportByjudgeId(@PathVariable Long judgeId, @PathVariable Long hackathonId) {
        return reportsService.getHackathonDetailsJudge(judgeId, hackathonId);
    }

    @GetMapping("/participante/lista/{actorId}")
    public Map<String, Object> getDetailsListbyParticipantId(@PathVariable Long actorId) {
        return reportsService.getDetailsListbyParticipantId(actorId);
    }


    @GetMapping("/participante/hackaton/{actorId}/{hackathonId}")
    public Map<String, Object>getHackathonDetailsParticipant(@PathVariable Long actorId, @PathVariable Long hackathonId) {
        return reportsService.getHackathonDetailsParticipant(actorId, hackathonId);
    }

    @GetMapping("/participante/detalles/{actorId}")
    public Map<String, Object> getDetailsParticipantbyId(@PathVariable Long actorId) {
        return reportsService.getDetailsParticipantbyId((actorId));
    }

}
