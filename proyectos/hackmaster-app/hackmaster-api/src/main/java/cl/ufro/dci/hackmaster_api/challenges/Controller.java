package cl.ufro.dci.hackmaster_api.challenges;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.challenges.dto.ChallengesDTO;
import cl.ufro.dci.hackmaster_api.challenges.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200") // Permitir solicitudes desde el frontend Angular
@RestController
@RequestMapping("/api/challenges")
public class Controller {
    private final Service challengeService;

    public Controller(Service challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping("/inscribir")
    public ResponseEntity<?> inscribirChallenge(@RequestBody ChallengesDTO challengeDTO) {
        try {
            Challenge nuevoChallenge = challengeService.inscribirChallenge(challengeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoChallenge);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarChallenges() {
        try {
            List<Challenge> challenges = challengeService.listarChallenges();
            return ResponseEntity.ok(challenges);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/challenge/{id}")
    public ResponseEntity<?> viewChallenge(@PathVariable Long id) {
        Optional<Challenge> challenge = challengeService.viewChallenge(id);

        // Verificar si el desafío fue encontrado
        if (challenge.isPresent()) {
            return ResponseEntity.ok(challenge.get());
        } else {
            // Retornar mensaje si no se encuentra el desafío
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el desafío con ID: " + id);
        }
    }

    @PutMapping("/{id}/publicar")
    public ResponseEntity<Challenge> publicarChallenge(@PathVariable Long id) {
        Optional<Challenge> challengePublicado = challengeService.publicarChallenge(id);
        return challengePublicado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/nuevo")
    public ResponseEntity<?> editarChallenge(@PathVariable Long id, @RequestBody ChallengesDTO challengeDTO) {
        try {
            Optional<Challenge> updatedChallenge = challengeService.editarChallenge(id, challengeDTO);
            if (updatedChallenge.isPresent()) {
                return ResponseEntity.ok(updatedChallenge.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Challenge no encontrado");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Retornar el mensaje de error
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarChallenge(@PathVariable Long id) {
        boolean eliminado = challengeService.eliminarChallenge(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}