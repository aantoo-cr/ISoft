package cl.ufro.dci.hackmaster_api.teams.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Representa un proyecto en la plataforma.
 * Un proyecto tiene un ID único, nombre, descripción, una foto asociada, fecha de creación
 * y una URL de repositorio. Además, cada proyecto está asociado a un equipo específico.
 *
 */

@Entity
@Data
@Table(name = "projects")
public class Project {

    /**
     * Identificador único del proyecto.
     *
     * Este campo es generado automáticamente por la base de datos y es la clave primaria de la tabla.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    /**
     * Nombre del proyecto.
     *
     * Este campo no puede ser nulo y es único para cada proyecto.
     */

    @Column(nullable = false, unique = true)
    private String projectName;

    /**
     * Descripción del proyecto.
     *
     * Este campo no puede ser nulo y proporciona una breve descripción del proyecto.
     */

    @Column(nullable = false)
    private String description;

    /**
     * URL de la foto asociada al proyecto.
     *
     * Este campo contiene la URL de la imagen que representa al proyecto.
     */

    private String photoUrl;

    /**
     * Fecha de creación del proyecto.
     *
     * Este campo no puede ser nulo y contiene la fecha en la que el proyecto fue creado.
     */

    @Column(nullable = false)
    private LocalDate creationDate;

    /**
     * URL del repositorio del proyecto.
     *
     * Este campo es opcional. Almacena la URL del repositorio donde se encuentra el código del proyecto,
     * por ejemplo, en plataformas como GitHub, GitLab, etc.
     */

    @Column()
    private String repositoryUrl;

    /**
     * El equipo asignado a este proyecto.
     *
     * La relación entre el proyecto y el equipo es uno a uno, y esta propiedad es administrada
     * por la entidad {@link Team} a través de la propiedad `mappedBy`.
     *
     * @see Team
     */

    @OneToOne(fetch = FetchType.LAZY, cascade =
            { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "team_id")
    private Team team;
}
