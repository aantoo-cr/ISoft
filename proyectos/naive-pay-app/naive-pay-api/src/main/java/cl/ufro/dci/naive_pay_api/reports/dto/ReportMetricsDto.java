package cl.ufro.dci.naive_pay_api.reports.dto;

import java.math.BigInteger;

/**
 * DTO utilizado para representar las métricas relacionadas con las transacciones en un informe.
 * <p>
 * Este DTO contiene información clave sobre las transacciones, incluyendo el número total de transacciones,
 * el monto total de las transacciones, el promedio de las transacciones y el número total de comercios involucrados.
 *
 * @param transactionsCount   Número total de transacciones realizadas.
 * @param transactionsTotal   Monto total de todas las transacciones realizadas.
 * @param transactionsAverage Promedio de los montos de las transacciones.
 * @param commercesCount      Número total de comercios involucrados en las transacciones.
 * @author Francisco
 * @version 1.0
 */
public record ReportMetricsDto(
        Integer transactionsCount,
        BigInteger transactionsTotal,
        Integer transactionsAverage,
        Integer commercesCount
) {
}
