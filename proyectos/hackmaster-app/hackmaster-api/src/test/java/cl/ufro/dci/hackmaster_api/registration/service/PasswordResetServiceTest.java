package cl.ufro.dci.hackmaster_api.registration.service;

import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.repository.OrganizationRepository;
import cl.ufro.dci.hackmaster_api.registration.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordResetServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PasswordResetService passwordResetService;

    private Participant participant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        participant = new Participant();
        participant.setEmail("j.alcalde01@ufromail.cl");
        participant.setPassword("123456789");
        participant.setUsername("miEmpresita123");
    }

    @Test
    void testSendPasswordResetTokenSuccess() {
        when(participantRepository.findByEmail("j.alcalde01@ufromail.cl")).thenReturn(Optional.of(participant));

        passwordResetService.sendPasswordResetToken("j.alcalde01@ufromail.cl");
        verify(participantRepository, times(1)).save(any(Participant.class));
        assertNotNull(participant.getResetPasswordToken());
        assertTrue(participant.getResetPasswordExpires().isAfter(LocalDateTime.now()));

        assertTrue(participant.getResetPasswordExpires().isAfter(LocalDateTime.now()));

        verify(emailService, times(1)).sendEmail(eq("j.alcalde01@ufromail.cl"), eq("Password reset"), anyString());
    }

    @Test
    void resetPassword() {
    }
}