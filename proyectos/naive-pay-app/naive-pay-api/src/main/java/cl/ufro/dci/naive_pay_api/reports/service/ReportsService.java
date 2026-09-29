package cl.ufro.dci.naive_pay_api.reports.service;

import cl.ufro.dci.naive_pay_api.payments.domain.State;
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.reports.dto.ChartsDataDto;
import cl.ufro.dci.naive_pay_api.reports.dto.ReportMetricsDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionAmountsByDateChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByAmountRangeChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByCommerceChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByDateChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByStatusChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByTypeChartDto;
import cl.ufro.dci.naive_pay_api.reports.repository.ReportsRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Servicio encargado de manejar los reportes relacionados con las transacciones.
 *
 * Esta clase proporciona métodos para obtener, filtrar y calcular métricas de las transacciones, además de generar
 * reportes en formato PDF y construir datos para gráficos relacionados con las transacciones.
 *
 * Los métodos que contiene esta clase permiten obtener información detallada sobre las transacciones, como:
 * - Filtrar transacciones por fechas, monto, tipo de servicio, estado, dispositivo y comercio.
 * - Generar reportes PDF de las transacciones.
 * - Calcular métricas como el total de las transacciones, el promedio de los montos, y el número de comercios.
 * - Construir los datos necesarios para generar gráficos, como las transacciones por fecha, por estado, por rango de monto, etc.
 *
 * @author Francisco, Camilo
 * @version 1.0
 */
@Service
public class ReportsService {
    private final List<Transaction> transactions;
    private final ReportsRepository reportsRepository;

    /**
     * Constructor del servicio de reportes.
     *
     * @param reportsRepository El repositorio que se utilizará para acceder a las transacciones almacenadas en la base de datos.
     */
    public ReportsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
        this.transactions = List.of(); // Inicializar con datos reales
    }

    /**
     * Obtiene las transacciones filtradas por diferentes criterios, como fecha, monto, tipo de servicio, etc.
     *
     * @param rut El identificador del usuario.
     * @param startDate La fecha de inicio para filtrar las transacciones.
     * @param endDate La fecha de fin para filtrar las transacciones.
     * @param minAmount El monto mínimo para filtrar las transacciones.
     * @param maxAmount El monto máximo para filtrar las transacciones.
     * @param state El estado para filtrar las transacciones.
     * @return Una lista de transacciones filtradas según los parámetros proporcionados.
     */
    public List<Transaction> getTransactionsByDateAndAmount(
            String rut,
            LocalDate startDate,
            LocalDate endDate,
            BigInteger minAmount,
            BigInteger maxAmount,
            State state) {

        return reportsRepository.findFilteredTransactions(
                rut.isEmpty() ? null : rut,
                startDate,
                endDate,
                minAmount,
                maxAmount,
                state);
    }

    /**
     * Exporta las transacciones filtradas a un archivo PDF.
     *
     * @param accountNumber El identificador del usuario cuyas transacciones se desean exportar.
     * @param transactions las transacciones filtradas que se desean exportar.
     * @return Un arreglo de bytes representando el archivo PDF generado.
     */
    public byte[] generateTransactionReportPdf(String accountNumber, List<Transaction> transactions) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Reporte de Transacciones - NaivePay")
                    .setBold()
                    .setFontSize(25)
                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));
            document.add(new Paragraph("Numero de Cuenta: " + accountNumber).setFontSize(14).setMarginBottom(10));

            // Crear tabla
            float[] columnWidths = {50, 50, 50, 100, 50, 50, 100};
            Table table = new Table(columnWidths);

            // Encabezado de tabla
            String[] headers = {"Fecha", "Hora", "Monto", "Descripción", "Cuenta de Origen", "Cuenta de Destino", "Estado"};
            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header).setBold()));
            }

            // Agregar filas a la tabla
            for (Transaction transaction : transactions) {
                table.addCell(transaction.getDate() != null ? transaction.getDate().toString() : "N/A");
                table.addCell(transaction.getTime() != null ? transaction.getTime().toString() : "N/A");
                table.addCell(transaction.getAmount() != null ? transaction.getAmount().toString() : "N/A");
                table.addCell(transaction.getDescription() != null ? transaction.getDescription() : "N/A");
                table.addCell(transaction.getOriginAccount() != null ? transaction.getOriginAccount().getAccountNumber() : "N/A");
                table.addCell(transaction.getDestinationAccount() != null ? transaction.getDestinationAccount().getAccountNumber() : "N/A");
                table.addCell(transaction.getState() != null ? transaction.getState().toString() : "N/A");
            }
            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene todas las transacciones asociadas a un usuario específico.
     *
     * @param userId El identificador del usuario.
     * @return Una lista de todas las transacciones asociadas al usuario.
     */
    public List<Transaction> getAllTransactions(String userId) {
        return reportsRepository.findAllByOriginAccount_AccountNumber(userId);
    }

    /**
     * Calcula métricas relacionadas con las transacciones de un usuario, como el número total de transacciones,
     * el total acumulado de las transacciones, el promedio de los montos y la cantidad de comercios distintos.
     *
     * @param userId El identificador del usuario cuyas métricas se desean calcular.
     * @return Un objeto {@link ReportMetricsDto} con las métricas calculadas.
     */
    public ReportMetricsDto calculateMetrics(String userId) {
        var transactions = getAllTransactions(userId);

        int transactionsCount = countTransactions(transactions);

        if (transactionsCount == 0) {
            return new ReportMetricsDto(
                    transactionsCount,
                    BigInteger.ZERO,
                    0,
                    0
            );
        }

        var transactionsTotal = calculateTransactionsTotal(transactions);
        int transactionsAverage = transactionsTotal
                .divide(BigInteger.valueOf(transactionsCount))
                .intValue();
        int commerceCount = commerceCount(transactions);

        return new ReportMetricsDto(
                transactionsCount,
                transactionsTotal,
                transactionsAverage,
                commerceCount
        );
    }

    /**
     * Calcula el número de comercios distintos asociados a las transacciones de un usuario.
     *
     * @param transactions La lista de transacciones.
     * @return El número de comercios distintos.
     */
    public int commerceCount(List<Transaction> transactions) {
        return (int) transactions.stream()
                .map(Transaction::getDescription)
                .distinct()
                .count();
    }

    /**
     * Calcula el total acumulado de las transacciones en una lista.
     *
     * @param transactions La lista de transacciones.
     * @return El total acumulado de las transacciones.
     */
    public BigInteger calculateTransactionsTotal(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    /**
     * Cuenta el número total de transacciones en una lista de transacciones.
     *
     * @param transactions La lista de transacciones.
     * @return El número total de transacciones.
     */
    public int countTransactions(List<Transaction> transactions) {
        return transactions.size();
    }

    /**
     * Obtiene los datos necesarios para generar los gráficos de transacciones.
     *
     * @param userId El identificador del usuario cuyas transacciones se desean analizar.
     * @return Un objeto {@link ChartsDataDto} con los datos de los gráficos generados.
     */
    public ChartsDataDto getChartsData(String userId) {
        var allTransactions = getAllTransactions(userId);

        var transactionsByDateChartDto = buildTransactionsByDateChart(allTransactions);
        var transactionAmountsByDateChartDto = buildTransactionAmountsByDateChart(allTransactions);
        var transactionsByStatusChartDto = buildTransactionsByStatusChart(allTransactions);
        var transactionsByAmountRangeChartDto = buildTransactionsByAmountRangeChart(allTransactions);
  //      var transactionsByTypeChartDto = buildTransactionsByTypeChart(allTransactions);
        var transactionsByCommerceChartDto = buildTransactionsByCommerceChart(allTransactions);

        return new ChartsDataDto(transactionsByDateChartDto,
                transactionAmountsByDateChartDto,
                transactionsByStatusChartDto,
                transactionsByAmountRangeChartDto,
    //            transactionsByTypeChartDto,
                transactionsByCommerceChartDto);
    }

    /**
     * Construye los datos necesarios para generar un gráfico de montos de transacciones por fecha.
     *
     * @param transactions La lista de transacciones.
     * @return Un objeto {@link TransactionAmountsByDateChartDto} con los datos generados.
     */
    public TransactionsByDateChartDto buildTransactionsByDateChart(List<Transaction> transactions) {
        Map<LocalDate, Long> transactionsByDate = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getDate,
                        TreeMap::new,
                        Collectors.counting()));

        List<LocalDate> dates = new ArrayList<>();
        List<Integer> cumulativeCounts = new ArrayList<>();

        int cumulativeSum = 0;

        for (Map.Entry<LocalDate, Long> entry : transactionsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            Long count = entry.getValue();

            cumulativeSum += count;

            dates.add(date);
            cumulativeCounts.add(cumulativeSum);
        }

        return new TransactionsByDateChartDto(dates, cumulativeCounts);
    }

    /**
     * Construye los datos necesarios para generar un gráfico de montos de transacciones por fecha.
     *
     * @param transactions La lista de transacciones.
     * @return Un objeto {@link TransactionAmountsByDateChartDto} con los datos generados.
     */
    public TransactionAmountsByDateChartDto buildTransactionAmountsByDateChart(List<Transaction> transactions) {
        Map<LocalDate, BigInteger> amountsByDate = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.reducing(BigInteger.ZERO, Transaction::getAmount, BigInteger::add)
                ));

        var dates = amountsByDate.keySet().stream()
                .sorted()
                .toList();

        var amounts = dates.stream()
                .map(amountsByDate::get)
                .toList();

        return new TransactionAmountsByDateChartDto(dates, amounts);
    }

    /**
     * Construye los datos necesarios para generar un gráfico de transacciones por estado.
     *
     * @param transactions La lista de transacciones.
     * @return Un objeto {@link TransactionsByStatusChartDto} con los datos generados.
     */
    public TransactionsByStatusChartDto buildTransactionsByStatusChart(List<Transaction> transactions) {
        Map<State, Long> countsByStatus = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getState,
                        Collectors.counting()
                ));

        var statuses = new ArrayList<>(countsByStatus.keySet());
        var counts = countsByStatus.values().stream()
                .map(Long::intValue)
                .toList();

        return new TransactionsByStatusChartDto(statuses, counts);
    }

    /**
     * Construye los datos necesarios para generar un gráfico de transacciones por rango de montos.
     *
     * @param transactions La lista de transacciones.
     * @return Un objeto {@link TransactionsByAmountRangeChartDto} con los datos generados.
     */
    public TransactionsByAmountRangeChartDto buildTransactionsByAmountRangeChart(List<Transaction> transactions) {
        BigInteger maxAmount = transactions.stream()
                .map(Transaction::getAmount)
                .max(BigInteger::compareTo)
                .orElse(BigInteger.ZERO);

        int rangeCount = (int) (maxAmount.divide(BigInteger.valueOf(10000)).longValue() + 1);
        List<Integer> ranges = IntStream.range(0, rangeCount)
                .map(i -> i * 10000)
                .boxed()
                .collect(Collectors.toList());

        List<Integer> frequencies = new ArrayList<>(Collections.nCopies(rangeCount, 0));
        for (var transaction : transactions) {
            int index = transaction.getAmount().divide(BigInteger.valueOf(10000)).intValue();
            frequencies.set(index, frequencies.get(index) + 1);
        }

        return new TransactionsByAmountRangeChartDto(ranges, frequencies);
    }
/*
    /**
     * Construye los datos necesarios para generar un gráfico de transacciones por tipo.
     *
     * @param transactions La lista de transacciones.
     * @return Un objeto {@link TransactionsByTypeChartDto} con los datos generados.

    public TransactionsByTypeChartDto buildTransactionsByTypeChart(List<Transaction> transactions) {
        Map<String, Long> typeCounts = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::,
                        Collectors.counting()
                ));

        List<String> types = new ArrayList<>(typeCounts.keySet());
        List<Integer> counts = typeCounts.values().stream()
                .map(Long::intValue)
                .collect(Collectors.toList());

        return new TransactionsByTypeChartDto(types, counts);
    }
*/
    /**
     * Construye los datos necesarios para generar un gráfico de transacciones por comercio.
     *
     * @param transactions La lista de transacciones.
     * @return Un objeto {@link TransactionsByCommerceChartDto} con los datos generados.
     */
    public TransactionsByCommerceChartDto buildTransactionsByCommerceChart(List<Transaction> transactions) {
        Map<String, BigInteger> commerceAmounts = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        Collectors.reducing(BigInteger.ZERO, Transaction::getAmount, BigInteger::add)
                ));

        List<String> commerces = new ArrayList<>(commerceAmounts.keySet());
        List<Integer> amounts = commerceAmounts.values().stream()
                .map(BigInteger::intValue)
                .collect(Collectors.toList());

        return new TransactionsByCommerceChartDto(commerces, amounts);
    }

}