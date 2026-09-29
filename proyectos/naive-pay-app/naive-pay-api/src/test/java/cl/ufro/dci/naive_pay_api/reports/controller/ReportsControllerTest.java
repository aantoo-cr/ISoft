package cl.ufro.dci.naive_pay_api.reports.controller;
/*
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.registration.controller.BankAccountController;
import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import cl.ufro.dci.naive_pay_api.reports.dto.ChartsDataDto;
import cl.ufro.dci.naive_pay_api.reports.dto.ReportMetricsDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionAmountsByDateChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByAmountRangeChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByCommerceChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByDateChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByStatusChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByTypeChartDto;
import cl.ufro.dci.naive_pay_api.reports.service.ReportsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReportsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportsService reportsService;

    private String userId;

    @BeforeEach
    public void setUp() {
        userId = "123456789";
    }

    @Test
    @DisplayName("Get transaction history for non-existent user - GET /history/{userId}")
    public void testGetHistoryNonExistentUser() throws Exception {
        String userId = "nonExistentUser";
        Mockito.when(reportsService.getAllTransactions(userId)).thenReturn(Arrays.asList());

        mockMvc.perform(MockMvcRequestBuilders.get("/report/history/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @DisplayName("Filter transactions with incorrect date range - GET /filtro")
    public void testFilterTransactionsIncorrectDateRange() throws Exception {
        String userId = "206447261";
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        BigInteger minAmount = new BigInteger("10");
        BigInteger maxAmount = new BigInteger("100");

        Mockito.when(reportsService.getTransactionsByDateAndAmount(userId, startDate, endDate, minAmount, maxAmount,null))
                .thenReturn(Arrays.asList());

        mockMvc.perform(MockMvcRequestBuilders.get("/report/filtro")
                        .param("id", userId)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("minAmount", minAmount.toString())
                        .param("maxAmount", maxAmount.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @DisplayName("Filter transactions with impossible amount - GET /filtro")
    public void testFilterTransactionsImpossibleAmount() throws Exception {
        String userId = "206447261";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        BigInteger minAmount = new BigInteger("-10");
        BigInteger maxAmount = new BigInteger("-1");

        Mockito.when(reportsService.getTransactionsByDateAndAmount(userId, startDate, endDate, minAmount, maxAmount, null))
                .thenReturn(Arrays.asList());

        mockMvc.perform(MockMvcRequestBuilders.get("/report/filtro")
                        .param("id", userId)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("minAmount", minAmount.toString())
                        .param("maxAmount", maxAmount.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @DisplayName("Filter transactions with correct parameters - GET /filtro")
    public void testFilterTransactionsCorrectParameters() throws Exception {
        String userId = "206447261";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        BigInteger minAmount = new BigInteger("10");
        BigInteger maxAmount = new BigInteger("100");
        BankAccount originAccount = new BankAccount();
        Transaction transaction1 = new Transaction();
        transaction1.setOriginAccount(originAccount.setAccountNumber(userId));
        transaction1.setDate(LocalDate.of(2023, 6, 15));
        transaction1.setAmount(new BigInteger("50"));

        List<Transaction_report> transactions = Arrays.asList(transaction1);
        Mockito.when(reportsService.getTransactionsByDateAndAmount(userId, startDate, endDate, minAmount, maxAmount, null))
                .thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/filtro")
                        .param("id", userId)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("minAmount", minAmount.toString())
                        .param("maxAmount", maxAmount.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    @DisplayName("Export transactions to PDF - GET /export/pdf/{userId}")
    public void testExportToPdf() throws Exception {
        String userId = "206447261";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        BigInteger minAmount = new BigInteger("10");
        BigInteger maxAmount = new BigInteger("100");

        Transaction_report transaction1 = new Transaction_report();
        transaction1.setOriginRut(userId);
        transaction1.setDate(LocalDate.of(2023, 6, 15));
        transaction1.setAmount(new BigInteger("50"));

        List<Transaction_report> transactions = Arrays.asList(transaction1);
        Mockito.when(reportsService.getTransactionsByDateAndAmount(userId, startDate, endDate, minAmount, maxAmount, null))
                .thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/export/pdf/{userId}", userId)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("minAmount", minAmount.toString())
                        .param("maxAmount", maxAmount.toString())
                        .contentType(MediaType.APPLICATION_PDF_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.pdf"));
    }

    @Test
    @DisplayName("Find user transactions - GET /admin/findUser/{userId}")
    public void testFindUserTransactions() throws Exception {
        String userId = "206447261";
        Transaction_report transaction1 = new Transaction_report();
        transaction1.setOriginRut(userId);
        Transaction_report transaction2 = new Transaction_report();
        transaction2.setOriginRut(userId);

        List<Transaction_report> transactions = Arrays.asList(transaction1, transaction2);
        Mockito.when(reportsService.getAllTransactions(userId)).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/history/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    @DisplayName("Find user transactions for non-existent user - GET /admin/findUser/{userId}")
    public void testFindUserTransactionsNonExistentUser() throws Exception {
        String userId = "nonExistentUser";
        Mockito.when(reportsService.getAllTransactions(userId)).thenReturn(Arrays.asList());

        mockMvc.perform(MockMvcRequestBuilders.get("/report/history/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @DisplayName("Get transaction history - GET /report/history/{userId}")
    public void testGetHistory() throws Exception {
        Transaction_report transaction1 = new Transaction_report(1L,
                LocalDate.now(),
                LocalTime.now(),
                BigInteger.valueOf(100),
                "ServiceType1",
                "OriginRut1",
                "DestinationRut1",
                "Commerce1",
                "Completed",
                "Device1");

        Transaction_report transaction2 = new Transaction_report(2L,
                LocalDate.now(),
                LocalTime.now(),
                BigInteger.valueOf(200),
                "ServiceType2",
                "OriginRut2",
                "DestinationRut2",
                "Commerce2",
                "Completed",
                "Device2");

        List<Transaction_report> mockTransactions = Arrays.asList(transaction1, transaction2);

        doReturn(mockTransactions).when(reportsService).getAllTransactions(userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/history/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].serviceType", is("ServiceType1")))
                .andExpect(jsonPath("$[0].amount", is(100)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].serviceType", is("ServiceType2")))
                .andExpect(jsonPath("$[1].amount", is(200)));
    }

    @Test
    @DisplayName("Get report metrics - GET /report/metrics/{userId}")
    public void testGetMetrics() throws Exception {
        int expectedTransactionsCount = 5;
        int expectedCommercesCount = 3;
        BigInteger expectedTransactionsTotal = BigInteger.valueOf(100_000);
        int expectedTransactionsAverage = expectedTransactionsTotal.divide(BigInteger.valueOf(expectedTransactionsCount)).intValue();

        ReportMetricsDto mockMetrics = new ReportMetricsDto(
                expectedTransactionsCount,
                expectedTransactionsTotal,
                expectedTransactionsAverage,
                expectedCommercesCount
        );

        doReturn(mockMetrics).when(reportsService).calculateMetrics(userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/metrics/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.transactionsCount", is(mockMetrics.transactionsCount())))
                .andExpect(jsonPath("$.transactionsTotal", is(mockMetrics.transactionsTotal().intValue())))
                .andExpect(jsonPath("$.transactionsAverage", is(mockMetrics.transactionsAverage())))
                .andExpect(jsonPath("$.commercesCount", is(mockMetrics.commercesCount())));
    }

    @Test
    @DisplayName("Get charts data - GET /report/charts/{userId}")
    public void testGetCharts() throws Exception {
        List<LocalDate> dates = List.of(LocalDate.of(2024, 10,23), LocalDate.of(2024,10,24));
        List<Integer> transactionCounts = List.of(5, 3);
        var transactionsByDateChartDto = new TransactionsByDateChartDto(dates, transactionCounts);

        List<BigInteger> amounts = List.of(BigInteger.valueOf(100), BigInteger.valueOf(200));
        var transactionAmountsByDateChartDto = new TransactionAmountsByDateChartDto(dates, amounts);

        List<String> statuses = List.of("COMPLETED", "PENDING");
        List<Integer> statusCounts = List.of(8, 2);
        var transactionsByStatusChartDto = new TransactionsByStatusChartDto(statuses, statusCounts);

        List<Integer> ranges = List.of(0, 10000, 20000);
        List<Integer> frequencies = List.of(5, 3, 2);
        var transactionsByAmountRangeChartDto = new TransactionsByAmountRangeChartDto(ranges, frequencies);

        List<String> types = List.of("CREDIT", "DEBIT");
        List<Integer> typeCounts = List.of(4, 6);
        var transactionsByTypeChartDto = new TransactionsByTypeChartDto(types, typeCounts);

        List<String> commerces = List.of("Commerce A", "Commerce B");
        List<Integer> commerceAmounts = List.of(150, 350);
        var transactionsByCommerceChartDto = new TransactionsByCommerceChartDto(commerces, commerceAmounts);

        ChartsDataDto mockChartsData = new ChartsDataDto(
                transactionsByDateChartDto,
                transactionAmountsByDateChartDto,
                transactionsByStatusChartDto,
                transactionsByAmountRangeChartDto,
                transactionsByTypeChartDto,
                transactionsByCommerceChartDto
        );

        doReturn(mockChartsData).when(reportsService).getChartsData(userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/charts/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.transactionsByDateChartDto.dates", is(Arrays.asList("2024-10-23", "2024-10-24"))))
                .andExpect(jsonPath("$.transactionsByDateChartDto.counts", is(transactionCounts)))
                .andExpect(jsonPath("$.transactionAmountsByDateChartDto.dates", is(Arrays.asList("2024-10-23", "2024-10-24"))))
                .andExpect(jsonPath("$.transactionAmountsByDateChartDto.amounts", is(Arrays.asList(100, 200))))
                .andExpect(jsonPath("$.transactionsByStatusChartDto.statuses", is(statuses)))
                .andExpect(jsonPath("$.transactionsByStatusChartDto.counts", is(statusCounts)))
                .andExpect(jsonPath("$.transactionsByAmountRangeChartDto.ranges", is(ranges)))
                .andExpect(jsonPath("$.transactionsByAmountRangeChartDto.frequencies", is(frequencies)))
                .andExpect(jsonPath("$.transactionsByTypeChartDto.types", is(types)))
                .andExpect(jsonPath("$.transactionsByTypeChartDto.counts", is(typeCounts)))
                .andExpect(jsonPath("$.transactionsByCommerceChartDto.commerces", is(commerces)))
                .andExpect(jsonPath("$.transactionsByCommerceChartDto.amounts", is(commerceAmounts)));
    }

}
*/