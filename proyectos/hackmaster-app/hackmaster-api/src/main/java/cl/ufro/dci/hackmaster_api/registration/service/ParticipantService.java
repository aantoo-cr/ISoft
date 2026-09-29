package cl.ufro.dci.hackmaster_api.registration.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la gestión de participantes, incluyendo su registro,
 * actualización,
 * inicio de sesión y verificación por correo electrónico.
 * Implementa la interfaz UserService para las operaciones comunes de usuario.
 */
@Service
@RequiredArgsConstructor
public class ParticipantService implements UserRegService<Participant> {

  private final ParticipantRepository participantRepository;

  public Iterable<Participant> findAll() {
    return participantRepository.findAll();
  }

  /**
   * Verifica si existe un participante con el correo electrónico proporcionado.
   *
   * @param email El correo electrónico del participante.
   * @return Un {@link Optional} que contiene el participante si existe, o vacío
   *         si no se encuentra.
   */
  public Optional<Participant> existsByEmail(String email) {
    return participantRepository.findByEmail(email);
  }

  /**
   * Registra un nuevo participante.
   * Si el correo electrónico ya existe, se lanza una excepción.
   *
   * @param participant El objeto de participante a registrar
   * @throws IllegalArgumentException Si el correo electrónico ya existe.
   */
  @Override
  public void register(Participant participant) {
    if (existsByEmail(participant.getEmail()).isPresent()) {
      throw new IllegalArgumentException("[ParticipantService] El email ya está registrado");
    }
    participantRepository.save(participant);
  }

  /**
   * Actualiza los detalles de un participante existente.
   * Si el participante no existe o el token está expirado, no se realiza la
   * actualización.
   * También verifica que la contraseña coincida con la almacenada.
   *
   * @param participantDto El objeto de participante con los nuevos detalles.
   * @return Un {@link Optional} que contiene el participante actualizado si la
   *         actualización es exitosa,
   *         o vacío si no fue posible realizarla.
   */
  public Optional<Participant> update(Participant participantDto) {
    Optional<Participant> participantOpt = participantRepository.findById(participantDto.getId());

    if (participantOpt.isPresent()) {
      Participant existingParticipant = participantOpt.get();
      existingParticipant.setUsername(participantDto.getUsername());
      existingParticipant.setPassword(participantDto.getPassword());
      existingParticipant.setEmail(participantDto.getEmail());
      existingParticipant.setName(participantDto.getName());
      existingParticipant.setLastname(participantDto.getLastname());
      existingParticipant.setCity(participantDto.getCity());
      existingParticipant.setCountry(participantDto.getCountry());
      existingParticipant.setPhoneNumber(participantDto.getPhoneNumber());

      participantRepository.save(existingParticipant);
      return Optional.of(existingParticipant);
    }

    return Optional.empty();
  }

  /**
   * Busca un participante por su ID.
   *
   * @param id El ID del participante a buscar.
   * @return Un {@link Optional} que contiene el participante si se encuentra, o
   *         vacío si no se encuentra.
   */
  @Override
  public Optional<Participant> findById(Long id) {
    return participantRepository.findById(id);
  }

  /**
   * Realiza el inicio de sesión de un participante con su correo electrónico y
   * contraseña.
   * Si las credenciales son correctas, se genera un token de acceso.
   *
   * @param email    El correo electrónico del participante.
   * @param password La contraseña del participante.
   * @return El participante con el token de acceso generado.
   * @throws IllegalArgumentException Si el correo electrónico no existe o la
   *                                  contraseña es incorrecta.
   */
  public Participant login(String email, String password) {
    Optional<Participant> participantOpt = participantRepository.findByEmail(email);

    if (participantOpt.isPresent()) {
      Participant participant = participantOpt.get();
      if (participant.getPassword().equals(password)) {
        participant.setTokenAcess(participant.generateToken(email));
        return participant;
      } else {
        throw new IllegalArgumentException("[ParticipantService] Contraseña incorrecta");
      }
    } else {
      throw new IllegalArgumentException("[ParticipantService] El Email no existe");
    }
  }


}
