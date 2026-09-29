package cl.ufro.dci.naive_pay_api.devices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio para enviar correos electrónicos relacionados con la vinculación y desvinculación de dispositivos.
 * Este servicio maneja el envío de correos electrónicos con códigos de verificación y confirmaciones.
 */
@Service
public class DeviceEmailService {

    /**
     * Servicio para enviar correos electrónicos.
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo electrónico con un código de verificación.
     *
     * @param destinatario El correo electrónico del destinatario.
     * @param codigo El código de verificación.
     * @throws MessagingException Si ocurre un error al enviar el correo electrónico.
     */
    public void enviarCorreo(String destinatario, Integer codigo) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");

        helper.setTo(destinatario);
        helper.setSubject("Código de verificación");
        helper.setText("Tu código de verificación es: " + codigo, true);

        mailSender.send(mensaje);
    }

    /**
     * Envía un correo electrónico con un código de desvinculación.
     *
     * @param destinatario El correo electrónico del destinatario.
     * @param codigo El código de desvinculación.
     * @throws MessagingException Si ocurre un error al enviar el correo electrónico.
     */
    public void enviarCorreoDesvinculacion(String destinatario, Integer codigo) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");

        helper.setTo(destinatario);
        helper.setSubject("Código de desvinculación");
        helper.setText("Tu código de desvinculación es: " + codigo, true);

        mailSender.send(mensaje);
    }

    /**
     * Envía un correo electrónico de confirmación de vinculación de dispositivo.
     *
     * @param destinatario El correo electrónico del destinatario.
     * @throws MessagingException Si ocurre un error al enviar el correo electrónico.
     */
    public void enviarMensajeConfirmacion(String destinatario) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");

        helper.setTo(destinatario);
        helper.setSubject("Dispositivo vinculado exitosamente");
        helper.setText("Tu dispositivo ha sido vinculado exitosamente.", true);

        mailSender.send(mensaje);
    }

    /**
     * Envía un correo electrónico de confirmación de desvinculación de dispositivo.
     *
     * @param destinatario El correo electrónico del destinatario.
     * @throws MessagingException Si ocurre un error al enviar el correo electrónico.
     */
    public void enviarMensajeDesvinculacion(String destinatario) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");

        helper.setTo(destinatario);
        helper.setSubject("Dispositivo desvinculado exitosamente");
        helper.setText("Tu dispositivo ha sido desvinculado exitosamente.", true);

        mailSender.send(mensaje);
    }
}
