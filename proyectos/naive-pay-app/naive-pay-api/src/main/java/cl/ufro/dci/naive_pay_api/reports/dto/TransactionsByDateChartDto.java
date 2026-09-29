package cl.ufro.dci.naive_pay_api.reports.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO utilizado para representar los datos de un gráfico de transacciones por fecha.
 * <p>
 * Esta clase contiene dos listas:
 * - Una lista de fechas (de tipo LocalDate), y
 * - Una lista de contadores (enteros) que representan la cantidad de transacciones realizadas en cada fecha.
 *
 * @param dates  Lista de fechas en las que se realizaron las transacciones.
 * @param counts Lista de contadores de transacciones por fecha.
 * @author Francisco
 * @version 1.0
 */
public record TransactionsByDateChartDto(
        List<LocalDate> dates,
        List<Integer> counts
) {
}
