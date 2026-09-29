package cl.ufro.dci.hackmaster_api.teams.domain;
import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.tracking.domain.Avance;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa un equipo en la plataforma.
 * Un equipo tiene un nombre, un líder y varios miembros que forman parte de él.
 * Los equipos también pueden estar relacionados con desafíos específicos.
 */

@Entity
@Data
@Table(name = "teams")
public class Team {

    /**
     * Identificador único del equipo.
     * Se genera automáticamente con la estrategia de IDENTITY.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    /**
     * Nombre del equipo.
     * Este campo no puede ser nulo y debe ser único en el sistema.
     */

    private String teamName;

    /**
     * Límite máximo de miembros permitidos en un equipo.
     * Este valor es constante y no puede cambiar.
     */

    /**
     * Fecha de creación del equipo.
     *
     * Este campo no puede ser nulo y contiene la fecha en la que el equipo fue creado.
     */

    @Column(nullable = false)
    private LocalDate creationDate;

    public static final int MEMBER_LIMIT = 7;

    /**
     * Lista de miembros que forman parte del equipo.
     * Cada miembro incluye información sobre el nombre y el correo electrónico,
     * representados por la clase embebida {@link Member}.
     * Esta lista está almacenada en la tabla 'team_members' con una relación de colección.
     */

    @ElementCollection
    @CollectionTable(name = "team_members", joinColumns = @JoinColumn(name = "team_id"))
    private List<Member> members;

    /**
     * El líder del equipo.
     * Esta relación es de muchos a uno con la entidad {@link Participant}.
     * Cada equipo debe tener un líder, quien es responsable del equipo.
     */

    @ManyToOne(fetch = FetchType.LAZY, cascade =
            { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "leader_id", nullable = false)
    private Participant leader;

    /**
     * El desafío al que está asociado el equipo.
     * Esta relación es de muchos a uno con la entidad {@link Challenge}.
     * El desafío puede ser nulo si el equipo no está participando en un desafío.
     */

    @ManyToOne(fetch = FetchType.LAZY, cascade =
            { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    /**
     * El proyecto al que está asociado el equipo.
     * Esta relación es de uno a uno con la entidad {@link Project}.
     * El proyecto puede ser nulo si el equipo aún no crea su proyecto.
     */

    @OneToOne(mappedBy = "team")
    private Project project;
}



