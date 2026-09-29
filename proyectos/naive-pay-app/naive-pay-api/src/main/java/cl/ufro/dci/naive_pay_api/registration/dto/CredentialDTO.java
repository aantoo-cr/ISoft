package cl.ufro.dci.naive_pay_api.registration.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) para representar la información de la credencial.
 * Esta clase se utiliza para transferir los datos de una credencial, incluyendo la clave pública
 * y el estado de la clave, junto con la referencia al titular de la cuenta (identificado por su RUT).
 *
 * @author Gerson, Camille
 */
@Data
public class CredentialDTO {

    /**
     * Clave pública asociada a la credencial.
     * Esta clave se utiliza para la autenticación y encriptación de datos.
     */
    private String publicKey;

    /**
     * Estado de la clave asociada a la credencial.
     * Puede tener valores como 'ACTIVA' o 'INACTIVA'.
     */
    private String keyState;

    /**
     * RUT del titular de la cuenta que está asociado a esta credencial.
     * Este campo representa una referencia al titular de la cuenta.
     */
    private String rutAccountHolder;

    private String g;
    private String p;
}
