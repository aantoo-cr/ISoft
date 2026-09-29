package cl.ufro.dci.hackmaster_api.registration.service;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.repository.OrganizationRepository;
import cl.ufro.dci.hackmaster_api.registration.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio encargado de gestionar el proceso de restablecimiento de contraseñas para organizaciones y participantes.
 * Incluye la generación, validación y envío de tokens para el restablecimiento de contraseñas.
 */
@Service
@RequiredArgsConstructor
public class PasswordResetService {
  private final OrganizationRepository organizationRepository;
  private final ParticipantRepository participantRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;

  private static final long EXPIRATION_TIME = 5;

  private String generateToken() {
    return UUID.randomUUID().toString();
  }

  /**
   * Valida si el token recibido es válido. El token debe coincidir con el almacenado
   * y no debe estar expirado.
   *
   * @param storedToken El token almacenado en la base de datos.
   * @param expirationDate La fecha de expiración del token.
   * @param token El token recibido para validación.
   * @throws IllegalArgumentException Si el token es inválido o ha expirado.
   */
  private void validateToken(String storedToken, LocalDateTime expirationDate, String token) {
    System.out.println("Token almacenado: " + storedToken);
    System.out.println("Token recibido: " + token);
    System.out.println("Fecha de expiración: " + expirationDate);

    if (storedToken == null || !storedToken.equals(token) || expirationDate.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Token inválido");
    }
  }

  /**
   * Envía un token para restablecer la contraseña a través de un correo electrónico.
   * También actualiza la base de datos con el token y su fecha de expiración.
   *
   * @param email El correo electrónico del usuario (organización o participante).
   * @param token El token generado para el restablecimiento.
   * @param type El tipo de usuario (puede ser "organization" o "participant").
   */
  private void sendToken(String email, String token, String type) {
    emailService.sendEmail(email, "[PasswordResetService] Contraseña reestablecida",
        "Tu token para restablecer contraseña en HackMaster es: " + token);
    if (type.equals("organization")) {
      Organization organization = organizationRepository.findByEmail(email).orElseThrow();
      organization.setResetPasswordToken(token);
      organization.setResetPasswordExpires(LocalDateTime.now().plusMinutes(EXPIRATION_TIME));
      organizationRepository.save(organization);
    } else if (type.equals("participant")) {
      Participant participant = participantRepository.findByEmail(email).orElseThrow();
      participant.setResetPasswordToken(token);
      participant.setResetPasswordExpires(LocalDateTime.now().plusMinutes(EXPIRATION_TIME));
      participantRepository.save(participant);
    }
  }

  /**
   * Envía un token para restablecer la contraseña a la organización o participante
   * con el correo electrónico proporcionado.
   *
   * @param email El correo electrónico del usuario (organización o participante).
   * @throws IllegalArgumentException Si el correo electrónico no se encuentra en el sistema.
   */
  public void sendPasswordResetToken(String email) {
    Optional<Organization> organizationOpt = organizationRepository.findByEmail(email);
    Optional<Participant> participantOpt = participantRepository.findByEmail(email);

    if (organizationOpt.isPresent()) {
      String token = generateToken();
      sendToken(email, token, "organization");
    } else if (participantOpt.isPresent()) {
      String token = generateToken();
      sendToken(email, token, "participant");
    } else {
      throw new IllegalArgumentException("[PasswordResetService] Correo no encontrado");
    }
  }

  /**
   * Restablece la contraseña de un usuario (organización o participante) utilizando
   * el token proporcionado y la nueva contraseña.
   *
   * @param email El correo electrónico del usuario (organización o participante).
   * @param token El token de restablecimiento de contraseña.
   * @param newPassword La nueva contraseña para el usuario.
   * @throws IllegalArgumentException Si el correo electrónico no existe o el token es inválido.
   */
  public void resetPassword(String email, String token, String newPassword) {
    Optional<Organization> organizationOpt = organizationRepository.findByEmail(email);
    Optional<Participant> participantOpt = participantRepository.findByEmail(email);

    if (organizationOpt.isPresent()) {
      Organization organization = organizationOpt.get();
      validateToken(organization.getResetPasswordToken(), organization.getResetPasswordExpires(), token);
      organization.setPassword(passwordEncoder.encode(newPassword));
      organization.setResetPasswordToken(null);
      organization.setResetPasswordExpires(null);
      organizationRepository.save(organization);
    } else if (participantOpt.isPresent()) {
      Participant participant = participantOpt.get();
      validateToken(participant.getResetPasswordToken(), participant.getResetPasswordExpires(), token);
      participant.setPassword(passwordEncoder.encode(newPassword));
      participant.setResetPasswordToken(null);
      participant.setResetPasswordExpires(null);
      participantRepository.save(participant);
    } else {
      throw new IllegalArgumentException("[PasswordResetService] Ningún usuario fue encontrado con ese email");
    }
  }
}
