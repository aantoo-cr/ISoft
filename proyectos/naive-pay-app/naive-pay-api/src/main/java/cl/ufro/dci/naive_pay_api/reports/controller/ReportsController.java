package cl.ufro.dci.naive_pay_api.reports.controller;

import cl.ufro.dci.naive_pay_api.payments.domain.State;
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.reports.dto.ChartsDataDto;
import cl.ufro.dci.naive_pay_api.reports.dto.ReportMetricsDto;
import cl.ufro.dci.naive_pay_api.reports.service.ReportsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador de la API de informes, encargado de manejar las solicitudes relacionadas con los informes de transacciones.
 * Proporciona varios puntos de entrada para consultar las transacciones, exportarlas a PDF,
 * obtener métricas y datos para gráficos.
 * <p>
 * Los endpoints proporcionados incluyen:
 * - /report/filtro: Filtra transacciones según parámetros específicos.
 * - /report/export/pdf/{userId}: Exporta las transacciones a un archivo PDF.
 * - /report/history/{userId}: Obtiene el historial completo de transacciones de un usuario.
 * - /report/metrics/{userId}: Obtiene las métricas calculadas para un usuario.
 * - /report/charts/{userId}: Obtiene los datos necesarios para los gráficos del usuario.
 * <p>
 * Este controlador se comunica con el servicio ReportsService para manejar la lógica de negocio y generar las respuestas.
 *
 * @author Francisco, Camilo
 * @version 1.0
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/report")
public class ReportsController {

    private final ReportsService reportsService;

    /**
     * Constructor de la clase ReportsController.
     *
     * @param reportsService El servicio encargado de manejar la lógica de negocio para los informes.
     */
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    /**
     * Endpoint para filtrar las transacciones según parámetros como ID, fechas y montos.
     *
     * @param accountNumber        El accountNumber de la transacción (puede ser nulo para no filtrar por ID).
     * @param startDate La fecha de inicio del rango de transacciones.
     * @param endDate   La fecha de fin del rango de transacciones.
     * @param minAmount El monto mínimo para las transacciones.
     * @param maxAmount El monto máximo para las transacciones.
     * @return Una lista de transacciones que coinciden con los parámetros de filtro, o un error si los parámetros son inválidos.
     */
    @GetMapping("/filtro/{accountNumber}/{startDate}/{endDate}/{minAmount}/{maxAmount}/{state}")
    public ResponseEntity<List<Transaction>> getFilteredTransactions(
            @PathVariable String accountNumber,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate,
            @PathVariable String minAmount,
            @PathVariable String maxAmount,
            @PathVariable String state) {

        // Manejo de valores "null"
        LocalDate start = startDate.equalsIgnoreCase("null") ? null : LocalDate.parse(startDate);
        LocalDate end = endDate.equalsIgnoreCase("null") ? null : LocalDate.parse(endDate);
        BigInteger min = minAmount.equalsIgnoreCase("null") ? null : new BigInteger(minAmount);
        BigInteger max = maxAmount.equalsIgnoreCase("null") ? null : new BigInteger(maxAmount);
        State stateFilter = state.equalsIgnoreCase("null") ? null : State.valueOf(state);
        List<Transaction> transactions = reportsService.getTransactionsByDateAndAmount(
                accountNumber, start, end, min, max, stateFilter);

        return ResponseEntity.ok(transactions);
    }
    /**
     * Endpoint para exportar las transacciones de un usuario a un archivo PDF.
     *
     * @param rut    El ID del usuario cuyos datos se van a exportar.
     * @param startDate La fecha de inicio del rango de transacciones.
     * @param endDate   La fecha de fin del rango de transacciones.
     * @param minAmount El monto mínimo para las transacciones.
     * @param maxAmount El monto máximo para las transacciones.
     * @return Un archivo PDF con las transacciones filtradas, o un error si los parámetros son inválidos.
     */
    @GetMapping("/export/{accountNumber}/{startDate}/{endDate}/{minAmount}/{maxAmount}/{state}")
    public ResponseEntity<byte[]> exportToPdf(
            @PathVariable String accountNumber,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate,
            @PathVariable String minAmount,
            @PathVariable String maxAmount,
            @PathVariable String state) {
        // Manejo de valores "null"
        LocalDate start = startDate.equalsIgnoreCase("null") ? null : LocalDate.parse(startDate);
        LocalDate end = endDate.equalsIgnoreCase("null") ? null : LocalDate.parse(endDate);
        BigInteger min = minAmount.equalsIgnoreCase("null") ? null : new BigInteger(minAmount);
        BigInteger max = maxAmount.equalsIgnoreCase("null") ? null : new BigInteger(maxAmount);
        State stateFilter = state.equalsIgnoreCase("null") ? null : State.valueOf(state);

        List<Transaction> transactions = reportsService.getTransactionsByDateAndAmount(accountNumber, start, end, min, max, stateFilter);
        byte[] pdfContent = reportsService.generateTransactionReportPdf(accountNumber, transactions);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=transactions.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }

    /**
     * Endpoint para obtener el historial completo de transacciones de un usuario.
     *
     * @param userId El ID del usuario cuyo historial de transacciones se desea obtener.
     * @return Una lista de transacciones asociadas al usuario.
     */
    @GetMapping(value = "/history/{userId}")
    public List<Transaction> getHistory(@PathVariable String userId) {
        return reportsService.getAllTransactions(userId);
    }

    /**
     * Endpoint para obtener las métricas calculadas para un usuario.
     *
     * @param userId El ID del usuario para el cual se calculan las métricas.
     * @return Un objeto ReportMetricsDto que contiene las métricas calculadas.
     */
    @GetMapping(value = "/metrics/{userId}")
    public ReportMetricsDto getMetrics(@PathVariable String userId) {
        return reportsService.calculateMetrics(userId);
    }

    /**
     * Endpoint para obtener los datos necesarios para los gráficos de un usuario.
     *
     * @param userId El ID del usuario cuyos datos se desean para los gráficos.
     * @return Un objeto ChartsDataDto con los datos necesarios para los gráficos.
     */
    @GetMapping(value = "/charts/{userId}")
    public ChartsDataDto getCharts(@PathVariable String userId) {
        return reportsService.getChartsData(userId);
    }
}
