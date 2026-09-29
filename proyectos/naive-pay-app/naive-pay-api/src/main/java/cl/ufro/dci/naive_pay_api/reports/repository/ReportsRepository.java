package cl.ufro.dci.naive_pay_api.reports.repository;

import cl.ufro.dci.naive_pay_api.payments.domain.State;
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio de transacciones para acceder y manipular datos relacionados con las transacciones en la base de datos.
 * <p>
 * Esta interfaz extiende {@link CrudRepository} de Spring Data, proporcionando métodos para realizar operaciones
 * CRUD (Crear, Leer, Actualizar, Eliminar) sobre las entidades {@link Transaction}.
 * <p>
 * Los métodos definidos en esta interfaz son utilizados para interactuar con la base de datos, específicamente
 * para buscar transacciones en función del RUT de origen (userRut). Esta interfaz también se encarga de la gestión
 * de transacciones dentro de la base de datos.
 *
 * @author Francisco, Camilo
 * @version 1.0
 */
@Transactional
public interface ReportsRepository extends CrudRepository<Transaction, Long> {

    /**
     * Encuentra todas las transacciones que tienen el RUT de origen igual al proporcionado.
     *
     * @param accountNumber El RUT del usuario de origen para buscar las transacciones asociadas.
     * @return Una lista de transacciones asociadas al RUT de origen proporcionado.
     */
    List<Transaction> findAllByOriginAccount_AccountNumber(String accountNumber);

    @Query("SELECT t FROM Transaction t WHERE " +
            "(:originAccount IS NULL OR t.originAccount.accountNumber = :originAccount) AND " +
            "(:startDate IS NULL OR t.date >= :startDate) AND " +
            "(:endDate IS NULL OR t.date <= :endDate) AND " +
            "(:minAmount IS NULL OR t.amount >= :minAmount) AND " +
            "(:maxAmount IS NULL OR t.amount <= :maxAmount) AND " +
            "(:state IS NULL OR t.state = :state)")
    List<Transaction> findFilteredTransactions(
            @Param("originAccount") String originAccount,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("minAmount") BigInteger minAmount,
            @Param("maxAmount") BigInteger maxAmount,
            @Param("state") State state);
}
