package cl.ufro.dci.hackmaster_api.communication.controller;

//import cl.ufro.dci.hackmaster_api.registration.service.OrganizationService;
//import cl.ufro.dci.hackmaster_api.registration.domain.Organization;
//import cl.ufro.dci.hackmaster_api.registration.domain.User;
import cl.ufro.dci.hackmaster_api.communication.service.ChatService;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.communication.domain.Chat;
import cl.ufro.dci.hackmaster_api.communication.domain.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar los chats.
 */
@RestController
@RequestMapping("api/chats")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ParticipantService participantService;
    
    /**
     * Obtiene la lista de chats de un usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de chats del usuario.
     */
    @GetMapping("/{userId}/list")
    public ResponseEntity<?> getUserChats(@PathVariable Long userId) {
        Optional<Participant> participant = participantService.findById(userId);
        Iterable<Participant> participants = participantService.findAll();

        if (participant.isEmpty()) {
            return ResponseEntity.ok().body("{}");
        }

        List<Object> users = new ArrayList<>();

        for (Participant p : participants) {
            if (!p.getId().equals(userId)) {
                users.add(p);
            }
        }

        return ResponseEntity.ok().body(users);
    }

    /**
     * Une a dos usuarios en un chat.
     *
     * @param userId ID del primer usuario.
     * @param user2Id ID del segundo usuario.
     * @return El chat creado o existente entre los dos usuarios.
     */
    @PostMapping("/{userId}/join/{user2Id}")
    public ResponseEntity<?> joinChat(@PathVariable Long userId, @PathVariable Long user2Id) {
        try {
            Chat chat = chatService.joinChat(userId, user2Id);
            System.out.println("Chat: " + chat.getId()+ ", "+ chat.getName());
            return ResponseEntity.ok().body(chat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos de usuario o chat");
        }
    }

    /**
     * Envía un mensaje en un chat.
     *
     * @param chatId ID del chat.
     * @param userIdSender ID del usuario que envía el mensaje.
     * @param content El contenido del mensaje.
     * @return El mensaje enviado.
     */
    @PostMapping("/{chatId}/messages/{userIdSender}")
    public ResponseEntity<?> sendMessage(@PathVariable Long chatId, 
                                        @PathVariable Long userIdSender,
                                        @RequestBody String content) {
        if (content == null || content.trim().isEmpty()) {
            System.out.println("El mensaje no puede estar vacío");
            return ResponseEntity.badRequest().body("El mensaje no puede estar vacío");
        }
        try {
            Optional<Participant> sender = participantService.findById(userIdSender);
            Participant userSender = sender.get();
            if (sender.isEmpty() && userIdSender != 0) {
                return ResponseEntity.badRequest().body("Usuario no encontrado");
            }
            Chat chat = chatService.getChatById(chatId);

            System.out.println("Chat: " + chat.getId()+ ", "+ chat.getName()+ ", "+ chat.getUsers());
            Message message = chatService.sendMessage(chat, userSender, content);
            System.out.println("Mensaje enviado y guardado: " + message.getContent());
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error al enviar el mensaje: " + e.getMessage());
        }
    }
}

