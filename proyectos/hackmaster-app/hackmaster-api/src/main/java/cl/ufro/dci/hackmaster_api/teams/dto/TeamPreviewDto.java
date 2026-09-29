package cl.ufro.dci.hackmaster_api.teams.dto;

import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * DTO de vista previa de un equipo. Se utiliza para representar un equipo con información básica
 * como el nombre, la foto, los intereses, y los detalles sobre los miembros del equipo (si el usuario
 * tiene permisos de administrador).
 */

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeamPreviewDto {

    private static final String IMAGE_BASE_URL = "http://localhost:8080/";

    /**
     * El ID único del equipo.
     */

    private long id;

    /**
     * El nombre del equipo.
     */

    private String name;

    /**
     * La URL de la foto del equipo.
     */

    private String photoUrl;

    /**
     * Convierte un objeto {@link Team} en un {@code TeamPreviewDto} con la información básica del equipo.
     * Si el usuario es administrador, se incluyen detalles adicionales sobre los miembros y el límite.
     */

    public static TeamPreviewDto fromTeam(Team team) {
        var preview = new TeamPreviewDto();
        preview.setId(team.getTeamId());
        preview.setName(team.getTeamName());
        if (team.getProject() != null) {
            preview.setPhotoUrl(IMAGE_BASE_URL + team.getProject().getPhotoUrl());
        }
        return preview;
    }
}

