package cl.ufro.dci.naive_pay_api.authentication.service;

import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import cl.ufro.dci.naive_pay_api.registration.service.EmailService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginServiceTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

//    @Autowired
//    private LoginService loginService;

    @Autowired
    private EmailService recoverService;

    @BeforeEach
    public void setup() {
        // Insert a user into the in-memory database
        //AccountHolder usuario = new AccountHolder("jason.m@gmail.com", "jason123");
        //accountHolderRepository.save(usuario);
    }

    @AfterEach
    public void tearDown() {
        // Clear existing users in the repository
        accountHolderRepository.deleteAll();
    }

    @DisplayName("CP-06: Login con credenciales correctas")
    @Test
    public void testLoginUser_CorrectCredentials() throws Exception {
       // AccountHolder usuarioLogin = new AccountHolder("jason.m@gmail.com", "jason123");

        // Respuesta
        //String esperado = "Login exitoso";
        //String respuesta = loginService.loginUser(usuarioLogin);

        //Assertions.assertEquals(esperado, respuesta);
    }

    @DisplayName("CP-07: Restablecer contraseña con correo registrado")
    @Test
    public void testRecoverPassword_CorrectEmail() throws Exception {
        //AccountHolderr usuarioEmail = new AccountHolderr("jason.m@gmail.com");

        // Respuesta
        String esperada = "Email encontrado, restableciendo contraseña";
        //String respuesta = recoverService.recoverPassword(usuarioEmail);

        //Assertions.assertEquals(esperada, respuesta);

    }

    @DisplayName("CP-08: Usuario no registrado")
    @Test
    public void testLoginUser_WrongCredentials() throws Exception {
    //    AccountHolder usuarioEmail = new AccountHolder("jasasdasdon.m@gmail.com", "jason123");

        // Respuesta
        //String esperada = "Correo no existe";
        //String respuesta = loginService.loginUser(usuarioEmail);

        //Assertions.assertEquals(esperada, respuesta);
    }
}
