package cl.ufro.dci.hackmaster_api.tracking.controller;


import cl.ufro.dci.hackmaster_api.tracking.domain.Proyecto;
import cl.ufro.dci.hackmaster_api.tracking.service.ProyectoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class ProyectoController {

    private final ProyectoService proyectService;
    ProyectoController(ProyectoService proyectService) {
        this.proyectService = proyectService;
    }

    @PostMapping("/proyecto")
    public ResponseEntity<Proyecto> createProyect(@RequestBody Proyecto proyect) {
        Proyecto newProyect = proyectService.createProyect(proyect);
        return ResponseEntity.ok(newProyect);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> updateProyect(@PathVariable Integer id, @RequestBody Proyecto proyectDetails) {
        Proyecto updatedProyect = proyectService.updateProyect(id, proyectDetails);
        if (updatedProyect != null) {
            return ResponseEntity.ok(updatedProyect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
