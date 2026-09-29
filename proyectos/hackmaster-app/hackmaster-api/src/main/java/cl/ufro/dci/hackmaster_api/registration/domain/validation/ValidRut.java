package cl.ufro.dci.hackmaster_api.registration.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada para validar el formato y la validez de un RUT chileno.
 * Se utiliza junto con la implementación del validador {@link RutValidator}.
 *
 */
@Constraint(validatedBy = RutValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRut {
  /**
   * Mensaje de error predeterminado que se muestra si la validación falla.
   *
   * @return el mensaje de error.
   */
  String message() default "[ValidRut] RUT inválido -> Formato válido: XXXXXXXX-Y";

  /**
   * Grupos de validación a los que pertenece esta restricción.
   *
   * @return los grupos de validación.
   */
  Class<?>[] groups() default {};

  /**
   * Carga útil adicional que puede ser utilizada por los clientes de validación.
   *
   * @return las clases de carga útil.
   */
  Class<? extends Payload>[] payload() default {};

}
