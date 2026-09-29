package cl.ufro.dci.hackmaster_api.registration.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador personalizado para verificar la validez de un RUT chileno.
 * Implementa la interfaz {@link ConstraintValidator} para utilizarse como
 * validador en anotaciones de validación de Jakarta Bean Validation.
 */
public class RutValidator implements ConstraintValidator<ValidRut, String> {

  /**
   * Metodo para validar un RUT chileno.
   *
   * @param rut el RUT a validar, en formato XXXXXXXX-Y.
   * @param constraintValidatorContext el contexto de validación que se utiliza
   * para construir mensajes de error personalizados.
   * @return {@code true} si el RUT es válido, de lo contrario {@code false}.
   */
  @Override
  public boolean isValid(String rut, ConstraintValidatorContext constraintValidatorContext) {
    try {
      validateRut(rut);
      return true;
    } catch (IllegalArgumentException e) {
      constraintValidatorContext.disableDefaultConstraintViolation();
      constraintValidatorContext
          .buildConstraintViolationWithTemplate(
              "[RutValidator] RUT inválido -> Formato válido: XXXXXXXX-Y \n Error específico: " + e.getMessage())
          .addConstraintViolation();
      return false;
    }
  }

  /**
   * Metodo auxiliar que valida el formato y el dígito verificador de un RUT.
   *
   * @param rut el RUT a validar, sin puntos y con guion opcional.
   * @throws IllegalArgumentException si el RUT es nulo, vacío, tiene un formato
   * inválido, o el dígito verificador es incorrecto.
   */
  private void validateRut(String rut) {
    if (rut == null || rut.isEmpty()) {
      throw new IllegalArgumentException("[RutValidator] RUT inválido: no puede estar vacío o ser nulo");
    }

    rut = rut.replace(".", "").replace("-", "");

    if (rut.length() < 8 || rut.length() > 9) {
      throw new IllegalArgumentException("[RutValidator] RUT inválido: la longitud debe ser 8 o 9");
    }

    String numero = rut.substring(0, rut.length() - 1);
    String digitoVerificador = rut.substring(rut.length() - 1);

    if (!digitoVerificador.equals(getVerificationDigit(numero))) {
      throw new IllegalArgumentException("[RutValidator] Dígito verificador incorrecto");
    }
  }

  /**
   * Calcula el dígito verificador correspondiente a un número de RUT.
   *
   * @param rut el número del RUT (sin dígito verificador).
   * @return el dígito verificador calculado como {@code String}.
   */
  private String getVerificationDigit(String rut) {
    int sum = 0;
    int multiplier = 2;

    for (int i = rut.length() - 1; i >= 0; i--) {
      sum += Integer.parseInt(rut.substring(i, i + 1)) * multiplier;
      multiplier = multiplier == 7 ? 2 : multiplier + 1;
    }

    int result = 11 - (sum % 11);

    if (result == 11) {
      return "0";
    } else if (result == 10) {
      return "K";
    } else {
      return String.valueOf(result);
    }
  }
}
