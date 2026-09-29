package cl.ufro.dci.hackmaster_api.challenges.service;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.challenges.dto.ChallengesDTO;
import cl.ufro.dci.hackmaster_api.challenges.repository.ChallengeRepository;
import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.repository.OrganizationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceImplements implements Service {
    private final List<Challenge> challenges = new ArrayList<>();
    private final ChallengeRepository challengeRepository;
    private final OrganizationRepository organizationRepository;
    private Long contadorId = 1L;

    public ServiceImplements(ChallengeRepository challengeRepository, OrganizationRepository organizationRepository) {
        this.challengeRepository = challengeRepository;
        this.organizationRepository = organizationRepository;
    }


    @Override
    public Challenge inscribirChallenge(ChallengesDTO challengeDTO) {
        // Validación para comprobar que no haya campos vacíos
        if (isInputInvalid(challengeDTO)) {
            throw new IllegalArgumentException("Todos los campos son obligatorios y no pueden estar vacíos.");
        }
        Organization organization = organizationRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("La organización con el ID especificado no existe."));


        // Si las validaciones pasan, creamos el nuevo desafío
        Challenge nuevoChallenge = new Challenge(contadorId++,
                challengeDTO.getNombre(),
                challengeDTO.getDescripcion(),
                challengeDTO.getRequisitos_Participantes(),
                challengeDTO.getFechaI(),
                challengeDTO.getFechaT(),
                challengeDTO.getCuposParticipantes(),
                challengeDTO.getUrlImagen(),
                false,
                challengeDTO.getCriteriosEval(),
                challengeDTO.getChallengePrice(),
                organization);

        //challengeDTO.getOrganization());

        challengeRepository.save(nuevoChallenge); // Guardar el nuevo desafío
        return nuevoChallenge; // Retornar el desafío creado
    }


    @Override
    public List<Challenge> listarChallenges() {
        List<Challenge> challenges = (List<Challenge>) challengeRepository.findAll(); // Suponiendo que este método obtiene todos los desafíos
        if (challenges.isEmpty()) {
            throw new NoSuchElementException("No hay desafíos disponibles."); // O puedes lanzar una excepción personalizada
        }
        return challenges;
    }

    public Optional<Challenge> viewChallenge(Long id) {
        return challengeRepository.findById(id);
    }

    @Override
    public Optional<Challenge> publicarChallenge(Long id) {
        Optional<Challenge> challenge = challengeRepository.findById(id);
        challenge.ifPresent(c -> {
            c.setEstado(!c.isEstado());
            challengeRepository.save(c);
        });
        return challenge;
    }
    @Override
    public Optional<Challenge> editarChallenge(Long id, ChallengesDTO challengeDTO) {
        // Validar los campos del DTO
        if (isInputInvalid(challengeDTO)) {
            throw new IllegalArgumentException("Los campos no pueden estar vacíos o ser inválidos."); // Lanza una excepción
        }

        Optional<Challenge> challenge = challengeRepository.findById(id);
        if (challenge.isPresent()) {
            Challenge c = challenge.get();
            c.setNombre(challengeDTO.getNombre());
            c.setDescripcion(challengeDTO.getDescripcion());
            c.setRequisitos_Participantes(challengeDTO.getRequisitos_Participantes());
            c.setFechaI(challengeDTO.getFechaI());
            c.setFechaT(challengeDTO.getFechaT());
            c.setCuposParticipantes(challengeDTO.getCuposParticipantes());
            c.setUrlImagen(challengeDTO.getUrlImagen());
            c.setCriteriosEval(challengeDTO.getCriteriosEval());
            c.setChallengePrice(challengeDTO.getChallengePrice());
            //c.setOrganization(challengeDTO.getOrganization());
            challengeRepository.save(c); // Guardar los cambios en la base de datos
            return Optional.of(c); // Retornar el desafío editado
        } else {
            return Optional.empty(); // Retornar vacío si no se encuentra el desafío
        }
    }

    @Override
    public boolean eliminarChallenge(Long id) {
        if (challengeRepository.existsById(id)) {
            challengeRepository.deleteById(id); // Eliminar de la base de datos
            return true;
        }
        return false;
    }

    private boolean isInputInvalid(ChallengesDTO challengeDTO) {
        // Comprobamos cada campo necesario asegurandonos de que no sea nulo ni vacío
        if (challengeDTO.getNombre() == null || challengeDTO.getNombre().trim().isEmpty() ||
                challengeDTO.getDescripcion() == null || challengeDTO.getDescripcion().trim().isEmpty() ||
                challengeDTO.getRequisitos_Participantes() == null || challengeDTO.getRequisitos_Participantes().trim().isEmpty() ||
                challengeDTO.getCuposParticipantes() == null || challengeDTO.getCuposParticipantes() <= 0 ||
                !isNumeric(challengeDTO.getCuposParticipantes().toString()) ||
                challengeDTO.getCuposParticipantes().toString().isEmpty() ||
                challengeDTO.getFechaI() == null || challengeDTO.getFechaT() == null){
            return true;
        }
        return false;

    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+"); // Verifica si el string contiene solo dígitos
    }
}