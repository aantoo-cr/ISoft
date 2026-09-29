package cl.ufro.dci.hackmaster_api.registration.security;

import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
import cl.ufro.dci.hackmaster_api.registration.service.OrganizationService;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetails implements UserDetailsService {

  private final ParticipantService participantService;
  private final OrganizationService organizationService;

  public CustomUserDetails(ParticipantService participantService, OrganizationService organizationService) {
    this.participantService = participantService;
    this.organizationService = organizationService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<UserReg> userOptional = participantService
        .existsByEmail(email)
        .map(participant -> (UserReg) participant)
        .or(() -> organizationService
            .existsByEmail(email)
            .map(organization -> (UserReg) organization));

    if (userOptional.isEmpty()) {
      throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
    }

    return userOptional.get();
  }
}
