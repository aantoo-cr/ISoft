package cl.ufro.dci.hackmaster_api.communication.service;

import cl.ufro.dci.hackmaster_api.communication.domain.Chat;
import cl.ufro.dci.hackmaster_api.communication.domain.Message;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
//import cl.ufro.dci.hackmaster_api.registration.service.OrganizationService;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import cl.ufro.dci.hackmaster_api.communication.repository.ChatRepository;
import cl.ufro.dci.hackmaster_api.communication.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar los chats y mensajes.
 */
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ParticipantService participantService;

/*     @Autowired
    private OrganizationService organizationService; */

    /**
     * Obtiene los chats de un usuario.
     *
     * @param user1 El usuario del que se obtendrán los chats.
     * @return Lista de chats del usuario.
     */
    public List<Chat> getChatsByUser(Optional<Participant> user1) {
                return chatRepository.findByUsers(user1);
    }

    /**
     * Obtiene todos los chats.
     *
     * @return Lista de todos los chats.
     */
    public List<Chat> getAllChats(){
        Iterable<Chat> chats = chatRepository.findAll();
        return (List<Chat>) chats;
    }

    /**
     * Envía un mensaje en un chat.
     *
     * @param chat El chat en el que se enviará el mensaje.
     * @param sender El usuario que envía el mensaje.
     * @param content El contenido del mensaje.
     * @return El mensaje enviado.
     * @throws IllegalArgumentException Si el contenido del mensaje está vacío.
     */
    public Message sendMessage(Chat chat, UserReg sender, String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }

        Message message = new Message(content, sender, chat);
        chat.getMessages().add(message); // Agregar el mensaje al chat
        messageRepository.save(message);  // Guardar el mensaje por separado
        return message;
    }

    /**
     * Une a dos usuarios en un chat.
     *
     * @param user1Id ID del primer usuario.
     * @param user2Id ID del segundo usuario.
     * @return El chat creado o existente entre los dos usuarios.
     */
    public Chat joinChat(Long user1Id, Long user2Id) {
        // Obtener los usuarios desde el repositorio para que sean entidades gestionadas
        Optional<Participant> user1 = participantService.findById(user1Id);
        Optional<Participant> user2 = participantService.findById(user2Id);
        List<Chat> chatsUser1 = this.getChatsByUser(user1);
        List<Chat> chatsUser2 = this.getChatsByUser(user2);

        // Buscar si ya existe un chat entre los dos usuarios
        Chat chat = chatsUser1.stream()
                .filter(chatsUser2::contains)
                .findFirst()
                .orElse(null);

        if (chat == null && !user1.equals(user2)) {
            Participant u1 = user1.get();
            Participant u2 = user2.get();
            chat = new Chat();
            chat.setName(u1.getName() + " & " + u2.getName());
            chat.getUsers().add(u1);
            chat.getUsers().add(u2);
            chat = chatRepository.save(chat);
        }
        return chat;
    }

    /**
     * Obtiene un chat por su ID.
     *
     * @param chatId ID del chat.
     * @return El chat correspondiente al ID.
     * @throws IllegalArgumentException Si el chat no se encuentra.
     */
    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(() -> new IllegalArgumentException("Chat not found"));
    }
}

