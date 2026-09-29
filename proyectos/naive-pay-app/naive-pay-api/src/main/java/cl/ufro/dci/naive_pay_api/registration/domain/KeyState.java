package cl.ufro.dci.naive_pay_api.registration.domain;

/**
 * Enum que representa los posibles estados de una clave.
 * Se utiliza para indicar si una clave (como una credencial o clave pública)
 * está activa o inactiva en el sistema.
 *
 * Los estados posibles son:
 * - ACTIVE: La clave está activa y se puede usar.
 * - INACTIVE: La clave está inactiva y no se puede utilizar.
 *
 * @author Gerson y Camille
 */
public enum KeyState {

    /**
     * El estado activo indica que la clave está habilitada y operativa.
     */
    ACTIVE,

    /**
     * El estado inactivo indica que la clave ha sido deshabilitada
     * y no se puede utilizar en el sistema.
     */
    INACTIVE
}
