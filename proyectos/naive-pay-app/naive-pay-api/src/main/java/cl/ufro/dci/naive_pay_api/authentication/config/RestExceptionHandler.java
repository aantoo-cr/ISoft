package cl.ufro.dci.naive_pay_api.authentication.config;

import cl.ufro.dci.naive_pay_api.authentication.dto.ErrorDto;
import cl.ufro.dci.naive_pay_api.authentication.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Manejador global de excepciones para la aplicación.
 * Proporciona una forma centralizada de manejar excepciones específicas y devolver respuestas uniformes.
 *
 * @author Jesus
 * @version 1.0
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Maneja las excepciones de tipo AppException.
     * Devuelve un objeto de tipo ErrorDto con el mensaje de error y el estado HTTP correspondiente.
     *
     * @param ex La excepción AppException lanzada.
     * @return ResponseEntity que contiene el ErrorDto y el estado HTTP.
     */
    @ExceptionHandler(value = { AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorDto(ex.getMessage()));

    }
}
