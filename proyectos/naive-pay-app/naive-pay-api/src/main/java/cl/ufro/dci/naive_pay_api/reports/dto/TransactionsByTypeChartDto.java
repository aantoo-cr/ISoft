package cl.ufro.dci.naive_pay_api.reports.dto;

import java.util.List;

/**
 * DTO utilizado para representar los datos de un gráfico de transacciones por tipo.
 * <p>
 * Esta clase contiene dos listas:
 * - Una lista de tipos de transacciones (como strings), y
 * - Una lista de contadores (enteros) que representan la cantidad de transacciones de cada tipo.
 *
 * @param types  Lista de tipos de transacciones.
 * @param counts Lista de contadores de transacciones por tipo.
 * @author Francisco
 * @version 1.0
 */
public record TransactionsByTypeChartDto(
        List<String> types,
        List<Integer> counts
) {
}
