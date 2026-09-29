package cl.ufro.dci.naive_pay_api.registration.dto;

import lombok.Data;

/**
 * Clase interna para estructurar la respuesta con el par de claves.
 * Contiene las claves pública y privada en formato byte[].
 */
@Data
public class KeyPairResponseDTO {
    private byte[] publicKey;
    private byte[] privateKey;
    private byte[] g;
    private byte[] p;

    /**
     * Constructor para crear una respuesta con las claves pública y privada.
     *
     * @param publicKey la clave pública generada.
     * @param privateKey la clave privada generada.
     */
    public KeyPairResponseDTO(byte[] publicKey, byte[] privateKey, byte[] g, byte[] p) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.g = g;
        this.p = p;
    }
}