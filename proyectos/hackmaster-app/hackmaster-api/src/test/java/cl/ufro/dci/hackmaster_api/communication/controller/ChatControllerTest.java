package cl.ufro.dci.hackmaster_api.communication.controller;

import cl.ufro.dci.hackmaster_api.communication.domain.Chat;
import cl.ufro.dci.hackmaster_api.communication.domain.Message;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import cl.ufro.dci.hackmaster_api.communication.service.ChatService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private ChatController chatController;

    // CU-01 CP-01: El usuario despliega su lista de chat correctamente
    @Test
    public void testGetChatsList() {
        Long userId = 1L;
        Participant user = new Participant();
        user.setId(userId);
        Optional<Participant> userOptional = Optional.of(user);
        List<Chat> mockChats = List.of(new Chat(1L, "Chat 1", null, null), new Chat(2L, "Chat 2", null, null));

        when(participantService.findById(userId)).thenReturn(Optional.of(user));
        when(chatService.getChatsByUser(userOptional)).thenReturn(mockChats);

        ResponseEntity<?> response = chatController.getUserChats(userId);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof List<?>);
    }

    // CU-01 CP-02: El usuario despliega su lista de chat vacío mostrando mensaje lista de chat vacío
    @Test
    public void testGetChatsEmptyList() {
        Long userId = 1L;
        Participant user = new Participant();
        user.setId(userId);
        Optional<Participant> userOptional = Optional.of(user);

        when(participantService.findById(userId)).thenReturn(Optional.of(user));
        when(chatService.getChatsByUser(userOptional)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = chatController.getUserChats(userId);
        assertEquals(200, response.getStatusCode().value());
    }

    // CU-01 CP-03: El usuario se une correctamente a un chat
    @Test
    public void testJoinChat() {
        Long userId = 1L;
        Long user2Id = 2L;
        Chat chat = new Chat(1L, "Chat 1", null, null);

        when(chatService.joinChat(userId, user2Id)).thenReturn(chat);

        ResponseEntity<?> response = chatController.joinChat(userId, user2Id);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof Chat);
    }

    // CU-02 CP-01: El actor no escribe su mensaje, lo envía y ocurre un error en la validación
    @Test
    public void testSendEmptyMessage() {
        Long chatId = 1L;
        Long userIdSender = 1L;

        ResponseEntity<?> response = chatController.sendMessage(chatId, userIdSender, "");
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("El mensaje no puede estar vacío"));
    }

    // CU-02 CP-02: El actor escribe su mensaje, se valida y se envía correctamente
    @Test
    public void testSendMessageSuccessfully() {
        Long chatId = 1L;
        Long userIdSender = 1L;
        Participant participant = mock(Participant.class);
        participant.setId(userIdSender);
        Chat chat = new Chat(chatId, "Chat 1", null, null);
        Message message = new Message("Hola", participant, chat);

        when(participantService.findById(userIdSender)).thenReturn(Optional.of(participant));
        when(chatService.getChatById(chatId)).thenReturn(chat);
        when(chatService.sendMessage(chat, participant, "Hola")).thenReturn(message);

        ResponseEntity<?> response = chatController.sendMessage(chatId, userIdSender, "Hola");
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof Message);
    }
}
