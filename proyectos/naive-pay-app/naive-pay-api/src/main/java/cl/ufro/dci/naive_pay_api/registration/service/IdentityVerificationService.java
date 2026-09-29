package cl.ufro.dci.naive_pay_api.registration.service;

import jakarta.mail.MessagingException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Servicio encargado de la generación y validación de códigos de verificación (PIN) para la identidad del usuario.
 * Este servicio genera un código PIN de 6 dígitos y lo envía al correo electrónico proporcionado. También maneja
 * los intentos fallidos de verificación y bloquea el servicio temporalmente después de un número máximo de intentos fallidos.
 */
@Service
public class IdentityVerificationService {

    @Setter
    private String generatedPin;

    private int failedAttempts;
    private LocalDateTime lockTime;

    private final int MAX_ATTEMPTS = 3; // Número máximo de intentos fallidos
    private final long LOCK_DURATION = 30; // Duración del bloqueo en minutos

    @Autowired
    private EmailService emailService;

    /**
     * Genera un código de verificación (PIN) de 6 dígitos y lo envía al correo electrónico proporcionado.
     * Resetea los intentos fallidos y el tiempo de bloqueo si el código es generado correctamente.
     *
     * @param email La dirección de correo electrónico a la que se enviará el código de verificación.
     * @throws MessagingException Si ocurre un error al enviar el correo electrónico.
     */
    public void generateVerificationCode(String email) {
        Random random = new Random();
        StringBuilder pin = new StringBuilder();

        // Generar un código de 6 dígitos
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Genera un número entre 0 y 9
            pin.append(digit);
        }

        this.generatedPin = pin.toString();
        this.failedAttempts = 0; // Reiniciar los intentos fallidos
        this.lockTime = null; // Reiniciar el tiempo de bloqueo

        // Enviar el código de verificación por correo electrónico
        this.emailService.sendEmail(email, this.generatedPin);
    }

    /**
     * Valida el código PIN ingresado por el usuario.
     * Si el servicio está bloqueado debido a intentos fallidos, no se puede realizar la validación.
     *
     * @param inputPin El PIN ingresado por el usuario para validar.
     * @return true si el PIN ingresado es correcto, false en caso contrario.
     */
    public boolean validatePin(String inputPin) {
        // Comprobar si el servicio está bloqueado
        if (isLocked()) {
            return false; // El servicio está bloqueado, no se puede validar
        }

        // Comparar el PIN ingresado con el generado
        if (generatedPin.equals(inputPin)) {
            failedAttempts = 0; // Reiniciar los intentos si el PIN es correcto
            return true; // PIN válido
        } else {
            failedAttempts++; // Incrementar los intentos fallidos
            if (failedAttempts >= MAX_ATTEMPTS) {
                lockService(); // Bloquear el servicio si se alcanzan los intentos máximos
            }
            return false; // PIN incorrecto
        }
    }

    /**
     * Verifica si el servicio está bloqueado debido a intentos fallidos excesivos.
     * El servicio se bloquea si el número de intentos fallidos alcanza el máximo permitido
     * y se mantiene bloqueado durante una duración específica.
     *
     * @return true si el servicio está bloqueado, false en caso contrario.
     */
    private boolean isLocked() {
        // Verificar si el tiempo de bloqueo está establecido y si el tiempo de bloqueo aún no ha pasado
        if (lockTime != null) {
            return LocalDateTime.now().isBefore(lockTime.plusMinutes(LOCK_DURATION));
        }
        return false; // No está bloqueado
    }

    /**
     * Bloquea el servicio configurando un tiempo de bloqueo, para que no se puedan realizar más intentos
     * hasta que pase la duración del bloqueo.
     */
    private void lockService() {
        lockTime = LocalDateTime.now(); // Establecer el tiempo de bloqueo
    }
}
