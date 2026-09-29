package cl.ufro.dci.naive_pay_api.reports.dto;

import cl.ufro.dci.naive_pay_api.payments.domain.State;

import java.util.List;

/**
 * DTO utilizado para representar los datos de un gráfico de transacciones por estado.
 * <p>
 * Esta clase contiene dos listas:
 * - Una lista de estados de transacciones (como strings), y
 * - Una lista de contadores (enteros) que representan la cantidad de transacciones en cada estado.
 *
 * @param statuses Lista de estados de las transacciones.
 * @param counts   Lista de contadores de transacciones por estado.
 * @author Francisco
 * @version 1.0
 */
public record TransactionsByStatusChartDto(
        List<State> statuses,
        List<Integer> counts
) {
}
