package cl.ufro.dci.naive_pay_api.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Servicio que se encarga del envío de correos electrónicos, especialmente para enviar códigos de verificación.
 * Utiliza el servicio de correo proporcionado por Spring a través de {@link JavaMailSender}.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Constructor que inicializa el servicio de correo con el {@link JavaMailSender} proporcionado por Spring.
     *
     * @param mailSender El {@link JavaMailSender} utilizado para enviar correos electrónicos.
     */
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envia un correo electrónico con un código de verificación a la dirección de correo proporcionada.
     *
     * <p>Este método decodifica la dirección de correo electrónico (en caso de que esté codificada en formato URL) y luego
     * envía un correo con el código de verificación.</p>
     *
     * @param email La dirección de correo electrónico a la que se enviará el código de verificación.
     * @param verificationCode El código de verificación que se incluirá en el correo electrónico.
     * @throws RuntimeException Si ocurre un error al decodificar el correo electrónico o al enviar el correo.
     */
    public void sendEmail(String email, String verificationCode) {
        // Decodificar el correo electrónico si está codificado
        try {
            email = URLDecoder.decode(email, "UTF-8"); // Decodificar URL
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al decodificar el correo electrónico", e);
        }

        // Crear un mensaje de correo simple
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Verificación");
        message.setText("Tu código de verificación es: " + verificationCode);

        // Intentar enviar el mensaje
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo electrónico", e);
        }
    }

    /**
     * Envía un correo electrónico de restablecimiento de contraseña al usuario.
     * <p>
     * Este metodo genera un enlace único de restablecimiento de contraseña que incluye un token y la dirección
     * de correo electrónico del usuario, ambos codificados. El enlace se envía al correo proporcionado.
     * Si el usuario no solicitó el restablecimiento, se recomienda ignorar el mensaje.
     * </p>
     *
     * @param email la dirección de correo electrónico del usuario a la cual se enviará el enlace de restablecimiento
     */
    public void sendPasswordResetEmail(String email) {
        // Decodificar el correo electrónico si está codificado
        email = URLDecoder.decode(email, StandardCharsets.UTF_8);

        // Generamos un token único de restablecimiento de contraseña
        String token = UUID.randomUUID().toString();

        // Codificamos el email y el token para añadirlos al enlace
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);

        // Enlace de restablecimiento de contraseña con los parámetros
        String resetLink = "https://localhost:4200/reset-password?email=" + encodedEmail + "&token=" + encodedToken;

        // Creamos el mensaje del correo
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Restablecimiento de contraseña");
        message.setText("Para restablecer tu contraseña, haz clic en el siguiente enlace:\n" + resetLink +
                "\n\nSi usted no ha solicitado restablecer tu contraseña, ignora este correo.");

        // Enviamos el correo
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo electrónico", e);
        }
    }

}

