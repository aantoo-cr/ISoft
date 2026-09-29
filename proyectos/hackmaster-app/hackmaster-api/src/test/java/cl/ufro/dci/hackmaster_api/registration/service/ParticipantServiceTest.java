package cl.ufro.dci.hackmaster_api.registration.service;

import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.repository.ParticipantRepository;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ParticipantServiceTest {

    private Participant participantRegister = new Participant();
    private Participant participantExisting = new Participant();
    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        participantRegister.setId(1L);
        participantRegister.setEmail("j.alcalde01@ufromail.cl");
        participantRegister.setPassword("12345678");
        participantRegister.setUsername("bigalcah");
        participantRegister.setRut("27847694-4");
        participantRegister.setName("Javier");
        participantRegister.setLastname("Alcalde");
        participantRegister.setCity("Temuco");
        participantRegister.setCountry("Chile");
        participantRegister.setPhoneNumber("123456789");

        participantRegister.setId(2L);
        participantExisting.setEmail("j.tapia05@ufromail.cl");
        participantExisting.setPassword("12345678");
        participantExisting.setUsername("yisus");
        participantExisting.setRut("21476732-5");
        participantExisting.setName("Jesus");
        participantExisting.setLastname("Tapia");
        participantExisting.setCity("Temuco");
        participantExisting.setCountry("Chile");
        participantExisting.setPhoneNumber("123456789");

    }

    @Mock
    ParticipantRepository participantRepository;

    @InjectMocks
    ParticipantService participantService;

    /*
    @Test
    void testRegisterParticipantSuccess() {
        @SuppressWarnings("rawtypes")
        Set violations = validator.validate(participantRegister);

        assertTrue(violations.isEmpty());

        // simular que no existe participante con el mismo email
        when(participantRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(participantRepository.save(any(Participant.class))).thenReturn(participantRegister);

        Participant savedParticipant = participantService.register(participantRegister);

        assertNotNull(savedParticipant);
        assertEquals(participantRegister.getEmail(), savedParticipant.getEmail());

        verify(participantRepository, times(1)).save(any(Participant.class));

    }*/

    @Test
    void testUpdateParticipantSucess() {
        @SuppressWarnings("rawtypes")
        Set violations = validator.validate(participantRegister);

        assertTrue(violations.isEmpty());

        when(participantRepository.findById(2L)).thenReturn(Optional.of(participantExisting));
        when(participantRepository.save(any(Participant.class))).thenReturn(participantRegister);

        Optional<Participant> updatedParticipant = participantService.update(participantRegister);

        assertTrue(updatedParticipant.isPresent());
        assertEquals(participantRegister.getEmail(), updatedParticipant.get().getEmail());
        assertEquals(participantRegister.getUsername(), updatedParticipant.get().getUsername());
        assertEquals(participantRegister.getRut(), updatedParticipant.get().getRut());
        assertEquals(participantRegister.getName(), updatedParticipant.get().getName());
        assertEquals(participantRegister.getLastname(), updatedParticipant.get().getLastname());
        assertEquals(participantRegister.getCity(), updatedParticipant.get().getCity());
        assertEquals(participantRegister.getCountry(), updatedParticipant.get().getCountry());
        assertEquals(participantRegister.getPhoneNumber(), updatedParticipant.get().getPhoneNumber());

        verify(participantRepository, times(1)).findById(anyLong());
    }
}