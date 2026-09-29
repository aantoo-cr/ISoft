package cl.ufro.dci.hackmaster_api.teams.dto;
import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import lombok.Data;
import java.util.List;

import static cl.ufro.dci.hackmaster_api.teams.dto.MemberInfoDto.fromMembers;

/**
 * Representa un DTO con la información de un equipo que se envía como respuesta desde la API.
 * Esta clase se utiliza para estructurar los datos del equipo antes de enviarlos al cliente,
 * incluyendo detalles como el identificador del equipo, nombre del equipo y la información de sus miembros.
 */

@Data
public class TeamInfoDto {
    /**
     * El nombre del equipo.
     */

    private String name;

    /**
     * Lista de miembros de los miembros del equipo.
     * Contiene las referencias a los objetos de tipo {@link MemberInfoDto}
     */

    private List<MemberInfoDto> members;

    /**
     * Convierte un objeto {@link Team} a un objeto {@code TeamInfoDto} para ser utilizado como salida de la API.
     * Este método toma la información del equipo y la adapta a un formato adecuado para la respuesta.
     *
     * @param team el equipo cuyo contenido será mapeado a {@code TeamInfoDto}
     * @return un objeto {@code TeamInfoDto} con la información mapeada desde el equipo
     */

    public static TeamInfoDto fromTeam(Team team) {
        var info = new TeamInfoDto();
        info.setName(team.getTeamName());
        info.setMembers(fromMembers(team.getMembers()));
        return info;
    }
}

