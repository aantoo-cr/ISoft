package cl.ufro.dci.naive_pay_api.registration.repository;

import cl.ufro.dci.naive_pay_api.registration.domain.Credential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repositorio para manejar las operaciones de acceso a datos relacionadas con las credenciales de los titulares de cuenta.
 * Esta interfaz extiende de {@link CrudRepository}, lo que permite realizar operaciones básicas de CRUD
 * (crear, leer, actualizar, eliminar) sobre las entidades {@link Credential}.
 *
 * El repositorio utiliza el {@link Long} como tipo de clave primaria, que corresponde al identificador único de cada {@link Credential}.
 *
 * @author Gerson, Camille
 */
public interface CredentialRepository extends CrudRepository<Credential, Long> {

    /**
     * Encuentra la credencial asociada al rut del titular de la cuenta.
     * @param rut el rut del titular de la cuenta.
     * @return la credencial asociada al rut.
     */
    Optional<Credential> findByAccountHolder_Rut(String rut);

    /**
     * Encuentra la clave pública asociada al rut del titular de la cuenta.
     * @param rut el rut del titular de la cuenta.
     * @return la clave pública como byte array.
     */
    Optional<byte[]> findPublicKeyByAccountHolder_Rut(String rut);

    /**
     * Encuentra el valor de 'g' asociado al rut del titular de la cuenta.
     * @param rut el rut del titular de la cuenta.
     * @return el valor 'g' como byte array.
     */
    Optional<byte[]> findGValueByAccountHolder_Rut(String rut);

    /**
     * Encuentra el valor de 'p' asociado al rut del titular de la cuenta.
     * @param rut el rut del titular de la cuenta.
     * @return el valor 'p' como byte array.
     */
    Optional<byte[]> findPValueByAccountHolder_Rut(String rut);
}
