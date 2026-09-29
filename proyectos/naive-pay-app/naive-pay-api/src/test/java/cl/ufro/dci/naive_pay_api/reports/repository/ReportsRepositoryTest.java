package cl.ufro.dci.naive_pay_api.reports.repository;
/*
import cl.ufro.dci.naive_pay_api.payments.domain.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ReportsRepositoryTest {
    @Autowired
    private ReportsRepository reportsRepository;

    private static final File DATA_JSON = Paths.get("src", "test", "resources", "transactions.json").toFile();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    public void setup() throws IOException {
        Transaction[] transactions = objectMapper.readValue(DATA_JSON, Transaction[].class);

        reportsRepository.saveAll(Arrays.asList(transactions));
    }

    @AfterEach
    public void cleanup() {
        reportsRepository.deleteAll();
    }

    @Test
    @DisplayName("Test findAllByOriginRut returns correct transactions")
    public void testFindAllByOriginRut() {
        String userRut = "123456789";

        List<Transaction> transactions = reportsRepository.findAllByOriginAccount_AccountNumber(userRut);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty(), "Transactions should not be empty for userRut: " + userRut);
    }

    @Test
    @DisplayName("Test findAllByOriginRut returns empty list for non-existing userRut")
    public void testFindAllByOriginRutReturnsEmptyForNonExistingRut() {
        String userRut = "non-existing-rut";

        List<Transaction> transactions = reportsRepository.findAllByOriginAccount_AccountNumber(userRut);

        assertNotNull(transactions);
        assertTrue(transactions.isEmpty(), "Transactions should be empty for non-existing userRut: " + userRut);
    }
}
*/