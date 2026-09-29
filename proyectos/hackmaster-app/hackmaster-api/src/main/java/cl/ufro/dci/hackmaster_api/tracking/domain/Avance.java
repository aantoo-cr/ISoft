package cl.ufro.dci.hackmaster_api.tracking.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;


@Entity
@Data
public class Avance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private LocalDate date;

    private Long idProyecto;

    private String urlAvance;
}
