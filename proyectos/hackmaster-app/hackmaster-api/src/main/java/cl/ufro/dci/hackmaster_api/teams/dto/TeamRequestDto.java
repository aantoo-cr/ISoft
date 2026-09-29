package cl.ufro.dci.hackmaster_api.teams.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

/**
 * DTO para la solicitud de creación de un equipo.
 * Este DTO recibe los datos del equipo como nombre, líder, descripción e intereses.
 * Los campos son validados para asegurar que los datos son correctos y completos.
 */

@Data
public class TeamRequestDto {

    /**
     * El nombre del equipo. No debe ser vacío ni nulo, y solo puede contener letras y espacios.
     *
     * @see #name
     */

    @NotEmpty(message = "Team name should not be blank.")
    @NotNull(message = "Team name should not be null.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$", message = "The name can only contain letters, spaces, and accented characters.")
    private String name;

    /**
     * El ID del líder del equipo. No debe ser nulo.
     *
     * @see #leaderId
     */

    @NotNull(message = "Leader should not be null.")
    private Long leaderId;
}

