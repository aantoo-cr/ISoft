package cl.ufro.dci.hackmaster_api.registration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailRequestDTO {

  @Email(message = "[EmailRequestDTO] Email debe ser válido")
  private String email;

  private String token;

  @Size(min = 8, message = "[EmailRequestDTO] Contraseña debe tener al menos 8 carácteres")
  private String newPassword;
}
