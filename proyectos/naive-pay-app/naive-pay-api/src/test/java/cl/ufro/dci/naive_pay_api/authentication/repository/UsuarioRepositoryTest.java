package cl.ufro.dci.naive_pay_api.authentication.repository;

import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Pruebas específicas para JPA
public class UsuarioRepositoryTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @BeforeEach
    public void setup() {
        // Inserta un usuario en la base de datos antes de cada prueba
       // AccountHolder usuario = new AccountHolder("12345678-9", "9", "John", "Doe", "jason.m@gmail.com", "123456789", "D1234567", "123 Main St", "Engineer", "some_salt", "jason123", "1990-01-01");
        //accountHolderRepository.save(usuario);
    }

    @AfterEach
    public void tearDown() {
        // Limpia los datos después de cada prueba
        accountHolderRepository.deleteAll();
    }

    @Test
    @DisplayName("Buscar un usuario registrado, por Id (email)")
    public void testFindById() {
        Optional<AccountHolder> usuario = accountHolderRepository.findByEmail("jason.m@gmail.com");
        assertTrue(usuario.isPresent(), "El usuario existe");

        String esperado = "jason.m@gmail.com";
        String respuesta = usuario.get().getEmail();

        assertEquals(esperado, respuesta);
    }

    @Test
    @DisplayName("Buscar un usuario que no existe")
    public void testFindNonRegistredId() {
        Optional<AccountHolder> usuario = accountHolderRepository.findById("no.registrado@gmail.com");

        assertFalse(usuario.isPresent(), "El usuario no existe");
    }
}
