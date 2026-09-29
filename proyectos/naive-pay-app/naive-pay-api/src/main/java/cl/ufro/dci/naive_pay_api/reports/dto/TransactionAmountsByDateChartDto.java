package cl.ufro.dci.naive_pay_api.reports.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO utilizado para representar los datos de un gráfico de montos de transacciones por fecha.
 * <p>
 * Esta clase contiene dos listas:
 * - Una lista de fechas, y
 * - Una lista de montos asociados a esas fechas, representados como {@link BigInteger}.
 *
 * @param dates   Lista de fechas asociadas a las transacciones.
 * @param amounts Lista de montos de las transacciones que ocurrieron en cada fecha.
 * @author Francisco
 * @version 1.0
 */
public record TransactionAmountsByDateChartDto(
        List<LocalDate> dates,
        List<BigInteger> amounts
) {
}
