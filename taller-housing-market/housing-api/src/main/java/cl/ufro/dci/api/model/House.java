package cl.ufro.dci.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houId;
    private String houAddress;
    private String houTitle;
    private String houDescription;
    private String houPhoto;
    private boolean houAvailable;

}
