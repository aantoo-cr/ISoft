package cl.ufro.dci.hackmaster_api.registration.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * Servicio encargado de enviar correos electrónicos para
 * la recuperacion de contrseña utilizando JavaMailSender.
 */
@Service
public class EmailService {

  private JavaMailSender javaMailSender;

  public EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  /**
   * Envía un correo electrónico con el asunto y cuerpo especificados a la dirección de correo indicada.
   *
   * @param to La dirección de correo electrónico del destinatario.
   * @param subject El asunto del correo electrónico.
   * @param body El contenido del cuerpo del correo electrónico.
   */
  public void sendEmail(String to, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);

    javaMailSender.send(message);
  }
}
