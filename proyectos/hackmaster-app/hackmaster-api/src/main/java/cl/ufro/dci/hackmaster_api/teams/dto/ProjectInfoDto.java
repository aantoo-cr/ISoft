package cl.ufro.dci.hackmaster_api.teams.dto;

import cl.ufro.dci.hackmaster_api.teams.domain.Project;
import lombok.Data;

/**
 * Representa un DTO con la información de un proyecto que se envía como respuesta desde la API.
 * Esta clase se utiliza para estructurar los datos del proyecto antes de enviarlos al cliente,
 * incluyendo detalles como el identificador del proyecto, nombre del proyecto, descripción, foto y la url del repositorio.
 */

@Data
public class ProjectInfoDto {

    private static final String IMAGE_BASE_URL = "http://localhost:8080/";

    /**
     * El identificador único del proyecto.
     *
     */

    private long id;

    /**
     * El nombre del proyecto.
     *
     */

    private String name;

    /**
     * Una descripción detallada del proyecto.
     *
     */

    private String description;

    /**
     * La URL de la foto asociada al proyecto.
     *
     */

    private String photoUrl;

    /**
     * La URL del repositorio del proyecto.
     *
     */

    private String repositoryUrl;

    /**
     * Convierte un objeto {@link Project} en un {@link ProjectInfoDto}.
     *
     * Este método toma un objeto {@link Project} y crea una instancia de {@link ProjectInfoDto},
     * copiando los valores de los atributos {@link Project#projectId}, {@link Project#projectName},
     * {@link Project#description}, {@link Project#photoUrl} y {@link Project#repositoryUrl}.
     *
     * @param project El proyecto del cual se crea la información detallada.
     * @return Una instancia de {@link ProjectInfoDto} con los datos mapeados del proyecto.
     */

    public static ProjectInfoDto fromProject(Project project) {
        var info = new ProjectInfoDto();
        info.setId(project.getProjectId());
        info.setName(project.getProjectName());
        info.setDescription(project.getDescription());
        info.setPhotoUrl(IMAGE_BASE_URL + project.getPhotoUrl());
        info.setRepositoryUrl(project.getRepositoryUrl());
        return info;
    }
}
