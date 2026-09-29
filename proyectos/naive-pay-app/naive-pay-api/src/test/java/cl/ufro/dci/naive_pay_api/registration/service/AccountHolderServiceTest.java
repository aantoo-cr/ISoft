/*package cl.ufro.dci.naive_pay_api.registration.service;

import cl.ufro.dci.naive_pay_api.funds.domain.BankAccount;
import cl.ufro.dci.naive_pay_api.funds.repository.AccountHolderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountHolderServiceTest {

    @Mock
    private AccountHolderRepository accountHolderRepository;

    private AccountHolderService accountHolderService;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountHolderService = new AccountHolderService(accountHolderRepository);

        bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.valueOf(1000000));
    }

    @Test
    @DisplayName("CP-01: Validar registrar usuario a partir del ingreso de datos")
    public void testValidateAccountHolder() {
        boolean result = accountHolderService.validateAccountHolder("21470145-6", "Javier", "Rodriguez", "javier.sandoval@gmail.com",
                "56928382382", "234123234", "Chile", "Temuco", bankAccount.getBalance());
        Assertions.assertTrue(result, "Los datos deben ser válidos y retornar un Optional no vacío");
        result = accountHolderService.validateAccountHolder("21470145-6", "Javier", "Rodrigu3z", "javier.sandoval@gmail.com",
                "56928382382", "234123234", "Chile", "Temuco", bankAccount.getBalance());
        Assertions.assertFalse(result, "Los datos debería ser inválidos ya que el apellido no puede tener valores numéricos");
        result = accountHolderService.validateAccountHolder("21470145-6", "Javier", "Rodrigu3z", "javier.sandoval@gmail.com",
                "56928382382", null, "Chile", "Temuco", bankAccount.getBalance());
        Assertions.assertFalse(result, "Los datos no deben tener valores null");
    }

    @Test
    @DisplayName("CP-02: Validar nombre cuando no es nulo, ni7 vacío ni contiene números")
    public void testValidateName() {
        Assertions.assertTrue(accountHolderService.validateString("Renato"), "El nombre debe ser válido");
        Assertions.assertFalse(accountHolderService.validateString(""), "El nombre no puede estar vacío");
        Assertions.assertFalse(accountHolderService.validateString(null), "El nombre no puede ser nulo");
        Assertions.assertFalse(accountHolderService.validateString("R3nato"), "El nombre no puede contener números");
    }

    @Test
    @DisplayName("CP-03: Validar correo electrónico cuando no tiene más de una arroba o tiene menos de un punto")
    public void testValidateEmail() {
        Assertions.assertFalse(accountHolderService.validateEmail(null), "El correo no debe ser un null");
        Assertions.assertFalse(accountHolderService.validateEmail(""), "El correo no debe estar vacío");
        Assertions.assertFalse(accountHolderService.validateEmail("fabricio67@gmail@.com"), "El correo debe contener más de una arroba");
        Assertions.assertFalse(accountHolderService.validateEmail("fabricio67@gmailcom"), "El correo debe contener un punto después de la arroba");
        Assertions.assertTrue(accountHolderService.validateEmail("fabricio67@gmail.com"), "El correo debe ser válido");
    }

    @Test
    @DisplayName("CP-04: Validar RUT cuando el rut contiene 1 y 8 dígitos más el dígito verificador")
    public void testValidateRut(){
        Assertions.assertFalse(accountHolderService.validateRUT(null), "El rut no debe ser un null");
        Assertions.assertFalse(accountHolderService.validateRUT(""), "El rut no debe estar vacío");
        Assertions.assertFalse(accountHolderService.validateRUT("231458890-9"), "El rut no debe ser tener más de 8 dígitos");
        Assertions.assertTrue(accountHolderService.validateRUT("23140588-7"), "El rut debe ser válido");
    }

    @Test
    @DisplayName("CP-05: Validar número de teléfono cuando contiene entre 7 y 15 dígitos sin letras")
    public void testValidatePhoneNumber(){
        Assertions.assertFalse(accountHolderService.validatePhoneNumber(null), "El teléfono no debe ser un null");
        Assertions.assertFalse(accountHolderService.validatePhoneNumber(""), "El teléfono no debe estar vacío");
        Assertions.assertFalse(accountHolderService.validatePhoneNumber("5693412343234323"), "El número de teléfono no debe tener más de 15 dígitos");
        Assertions.assertFalse(accountHolderService.validatePhoneNumber("457es872e72e"), "El número de teléfono no puede contener letras");
        Assertions.assertTrue(accountHolderService.validatePhoneNumber("56874372337"), "El número de teléfono debe ser válido");
    }

    @Test
    @DisplayName("CP-06: Validar fecha de nacimiento donde la persona debe tener más de 18 años")
    public void testIsAdult(){
        Assertions.assertFalse(accountHolderService.isAdult(LocalDate.of(2010, 1, 1)), "La fecha no debe ser válida. No se permiten menores de edad");
        Assertions.assertTrue(accountHolderService.isAdult(LocalDate.of(2000, 1, 1)), "La fecha debe ser válida");
    }

    @Test
    @DisplayName("CP-07: Validar el país y la ciudad perteneciente a la dirección del cliente")
    public void testValidateCountryAndCity(){
        Assertions.assertFalse(accountHolderService.validateString(""), "El país no debe estar vacío");
        Assertions.assertFalse(accountHolderService.validateString("Chil3"), "El país no debe contener números");
        Assertions.assertTrue(accountHolderService.validateString("Chile"), "El país debe ser válido");
        Assertions.assertFalse(accountHolderService.validateString("23534"), "La ciudad no debe contener números");
        Assertions.assertFalse(accountHolderService.validateString(null),"La ciudad no debe ser un null");
        Assertions.assertTrue(accountHolderService.validateString("Temuco"), "La ciudad debe ser válida");
    }

}
*/