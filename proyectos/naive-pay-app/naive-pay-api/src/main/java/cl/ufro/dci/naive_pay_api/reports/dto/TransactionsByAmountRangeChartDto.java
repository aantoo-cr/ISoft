package cl.ufro.dci.naive_pay_api.reports.dto;

import java.util.List;

/**
 * DTO utilizado para representar los datos de un gráfico de transacciones por rango de monto.
 * <p>
 * Esta clase contiene dos listas:
 * - Una lista de rangos de montos (enteros), y
 * - Una lista de frecuencias (enteras) que representan la cantidad de transacciones dentro de cada rango de monto.
 *
 * @param ranges      Lista de rangos de montos (por ejemplo, de 0 a 100, de 100 a 500, etc.).
 * @param frequencies Lista de frecuencias de transacciones en cada uno de esos rangos de monto.
 * @author Francisco
 * @version 1.0
 */
public record TransactionsByAmountRangeChartDto(
        List<Integer> ranges,
        List<Integer> frequencies
) {
}
