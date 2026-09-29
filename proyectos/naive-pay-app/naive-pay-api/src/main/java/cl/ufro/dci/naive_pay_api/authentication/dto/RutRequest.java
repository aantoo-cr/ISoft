package cl.ufro.dci.naive_pay_api.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una solicitud que contiene el RUT (Rol Único Tributario).
 * Este objeto es utilizado para transportar el RUT de un usuario en solicitudes que requieren dicha información.
 *
 * @author 00jesus
 * @version 1.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutRequest {

    /**
     * RUT (Rol Único Tributario) del titular de cuenta.
     * Este campo es utilizado para identificar de manera única al titular de cuenta en Chile.
     */
    private String rut;
}