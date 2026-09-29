package cl.ufro.dci.naive_pay_api.registration.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "credential")
public class Credential {

    // Atributos

    /**
     * Identificador único de la credencial.
     * Este campo se genera automáticamente mediante la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * La clave pública asociada a la credencial.
     * Almacena la clave pública utilizada para la autenticación o la verificación.
     */
    @Lob
    @Column(name = "public_key", columnDefinition = "BLOB")
    private String publicKey;

    /**
     * El estado de la clave.
     * Indica el estado actual de la credencial, que puede estar activa, inactiva, revocada, etc.
     */
    private KeyState keyState;

    /**
     * El titular de la cuenta asociado a esta credencial.
     * Establece una relación "muchos a uno" con la entidad `AccountHolder`,
     * donde varias credenciales pueden estar asociadas a un único titular de cuenta.
     */
    @ManyToOne
    @JoinColumn(name="rutAccountHolder", referencedColumnName = "rut")
    private AccountHolder accountHolder;

}

