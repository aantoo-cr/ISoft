package cl.ufro.dci.naive_pay_api.authentication.service;

import cl.ufro.dci.naive_pay_api.authentication.utils.UserAuthProviderUtils;
import cl.ufro.dci.naive_pay_api.authentication.dto.ApiResponse;
import cl.ufro.dci.naive_pay_api.authentication.dto.UserDto;
import cl.ufro.dci.naive_pay_api.authentication.mapper.UserMapper;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio encargado de la recuperación de contraseñas.
 *
 * Este servicio gestiona las solicitudes de recuperación de contraseñas de los usuarios
 * mediante el envío de correos electrónicos con un enlace único para restablecer la contraseña.
 *
 * @version 1.1
 * @author Jesus y Marcelo
 */
@Service
public class RecoverPasswordService {

    private final AccountHolderRepository accountHolderRepository;
    private final JavaMailSender emailSender;
    private final UserAuthProviderUtils userAuthProviderUtils;
    @Autowired
    private UserMapper userMapper;

    @Value("${app.reset_password_url:http://localhost:4200/reset-password}")
    private String resetPasswordUrl;

    @Autowired
    public RecoverPasswordService(AccountHolderRepository accountHolderRepository,
                                  JavaMailSender emailSender,
                                  UserAuthProviderUtils userAuthProviderUtils) {
        this.accountHolderRepository = accountHolderRepository;
        this.emailSender = emailSender;
        this.userAuthProviderUtils = userAuthProviderUtils;
    }

    /**
     * Maneja la solicitud de recuperación de contraseña.
     *
     * @param email Dirección de correo electrónico proporcionada por el usuario.
     * @return ResponseEntity con un ApiResponse indicando el resultado.
     */
    public ResponseEntity<ApiResponse> handlePasswordResetRequest(String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "El correo electrónico es obligatorio."));
        }

        Optional<?> user = accountHolderRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "El correo electrónico no está registrado."));
        }

        // Generar un token usando UserAuthProvider
        UserDto userDto = userMapper.toUserDto((AccountHolder) user.get());
        String token = userAuthProviderUtils.createToken(userDto, 5);
        String resetLink = resetPasswordUrl + "?token=" + token;

        try {
            sendPasswordResetEmail(email, "Enlace de Recuperación de Cuenta",
                    "Haga clic en el siguiente enlace para restablecer su contraseña: " + resetLink);
            return ResponseEntity.ok(new ApiResponse(true, "Correo de restablecimiento enviado exitosamente."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error al enviar el correo. Intente nuevamente."));
        }
    }

    /**
     * Envía un correo electrónico con un enlace de restablecimiento de contraseña.
     *
     * @param email   Dirección de correo electrónico del destinatario.
     * @param subject Asunto del correo.
     * @param message Mensaje del correo.
     */
    private void sendPasswordResetEmail(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("ejemplo.naive@gmail.com");
        emailSender.send(mailMessage);
    }
}