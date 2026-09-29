package cl.ufro.dci.naive_pay_api.reports.dto;

/**
 * DTO utilizado para encapsular los datos de los gráficos relacionados con las transacciones
 * en un informe.
 * <p>
 * Este DTO contiene diferentes representaciones gráficas de las transacciones, organizadas en categorías tales como
 * fecha, monto, estado, rango de monto, tipo de transacción y comercio. Cada uno de estos gráficos se representa
 * por un DTO específico para su respectivo conjunto de datos.
 *
 * @param transactionsByDateChartDto        DTO que contiene los datos para el gráfico de transacciones por fecha.
 * @param transactionAmountsByDateChartDto  DTO que contiene los datos para el gráfico de montos de transacciones por fecha.
 * @param transactionsByStatusChartDto      DTO que contiene los datos para el gráfico de transacciones por estado.
 * @param transactionsByAmountRangeChartDto DTO que contiene los datos para el gráfico de transacciones por rango de monto.
// * @param transactionsByTypeChartDto        DTO que contiene los datos para el gráfico de transacciones por tipo de servicio.
 * @param transactionsByCommerceChartDto    DTO que contiene los datos para el gráfico de transacciones por comercio.
 * @author Francisco
 * @version 1.0
 */
public record ChartsDataDto(
        TransactionsByDateChartDto transactionsByDateChartDto,
        TransactionAmountsByDateChartDto transactionAmountsByDateChartDto,
        TransactionsByStatusChartDto transactionsByStatusChartDto,
        TransactionsByAmountRangeChartDto transactionsByAmountRangeChartDto,
       // TransactionsByTypeChartDto transactionsByTypeChartDto,
        TransactionsByCommerceChartDto transactionsByCommerceChartDto
) {
}
