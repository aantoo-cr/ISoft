package cl.ufro.dci.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.Data;


@Entity
@Data
@Transactional
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proId;
    private String proName;
    private String proDescription;
    private Long proPrice;
    private String proBrand;
    private String proImage;


}
