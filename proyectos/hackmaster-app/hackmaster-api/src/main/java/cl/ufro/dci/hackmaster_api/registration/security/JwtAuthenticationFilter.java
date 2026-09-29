package cl.ufro.dci.hackmaster_api.registration.security;

import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import cl.ufro.dci.hackmaster_api.registration.service.OrganizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final ParticipantService participantService;
  private final OrganizationService organizationService;

  /**
   * Constructor para inicializar los servicios requeridos.
   *
   * @param jwtTokenProvider    Proveedor de validación y análisis de tokens JWT.
   * @param participantService  Servicio para gestionar datos de participantes.
   * @param organizationService Servicio para gestionar datos de organizaciones.
   */
  public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, ParticipantService participantService,
      OrganizationService organizationService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.participantService = participantService;
    this.organizationService = organizationService;
  }

  /**
   * Extrae el token JWT del encabezado «Authorization» de la solicitud.
   *
   * @param request La solicitud HTTP.
   * @return El token JWT si está presente, o null si no lo está.
   */
  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  /**
   * Valida el token JWT proporcionado.
   *
   * @param token El token JWT.
   * @return True si el token es válido, False de lo contrario.
   */
  private boolean isTokenValid(String token) {
    return StringUtils.hasText(token) && jwtTokenProvider.validateToken(token);
  }

  /**
   * Autentica al usuario basado en el token JWT y establece la autenticación en
   * el contexto de seguridad.
   *
   * @param token   El token JWT.
   * @param request La solicitud HTTP.
   */
  private void authenticateUser(String token, HttpServletRequest request) {
    String email = jwtTokenProvider.getEmailFromToken(token);
    UserDetails userDetails = findUserByEmail(email);

    if (userDetails != null) {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  }

  /**
   * Busca un usuario por su correo electrónico en «ParticipantService» o
   * «OrganizationService».
   *
   * @param email El «correo» del usuario.
   * @return El objeto «UserDetailsx si se encuentra o null si no.
   */
  private UserDetails findUserByEmail(String email) {
    return participantService
        .existsByEmail(email)
        .map(participant -> (UserDetails) participant)
        .orElseGet(() -> organizationService
            .existsByEmail(email)
            .map(organization -> (UserDetails) organization)
            .orElse(null));
  }

  /**
   * Filtra las solicitudes HTTP entrantes para verificar la validez del token JWT
   * y autenticar al usuario si corresponde.
   *
   * @param request     La solicitud HTTP.
   * @param response    La respuesta HTTP.
   * @param filterChain La cadena de filtros.
   * @throws ServletException Si ocurre una excepción de tipo servlet.
   * @throws IOException      Si ocurre una excepción de tipo IO.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromRequest(request);

    if (isTokenValid(token)) {
      authenticateUser(token, request);
    }

    filterChain.doFilter(request, response);
  }
}
