package cl.ufro.dci.hackmaster_api.communication.domain;

import java.time.LocalDateTime;

import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un mensaje en un chat.
 */
@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    private UserReg sender;

    @ManyToOne
    @JsonBackReference
    private Chat chat;

    public Message() {
        // Constructor vacío necesario para JPA
    }

    /**
     * Constructor para crear un mensaje.
     *
     * @param content El contenido del mensaje.
     * @param sender El usuario que envía el mensaje.
     * @param chat El chat al que pertenece el mensaje.
     */
    public Message(String content, UserReg sender, Chat chat) {
        this.content = content;
        this.sender = sender;
        this.chat = chat;
        this.timestamp = LocalDateTime.now();
    }

}
