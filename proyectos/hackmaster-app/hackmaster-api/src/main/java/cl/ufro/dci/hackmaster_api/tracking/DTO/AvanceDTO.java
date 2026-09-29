package cl.ufro.dci.hackmaster_api.tracking.DTO;


import lombok.Data;

import java.time.LocalDate;

@Data
public class AvanceDTO {
    private int avaId;
    private String avaDescripcion;
    private LocalDate avaDate;
    private Long avaProyectoId;
    private String avaURL;
}
