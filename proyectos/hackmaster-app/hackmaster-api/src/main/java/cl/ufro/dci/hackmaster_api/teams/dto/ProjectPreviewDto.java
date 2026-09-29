package cl.ufro.dci.hackmaster_api.teams.dto;

import cl.ufro.dci.hackmaster_api.teams.domain.Project;
import lombok.Data;

/**
 * Representa un objeto de transferencia de datos (DTO) que contiene una vista previa de un proyecto.
 * Este DTO se utiliza para mostrar información resumida de un proyecto, como su ID, nombre y la URL de la foto asociada.
 *
 */

@Data
public class ProjectPreviewDto {

    private static final String IMAGE_BASE_URL = "http://localhost:8080/";

    /**
     * El identificador único del proyecto.
     *
     */

    private long id;

    /**
     * El nombre del proyecto.
     */

    private String name;

    /**
     * La URL de la foto asociada al proyecto.
     */

    private String photoUrl;

    /**
     * Convierte un objeto {@link Project} en un {@link ProjectPreviewDto}.
     *
     * Este método toma un objeto {@link Project} y crea una instancia de {@link ProjectPreviewDto}
     * copiando los valores de los atributos {@link Project#projectId}, {@link Project#projectName} y {@link Project#photoUrl}.
     *
     * @param project El proyecto del cual se crea la vista previa.
     * @return Una instancia de {@link ProjectPreviewDto} con los datos del proyecto.
     */

    public static ProjectPreviewDto fromProject(Project project) {
        var preview = new ProjectPreviewDto();
        preview.setId(project.getProjectId());
        preview.setName(project.getProjectName());
        preview.setPhotoUrl(IMAGE_BASE_URL + project.getPhotoUrl());
        return preview;
    }
}
