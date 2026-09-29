package cl.ufro.dci.hackmaster_api.teams.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un miembro de un equipo.
 * Un miembro tiene un nombre y un correo electrónico.
 * Esta clase es utilizada dentro de la entidad {@link Team} para almacenar la información de los miembros
 * del equipo como un tipo embebido.
 *
 * Los miembros son almacenados en la base de datos en una tabla de colección {@code team_members},
 * donde cada miembro tiene su propio nombre y correo electrónico.
 */

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    /**
     * Nombre del miembro.
     * Este campo almacena el nombre del miembro, el cual se utiliza para identificarlo dentro del equipo.
     */

    private String name;

    /**
     * Correo electrónico del miembro.
     * Este campo almacena el correo electrónico del miembro, que puede ser utilizado para
     * contactar al miembro.
     */

    private String email;
}