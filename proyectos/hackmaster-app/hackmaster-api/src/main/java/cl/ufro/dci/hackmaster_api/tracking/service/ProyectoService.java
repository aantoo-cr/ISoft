package cl.ufro.dci.hackmaster_api.tracking.service;


import cl.ufro.dci.hackmaster_api.tracking.domain.Proyecto;
import cl.ufro.dci.hackmaster_api.tracking.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    private ProyectoRepository proyectRepository;

    public void ProyectService(ProyectoRepository proyectRepository) {
        this.proyectRepository = proyectRepository;
    }

    public Proyecto createProyect(Proyecto proyect) {
        return proyectRepository.save(proyect);
    }

    public Optional<Proyecto> getProyectById(int id) {
        return proyectRepository.findById(id);
    }

    public Proyecto updateProyect(int id, Proyecto proyectDetails) {
        Proyecto proyect = getProyectById(id).orElse(null); // Ajustado para Optional
        if (proyect != null) {
            proyect.setName(proyectDetails.getName());
            return proyectRepository.save(proyect);
        }
        return null;
    }

    public List<Proyecto> getAllProyect() {
        return (List<Proyecto>) proyectRepository.findAll();
    }
}
