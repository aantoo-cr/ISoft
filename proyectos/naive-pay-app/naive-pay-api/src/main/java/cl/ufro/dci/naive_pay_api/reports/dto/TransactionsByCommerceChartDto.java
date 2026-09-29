package cl.ufro.dci.naive_pay_api.reports.dto;

import java.util.List;

/**
 * DTO utilizado para representar los datos de un gráfico de transacciones por comercio.
 * <p>
 * Esta clase contiene dos listas:
 * - Una lista de nombres de comercios (de tipo String), y
 * - Una lista de montos (enteros) que representan la cantidad total de transacciones realizadas en cada comercio.
 *
 * @param commerces Lista de nombres de comercios en los que se realizaron las transacciones.
 * @param amounts   Lista de montos totales de las transacciones por comercio.
 * @author Francisco
 * @version 1.0
 */
public record TransactionsByCommerceChartDto(
        List<String> commerces,
        List<Integer> amounts
) {
}
