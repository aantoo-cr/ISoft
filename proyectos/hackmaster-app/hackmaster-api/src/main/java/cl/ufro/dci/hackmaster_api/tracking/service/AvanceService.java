package cl.ufro.dci.hackmaster_api.tracking.service;

import cl.ufro.dci.hackmaster_api.tracking.DTO.AvanceDTO;
import cl.ufro.dci.hackmaster_api.tracking.domain.Avance;
import cl.ufro.dci.hackmaster_api.tracking.domain.Proyecto;
import cl.ufro.dci.hackmaster_api.tracking.repository.AvanceRepository;
import cl.ufro.dci.hackmaster_api.tracking.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AvanceService {

    //private final challengeRepositorio challengeRepository;
    private final AvanceRepository avanceRepository;
    private final ProyectoRepository proyectoRepository;

    // Constructor para la inyección de dependencias
    public AvanceService(AvanceRepository avanceRepository, ProyectoRepository proyectoRepository) {
        this.avanceRepository = avanceRepository;
        this.proyectoRepository = proyectoRepository;
    }


    public Avance guardar(Avance avance) {
        return avanceRepository.save(avance);
    }

    public List<Avance> obtenerAvances() {
        return avanceRepository.findAll();
    }

    public Optional<Avance> obtenerPorId(int id) {
        return avanceRepository.findById(id);
    }

    //Cambiar las metricas: cant avances, num miembros, horas trabajadas, fecha del ultimo avance
    public int CountAvances(Long idProyecto) {
        return (int) avanceRepository.findAll().stream()
                .filter(avance -> avance.getIdProyecto().equals(idProyecto))
                .count();
    }

    /*public int obtenerNumeroDeMiembros(Integer idProyecto) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(idProyecto);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            Long idEquipo = proyecto.getIdEquipo();
            if (idEquipo != null) { // Verifica si hay un ID de equipo asignado
                // Suponiendo que existe un método en equipoService para obtener un equipo por su ID
                Team equipo = equipoService.obtenerEquipoPorId(idEquipo);
                if (equipo != null && equipo.getMembers() != null) {
                    return equipo.getMembers().size();
                }
            }
        }
        return 0;
    }*/

    public int Horas(Integer idProyecto) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(idProyecto);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            LocalDate fechaInicio = proyecto.getFecha();
            LocalDate fechaUltimoAvance = avanceRepository.findAll().stream()
                    .filter(avance -> avance.getIdProyecto().equals(idProyecto))
                    .map(Avance::getDate)
                    .max(LocalDate::compareTo)
                    .orElse(fechaInicio);

            long diasTrabajados = ChronoUnit.DAYS.between(fechaInicio, fechaUltimoAvance);
            return (int) diasTrabajados * 8; // Suponiendo 8 horas de trabajo por día
        }
        return 0;
    }

    public LocalDate fechaUltimoAvance(Long idProyecto) {
        return avanceRepository.findAll().stream()
                .filter(avance -> avance.getIdProyecto().equals(idProyecto))
                .map(Avance::getDate)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    public Avance convertirDTOaAvance(AvanceDTO avanceDTO) {
        Avance avance = new Avance();
        avance.setDescription(avanceDTO.getAvaDescripcion());
        avance.setDate(avanceDTO.getAvaDate());
        avance.setIdProyecto(avanceDTO.getAvaProyectoId()); // Suponiendo que idProyecto es del tipo Long o Integer
        avance.setUrlAvance(avanceDTO.getAvaURL());
        return avance;
    }
}