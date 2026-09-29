package cl.ufro.dci.hackmaster_api.registration.controller;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.dto.AuthResponse;
import cl.ufro.dci.hackmaster_api.registration.dto.LoginRequest;
import cl.ufro.dci.hackmaster_api.registration.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login/participant")
  public ResponseEntity<AuthResponse> loginParticipant(@RequestBody @Valid LoginRequest loginRequest) {
    AuthResponse response = authService.loginParticipant(loginRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login/organization")
  public ResponseEntity<AuthResponse> loginOrganization(@RequestBody @Valid LoginRequest loginRequest) {
    AuthResponse response = authService.loginOrganization(loginRequest);
    return ResponseEntity.ok(response);
  }



  @PostMapping("/register/participant")
  public AuthResponse registerParticipant(@Valid @RequestBody Participant request) {
    return authService.registerParticipant(request);
  }

  @PostMapping("/register/organization")
  public AuthResponse registerOrganization(@Valid @RequestBody Organization request) {
    return authService.registerOrganization(request);
  }

}
