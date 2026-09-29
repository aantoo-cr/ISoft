package cl.ufro.dci.hackmaster_api.challenges.dto;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO para representar los datos de un Challenge.
 */
@Data
public class ChallengesDTO {
    /**
     * Identificador único del desafío.
     */
    private Long id;
    /**
     * Nombre del desafío.
     */
    private String nombre;
    /**
     * Descripción detallada del desafío.
     */
    private String descripcion;
    /**
     * Requisitos para los participantes del desafío.
     */
    private String requisitos_Participantes;
    /**
     * Fecha de inicio del desafío.
     * Utiliza el formato yyyy-MM-dd.
     */
    private LocalDate fechaI;
    /**
     * Fecha de término del desafío.
     * Utiliza el formato yyyy-MM-dd.
     */
    private LocalDate fechaT;
    /**
     * Número máximo de cupos disponibles para participantes.
     */
    private Integer cuposParticipantes;
    /**
     * URL de la imagen asociada al desafío.
     * Debe ser un enlace válido a una imagen.
     */
    private String urlImagen;
    /**
     * Estado del desafío, donde `true` indica que está activo y
     * `false` que está inactivo.
     */
    private boolean estado;
    /**
     * Lista de criterios de evaluación ingresados por el usuario.
     * Mínimo de 1 criterio y máximo de 5.
     */
    private List<String> criteriosEval;
    /**
     * Valor de inscripcion para el desafio
     */
    private float challengePrice;
    /**
     * Nombre de la organizacion que crea el desafio
     */
    private Organization organization;
}