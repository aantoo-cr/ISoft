package cl.ufro.dci.hackmaster_api.registration.service;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
import cl.ufro.dci.hackmaster_api.registration.dto.AuthResponse;
import cl.ufro.dci.hackmaster_api.registration.dto.LoginRequest;
import cl.ufro.dci.hackmaster_api.registration.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final ParticipantService participantService;
  private final OrganizationService organizationService;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  /**
   * Inicia sesión con un usuario existente.
   */
  public AuthResponse loginParticipant(LoginRequest request) {
    String email = request.getEmail();
    String password = request.getPassword();

    // Verificar si existe como participante
    UserReg participant = participantService.existsByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Participante no encontrado"));

    // Validar contraseña
    if (!passwordEncoder.matches(password, participant.getPassword())) {
      throw new IllegalArgumentException("Contraseña incorrecta");
    }

    // Generar token
    String token = jwtTokenProvider.generateToken(participant.getEmail());

    return new AuthResponse(participant.getUsername(), participant.getEmail(), token);
  }

  public AuthResponse loginOrganization(LoginRequest request) {
    String email = request.getEmail();
    String password = request.getPassword();

    // Verificar si existe como organización
    Organization organization = organizationService.existsByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Organización no encontrada"));

    // Validar estado de la organización
    if (organization.getStatus() != Organization.Status.APPROVED) {
      throw new IllegalArgumentException("La organización no está aprobada para acceder");
    }

    // Validar contraseña
    if (!passwordEncoder.matches(password, organization.getPassword())) {
      throw new IllegalArgumentException("Contraseña incorrecta");
    }

    // Generar token
    String token = jwtTokenProvider.generateToken(organization.getEmail());

    return new AuthResponse(organization.getUsername(), organization.getEmail(), token);
  }


  /**
   * Registra un nuevo participante.
   */
  public AuthResponse registerParticipant(Participant request) {
    Participant participant = prepareParticipant(request);
    participantService.register(participant);
    return generateAuthResponse(participant);
  }

  /**
   * Registra una nueva organización.
   */
  public AuthResponse registerOrganization(Organization request) {
    Organization organization = prepareOrganization(request);
    organizationService.register(organization);
    return generateAuthResponse(organization);
  }

  private Participant prepareParticipant(Participant request) {
    return new Participant(
        request.getUsername(),
        request.getEmail(),
        passwordEncoder.encode(request.getPassword()),
        request.getRut(),
        request.getName(),
        request.getLastname(),
        request.getCity(),
        request.getCountry(),
        request.getPhoneNumber());
  }

  private Organization prepareOrganization(Organization request) {
    return new Organization(
        request.getUsername(),
        request.getEmail(),
        passwordEncoder.encode(request.getPassword()),
        request.getSocialNameOrganization(),
        request.getRutOrganization(),
        request.getRutRepresentative(),
        request.getRepresentativeName(),
        Organization.Status.PENDING);
  }

  private AuthResponse generateAuthResponse(UserReg user) {
    String token = jwtTokenProvider.generateToken(user.getEmail());
    return new AuthResponse(user.getUsername(), user.getEmail(), token);
  }
}
