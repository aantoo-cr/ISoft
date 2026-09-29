package cl.ufro.dci.hackmaster_api.rec_management.domain;

import cl.ufro.dci.hackmaster_api.challenges.domain.Challenge;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Challenge challenge;  // Relación ManyToOne con Challenge

    private String name;
    private String description;
    private Integer position;
    private Double valueReward;


}









