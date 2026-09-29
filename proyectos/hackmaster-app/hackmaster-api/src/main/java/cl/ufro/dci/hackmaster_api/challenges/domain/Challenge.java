package cl.ufro.dci.hackmaster_api.challenges.domain;

import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private String descripcion;
    private String requisitos_Participantes;
    private LocalDate fechaI;
    private LocalDate fechaT;
    private Integer cuposParticipantes;
    private String urlImagen;
    private boolean Estado;
    @ElementCollection
    @CollectionTable(name = "criterios_evaluacion")
    @Column(name = "criterio")
    private List<String> criteriosEval;
    private Float challengePrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

/*

    public Challenge(Long id, String nombre, String descripcion, String requisitos_Participantes, Date fechaI, Date fechaT, Integer cuposParticipantes, boolean Estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requisitos_Participantes = requisitos_Participantes;
        this.fechaI = fechaI;
        this.fechaT = fechaT;
        this.cuposParticipantes = cuposParticipantes;
        this.Estado = Estado;
    }

    public Challenge() {

    }
*/
}