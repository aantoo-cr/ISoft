package cl.ufro.dci.hackmaster_api.tracking.controller;

import cl.ufro.dci.hackmaster_api.tracking.DTO.AvanceDTO;
import cl.ufro.dci.hackmaster_api.tracking.domain.Avance;
import cl.ufro.dci.hackmaster_api.tracking.service.ProyectoService;
import cl.ufro.dci.hackmaster_api.tracking.domain.Proyecto;
import cl.ufro.dci.hackmaster_api.tracking.service.AvanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/avances")
public class AvanceController {

    private final AvanceService avanceService;
    private final ProyectoService proyectService;

    // Constructor para la inyección de dependencias
    public AvanceController(AvanceService avanceService, ProyectoService proyectService) {
        this.avanceService = avanceService;
        this.proyectService = proyectService;
    }

    //@GetMapping("/challenges")
    //public List<String> getChallengeNames() {
    //    return avanceService.getAllChallengeNames();
    //}

    @GetMapping("/proyects")
    public List<Proyecto> getAllProyect() {
        return proyectService.getAllProyect();
    }

    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable int id) {
        return proyectService.getProyectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/avances")
    public List<Avance> obtenerAvances() {
        return avanceService.obtenerAvances();
    }

    @PostMapping("/avance")
    public ResponseEntity<Avance> crearAvance(@RequestBody Avance avance) {
        Avance avanceGuardado = avanceService.guardar(avance);
        return ResponseEntity.ok(avanceGuardado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avance> obtenerAvancePorId(@PathVariable int id) {
        return avanceService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avance> actualizarAvance(@PathVariable int id, @RequestBody Avance avanceActualizado) {
        return avanceService.obtenerPorId(id)
                .map(avance -> {
                    avance.setDescription(avanceActualizado.getDescription());
                    avance.setDate(avanceActualizado.getDate());
                    avance.setUrlAvance(avanceActualizado.getUrlAvance());
                    avanceService.guardar(avance);
                    return ResponseEntity.ok(avance);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/countAvances/{idProyecto}")
    public ResponseEntity<Integer> countAvances(@PathVariable Long idProyecto) {
        int count = avanceService.CountAvances(idProyecto);
        return ResponseEntity.ok(count);
    }

    // Obtener el número de miembros de un proyecto a través de su equipo
    /*@GetMapping("/numMiembros/{idProyecto}")
    public ResponseEntity<Integer> numMiembros(@PathVariable Integer idProyecto) {
        int numMiembros = avanceService.NumMiembros(idProyecto);
        return ResponseEntity.ok(numMiembros);
    }*/

    // Calcular las horas trabajadas en un proyecto
    @GetMapping("/horasTrabajadas/{idProyecto}")
    public ResponseEntity<Integer> horasTrabajadas(@PathVariable Integer idProyecto) {
        int horas = avanceService.Horas(idProyecto);
        return ResponseEntity.ok(horas);
    }

    // Obtener la fecha del último avance de un proyecto
    @GetMapping("/fechaUltimoAvance/{idProyecto}")
    public ResponseEntity<LocalDate> fechaUltimoAvance(@PathVariable Long idProyecto) {
        LocalDate fechaUltimoAvance = avanceService.fechaUltimoAvance(idProyecto);
        if (fechaUltimoAvance != null) {
            return ResponseEntity.ok(fechaUltimoAvance);
        }
        return ResponseEntity.notFound().build();
    }
}
