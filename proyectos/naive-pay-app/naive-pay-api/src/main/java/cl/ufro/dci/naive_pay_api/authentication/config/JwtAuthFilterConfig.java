package cl.ufro.dci.naive_pay_api.authentication.config;

import cl.ufro.dci.naive_pay_api.authentication.utils.UserAuthProviderUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que intercepta las solicitudes HTTP para verificar la presencia de un token JWT en el encabezado
 * de autorización. Si el token es válido, se establece la autenticación en el contexto de seguridad.
 *
 * @author Jesus
 * @version 1.0
 */
@RequiredArgsConstructor
public class JwtAuthFilterConfig extends OncePerRequestFilter {

    private final UserAuthProviderUtils userAuthProviderUtils;

    /**
     * Metodo que se encarga de procesar cada solicitud HTTP.
     * Verifica la presencia de un token JWT en el encabezado de autorización y valida el token.
     * Si el token es válido, establece la autenticación en el contexto de seguridad.
     *
     * @param request La solicitud HTTP que se está procesando.
     * @param response La respuesta HTTP que se generará.
     * @param filterChain La cadena de filtros para continuar con el procesamiento de la solicitud.
     * @throws ServletException Si ocurre un error durante la ejecución del filtro.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");
            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    SecurityContextHolder.getContext().setAuthentication(userAuthProviderUtils.validateToken(authElements[1]));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
