package cl.ufro.dci.hackmaster_api.rec_management.controller;

import cl.ufro.dci.hackmaster_api.rec_management.domain.Prize;
import cl.ufro.dci.hackmaster_api.rec_management.service.PrizeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin (origins = "http://localhost:4200")
@RestController
@RequestMapping("/challenges/{challengeId}/prizes")
public class PrizeController {

    private static final Logger LOGGER = LogManager.getLogger(PrizeController.class);

    @Autowired
    private PrizeService prizeService;

    // Obtener todos los premios para un desafío
    @GetMapping
    public ResponseEntity<Iterable<Prize>> deployAllPrizes(@PathVariable Long challengeId) {
        LOGGER.info("Solicitando todos los premios para el challenge ID: {}", challengeId);
        Iterable<Prize> prizes = prizeService.deployAllPrizes(challengeId);
        return ResponseEntity.ok(prizes);
    }

    // Asignar un nuevo premio a un desafío (ruta separada)
    @PostMapping("/assign")
    public ResponseEntity<Prize> assignPrize(@PathVariable Long challengeId, @RequestBody Prize prize) {
        LOGGER.info("Creando nuevo premio para challenge ID {}: {}", challengeId, prize.getName());

        if (prize.getName() == null || prize.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Prize createdPrize = prizeService.assignPrize(challengeId, prize);
        if (createdPrize == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdPrize);
    }

    // Obtener un premio específico por ID
    @GetMapping("/{prizeId}")
    public ResponseEntity<Prize> getPrizeById(@PathVariable Long challengeId, @PathVariable Long prizeId) {
        Prize prize = prizeService.getPrizeById(challengeId, prizeId);
        return prize != null ? ResponseEntity.ok(prize) : ResponseEntity.notFound().build();
    }

    // Actualizar un premio específico
    @PutMapping("/{prizeId}")
    public ResponseEntity<Prize> updatePrize(@PathVariable Long challengeId, @PathVariable Long prizeId, @RequestBody Prize prize) {
        LOGGER.info("Actualizando premio con ID {} para challenge ID {}", prizeId, challengeId);

        Prize updatedPrize = prizeService.updatePrize(challengeId, prizeId, prize);
        return updatedPrize != null ? ResponseEntity.ok(updatedPrize) : ResponseEntity.notFound().build();
    }

    // Eliminar un premio específico
    @DeleteMapping("/{prizeId}")
    public ResponseEntity<Void> deletePrize(@PathVariable Long challengeId, @PathVariable Long prizeId) {
        prizeService.deletePrize(challengeId, prizeId);
        return ResponseEntity.noContent().build();
    }

    // Verificar si un desafío tiene premios
    @GetMapping("/has-prizes")
    public ResponseEntity<Boolean> hasPrizes(@PathVariable Long challengeId) {
        boolean hasPrizes = prizeService.hasPrizes(challengeId);
        return ResponseEntity.ok(hasPrizes);
    }
}






