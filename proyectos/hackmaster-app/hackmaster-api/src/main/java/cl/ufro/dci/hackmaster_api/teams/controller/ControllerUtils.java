package cl.ufro.dci.hackmaster_api.teams.controller;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase de utilidad para controlar las excepciones en los controladores.
 * Proporciona un método para obtener el código de estado HTTP correspondiente a una excepción específica.
 *
 */

public class ControllerUtils {

    /**
     * Obtiene el código de estado HTTP correspondiente a una excepción.
     *
     * @param ex La excepción para la cual se obtiene el código de estado HTTP.
     * @return El código de estado HTTP correspondiente.
     */
    public static HttpStatus getHttpStatus(Exception ex) {
        var exceptionStatusMap = Map.of(
                EntityNotFoundException.class, HttpStatus.NOT_FOUND,
                EntityExistsException.class, HttpStatus.CONFLICT,
                IllegalArgumentException.class, HttpStatus.BAD_REQUEST
        );
        return exceptionStatusMap.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Procesa los errores de validación y devuelve un mapa de campos con los mensajes de error.
     *
     * @param ex La excepción de validación {@link MethodArgumentNotValidException}.
     * @return Un mapa donde las claves son los nombres de los campos y los valores los mensajes de error.
     */
    public static Map<String, String> getValidationErrors(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}