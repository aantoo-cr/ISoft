package cl.ufro.dci.hackmaster_api.tracking.domain;


import cl.ufro.dci.hackmaster_api.teams.domain.Team;
import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "idProyecto", cascade = CascadeType.ALL)
    private List<Avance> avances;

    private Long idEquipo;


    private String challenge;

    private LocalDate Fecha;
}