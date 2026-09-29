package cl.ufro.dci.naive_pay_api.devices.domain;

import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import jakarta.persistence.*;

/**
 * Entidad que representa un dispositivo en el sistema.
 * Esta clase contiene los detalles de un dispositivo, incluyendo el nombre, modelo, estado y código de verificación.
 * También establece una relación con el titular de la cuenta (AccountHolder).
 */

@Entity
public class Devices {

    /**
     * Identificador único del dispositivo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del dispositivo.
     */
    private String name;

    /**
     * Modelo del dispositivo.
     */
    private String modelo;

    /**
     * Estado del dispositivo.
     */
    private String status;

    /**
     * Código de verificación del dispositivo.
     */
    private Integer verificationCode;

    /**
     * Relación con el titular de la cuenta (AccountHolder).
     */
    @ManyToOne
    @JoinColumn(name = "account_holder_rut", referencedColumnName = "rut")
    private AccountHolder accountHolder;

    /**
     * Constructor sin argumentos.
     */
    public Devices() {
    }

    /**
     * Constructor con argumentos.
     *
     * @param name El nombre del dispositivo.
     * @param status El estado del dispositivo.
     * @param modelo El modelo del dispositivo.
     */
    public Devices(String name, String status, String modelo) {
        this.name = name;
        this.status = status;
        this.modelo = modelo;
    }

    /**
     * Obtiene el identificador único del dispositivo.
     *
     * @return El identificador único del dispositivo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del dispositivo.
     *
     * @param id El identificador único del dispositivo.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del dispositivo.
     *
     * @return El nombre del dispositivo.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del dispositivo.
     *
     * @param name El nombre del dispositivo.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el estado del dispositivo.
     *
     * @return El estado del dispositivo.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Establece el estado del dispositivo.
     *
     * @param status El estado del dispositivo.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtiene el modelo del dispositivo.
     *
     * @return El modelo del dispositivo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del dispositivo.
     *
     * @param modelo El modelo del dispositivo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el titular de la cuenta asociado al dispositivo.
     *
     * @return El titular de la cuenta asociado al dispositivo.
     */
    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    /**
     * Establece el titular de la cuenta asociado al dispositivo.
     *
     * @param accountHolder El titular de la cuenta asociado al dispositivo.
     */
    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    /**
     * Obtiene el código de verificación del dispositivo.
     *
     * @return El código de verificación del dispositivo.
     */
    public Integer getVerificationCode() {
        return verificationCode;
    }

    /**
     * Establece el código de verificación del dispositivo.
     *
     * @param verificationCode El código de verificación del dispositivo.
     */
    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     * Obtiene el correo electrónico del titular de la cuenta asociado al dispositivo.
     *
     * @return El correo electrónico del titular de la cuenta asociado al dispositivo.
     */
    public String getAccountHolderEmail() {
        if (accountHolder != null) {
            return accountHolder.getEmail();
        }
        return null;
    }
}
