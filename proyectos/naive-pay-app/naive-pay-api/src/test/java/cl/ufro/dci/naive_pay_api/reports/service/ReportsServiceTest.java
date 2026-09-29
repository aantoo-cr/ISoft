package cl.ufro.dci.naive_pay_api.reports.service;

import cl.ufro.dci.naive_pay_api.payments.domain.State;
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import cl.ufro.dci.naive_pay_api.reports.dto.ChartsDataDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByAmountRangeChartDto;
import cl.ufro.dci.naive_pay_api.reports.dto.TransactionsByDateChartDto;
import cl.ufro.dci.naive_pay_api.reports.repository.ReportsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReportsServiceTest {
    @Autowired
    private ReportsService reportsService;

    @MockBean
    private ReportsRepository reportsRepository;

    private String userId;

    @BeforeEach
    public void setUp() {
        userId = "123456789";
    }

    @Test
    @DisplayName("Get all transactions for a user")
    public void testGetAllTransactions() {
        Transaction transaction = new Transaction();
        transaction.setToken("1L");
        transaction.setDate(LocalDate.now());
        transaction.setTime(LocalTime.now());
        transaction.setAmount(BigInteger.valueOf(100));
        transaction.setDescription("Servicio");
        transaction.setState(State.APPROVED);

        List<Transaction> mockTransactions = Collections.singletonList(transaction);

        doReturn(mockTransactions).when(reportsRepository).findAllByOriginAccount_AccountNumber(userId);

        List<Transaction> transactions = reportsService.getAllTransactions(userId);

        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals("Servicio", transactions.get(0).getDescription());
    }

    @Test
    @DisplayName("Get charts data")
    public void testGetChartsData() {
        Transaction transaction = new Transaction();
        transaction.setToken("1L");
        transaction.setDate(LocalDate.now());
        transaction.setTime(LocalTime.now());
        transaction.setAmount(BigInteger.valueOf(100));
        transaction.setDescription("Servicio");
        transaction.setState(State.APPROVED);

        List<Transaction> mockTransactions = Collections.singletonList(transaction);

        doReturn(mockTransactions).when(reportsRepository).findAllByOriginAccount_AccountNumber(userId);

        ChartsDataDto chartsData = reportsService.getChartsData(userId);

        assertNotNull(chartsData);
        assertNotNull(chartsData.transactionsByDateChartDto());
        assertNotNull(chartsData.transactionAmountsByDateChartDto());
    }

    @Test
    @DisplayName("Build transactions by date chart")
    public void testBuildTransactionsByDateChart() {
        var transaction1 = new Transaction();
        transaction1.setToken("1L");
        transaction1.setDate(LocalDate.of(2023, 10, 1));
        transaction1.setTime(LocalTime.now());
        transaction1.setAmount(BigInteger.valueOf(100));
        transaction1.setDescription("Servicio");
        transaction1.setState(State.APPROVED);

        var transaction2 = new Transaction();
        transaction2.setToken("2L");
        transaction2.setDate(LocalDate.of(2023, 10, 1));
        transaction2.setTime(LocalTime.now());
        transaction2.setAmount(BigInteger.valueOf(200));
        transaction2.setDescription("Servicio");
        transaction2.setState(State.APPROVED);

        List<Transaction> mockTransactions = Arrays.asList(transaction1, transaction2);

        TransactionsByDateChartDto chartDto = reportsService.buildTransactionsByDateChart(mockTransactions);

        assertEquals(1, chartDto.dates().size());
        assertEquals(2, chartDto.counts().get(0).intValue());
    }

    @Test
    @DisplayName("Build transactions by amount range chart")
    public void testBuildTransactionsByAmountRangeChart() {
        var transaction1 = new Transaction();
        transaction1.setToken("1L");
        transaction1.setDate(LocalDate.now());
        transaction1.setTime(LocalTime.now());
        transaction1.setAmount(BigInteger.valueOf(5000));
        transaction1.setDescription("Servicio");
        transaction1.setState(State.APPROVED);

        var transaction2 = new Transaction();
        transaction2.setToken("2L");
        transaction2.setDate(LocalDate.now());
        transaction2.setTime(LocalTime.now());
        transaction2.setAmount(BigInteger.valueOf(15000));
        transaction2.setDescription("Servicio");
        transaction2.setState(State.APPROVED);

        List<Transaction> mockTransactions = Arrays.asList(transaction1, transaction2);

        TransactionsByAmountRangeChartDto chartDto = reportsService.buildTransactionsByAmountRangeChart(mockTransactions);

        assertNotNull(chartDto);
        assertEquals(2, chartDto.frequencies().size());
    }
}
