package cl.ufro.dci.hackmaster_api.teams.dto;

import cl.ufro.dci.hackmaster_api.teams.domain.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Representa la información de un miembro del equipo.
 * Esta clase es utilizada para transportar los datos de un miembro de equipo, como el nombre
 * y el correo electrónico
 *
 * Las validaciones aseguran que los datos recibidos sean correctos antes de ser procesados.
 * El nombre del miembro debe ser una cadena que solo contenga letras (incluyendo caracteres acentuados y la letra 'ñ') y espacios.
 * El correo electrónico debe tener un formato válido.
 */

@Data
public class MemberInfoDto {

    /**
     * Nombre del miembro del equipo.
     * Este campo debe ser obligatorio y no puede estar vacío ni nulo.
     * Solo se permite letras (mayúsculas y minúsculas), caracteres acentuados y la letra 'ñ', así como espacios.
     */

    @NotEmpty(message = "Member name should not be blank.")
    @NotNull(message = "Member name should not be null.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$", message = "The name can only contain letters, spaces, and accented characters.")
    private String name;

    /**
     * Correo electrónico del miembro del equipo.
     * Este campo debe ser obligatorio y no puede estar vacío ni nulo.
     * Se valida que el formato del correo electrónico sea correcto (por ejemplo, usuario@dominio.com).
     */

    @NotEmpty(message = "Email should not be blank.")
    @NotNull(message = "Email should not be null.")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7}$", message = "Invalid email format")
    private String email;

    /**
     * Constructor de la clase MemberInfoDto.
     *
     * @param name  El nombre del miembro.
     * @param email El correo electrónico del miembro.
     */

    public MemberInfoDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Convierte un objeto {@link MemberInfoDto} en un objeto {@link Member}.
     * Este método se utiliza para convertir un DTO en una entidad del dominio.
     *
     * @param info El objeto {@link MemberInfoDto} con la información del miembro.
     * @return El objeto {@link Member} con la información del miembro.
     */

    public static Member toMember(MemberInfoDto info) {
        return new Member(info.name, info.email);
    }

    /**
     * Convierte una lista de objetos {@link Member} en una lista de objetos {@link MemberInfoDto}.
     * Este método se utiliza para convertir una lista de entidades del dominio en una lista de DTOs.
     *
     * @param members Lista de objetos {@link Member} a convertir.
     * @return Lista de objetos {@link MemberInfoDto} que representan los miembros.
     */

    public static List<MemberInfoDto> fromMembers(List<Member> members) {
        return members.stream()
                .map(member -> {
                    var name = member.getName();
                    var email = member.getEmail();
                    return new MemberInfoDto(name, email);
                })
                .collect(Collectors.toList());
    }
}
