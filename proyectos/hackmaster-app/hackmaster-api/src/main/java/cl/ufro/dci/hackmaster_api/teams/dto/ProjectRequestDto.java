package cl.ufro.dci.hackmaster_api.teams.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Representa un objeto de transferencia de datos (DTO) para la creación de un proyecto.
 * Este DTO es utilizado para recibir los datos necesarios para crear un nuevo proyecto.
 */

@Data
public class ProjectRequestDto {

    /**
     * El nombre del proyecto.
     * No puede estar vacío ni ser nulo. Solo se permiten letras, espacios y caracteres acentuados.
     */

    @NotEmpty(message = "Project name should not be blank.")
    @NotNull(message = "Project name should not be null.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$", message = "The name can only contain letters, spaces, and accented characters.")
    private String name;

    /**
     * Descripción del proyecto.
     * No puede estar vacío ni ser nulo.
     */

    @NotEmpty(message = "Description should not be blank.")
    @NotNull(message = "Description should not be null.")
    private String description;

    /**
     * URL del repositorio del proyecto.
     * No puede estar vacío ni ser nulo.
     */

    @NotEmpty(message = "The url of repository should not be blank.")
    @NotNull(message = "The url of repository should not be null.")
    private String repositoryUrl;

    /**
     * Identificador del equipo asignado al proyecto.
     * Este valor no puede ser nulo.
     */

    @NotNull(message = "Team should not be null.")
    private long teamId;
}