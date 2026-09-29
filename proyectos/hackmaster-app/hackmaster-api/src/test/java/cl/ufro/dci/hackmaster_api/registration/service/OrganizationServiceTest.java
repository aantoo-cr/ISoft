package cl.ufro.dci.hackmaster_api.registration.service;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
import cl.ufro.dci.hackmaster_api.registration.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrganizationServiceTest {

  private Organization organizationRegister = new Organization();
  private Organization organizationExisting = new Organization();
  private Validator validator;

  @Mock
  OrganizationRepository organizationRepository;

  @InjectMocks
  OrganizationService organizationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    organizationRegister.setId(1L);
    organizationRegister.setEmail("j.alcalde01@ufromail.cl");
    organizationRegister.setPassword("123456789");
    organizationRegister.setUsername("miEmpresita123");
    organizationRegister.setSocialNameOrganization("Comercializadora Alcalde");
    organizationRegister.setRutOrganization("27847694-4");
    organizationRegister.setRutRepresentative("27847694-4");
    organizationRegister.setRepresentativeName("Javier Alcalde");

    organizationExisting.setId(2L);
    organizationExisting.setEmail("j.tapia05@ufromail.cl");
    organizationExisting.setPassword("12345678");
    organizationExisting.setUsername("otraEmpresita");
    organizationExisting.setSocialNameOrganization("Comercializadora Tapia");
    organizationExisting.setRutOrganization("21476732-5");
    organizationExisting.setRutRepresentative("21476732-5");
    organizationExisting.setRepresentativeName("Jesus Tapia");

  }

  /*@Test
  void testRegisterOrganizationSuccess() {
    @SuppressWarnings("rawtypes")
    Set violations = validator.validate(organizationRegister);

    assertTrue(violations.isEmpty());

    when(organizationRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    when(organizationRepository.save(any())).thenReturn(organizationRegister);

    Organization organizationSaved = organizationService.register(organizationRegister);

    assertNotNull(organizationSaved);
    assertEquals(organizationRegister, organizationSaved);

    verify(organizationRepository, times(1)).save(any(Organization.class));
  }*/

  @Test
  void testUpdateOrganizationSucess() {
    @SuppressWarnings("rawtypes")
    Set violations = validator.validate(organizationExisting);

    assertTrue(violations.isEmpty());

    when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(organizationExisting));
    when(organizationRepository.save(any())).thenReturn(organizationExisting);

    Optional<Organization> organizationOptional = organizationService.update(organizationExisting);

    assertTrue(organizationOptional.isPresent());
    assertEquals(organizationExisting, organizationOptional.get());
    assertEquals(organizationExisting.getId(), organizationOptional.get().getId());
    assertEquals(organizationExisting.getEmail(), organizationOptional.get().getEmail());
    assertEquals(organizationExisting.getRutOrganization(), organizationOptional.get().getRutOrganization());
    assertEquals(organizationExisting.getRutRepresentative(), organizationOptional.get().getRutRepresentative());
    assertEquals(organizationExisting.getRepresentativeName(), organizationOptional.get().getRepresentativeName());
    assertEquals(organizationExisting.getSocialNameOrganization(),
        organizationOptional.get().getSocialNameOrganization());
    assertEquals(organizationExisting.getUsername(), organizationOptional.get().getUsername());

    verify(organizationRepository, times(1)).save(any(Organization.class));
  }
}
