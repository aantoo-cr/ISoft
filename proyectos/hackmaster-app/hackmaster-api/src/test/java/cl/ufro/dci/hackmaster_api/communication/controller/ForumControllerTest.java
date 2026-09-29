package cl.ufro.dci.hackmaster_api.communication.controller;

import cl.ufro.dci.hackmaster_api.communication.domain.ForumReply;
import cl.ufro.dci.hackmaster_api.communication.domain.ForumTopic;
import cl.ufro.dci.hackmaster_api.communication.service.ForumService;
import cl.ufro.dci.hackmaster_api.registration.domain.Participant;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ForumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForumService forumService;

    @MockBean
    private ParticipantService participantService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void shouldReturnForumTopicsList() throws Exception {
        ForumTopic topic = new ForumTopic();
        topic.setTitle("Tema de Prueba");
        topic.setCreatedAt(LocalDateTime.now());

        Mockito.when(forumService.getAllTopics()).thenReturn(Collections.singletonList(topic));

        mockMvc.perform(get("/api/forums/topics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Tema de Prueba"))
                .andExpect(jsonPath("$[0].createdAt").exists());
    }

    @Test
    public void shouldCreateNewForumTopic() throws Exception {
        ForumTopic newTopic = new ForumTopic();
        newTopic.setTitle("Nuevo Tema");
        newTopic.setDescription("Descripción del nuevo tema");
        newTopic.setCreatedAt(LocalDateTime.now());

        Participant participant = new Participant();
        participant.setId(1L);

        Mockito.when(participantService.findById(1L)).thenReturn(Optional.of(participant));
        Mockito.when(forumService.createTopic(Mockito.any(ForumTopic.class), Mockito.any(Participant.class)))
                .thenReturn(newTopic);

        mockMvc.perform(post("/api/forums/topics")
                        .param("userId", "1")
                        .param("userType", "participant")
                        .content(objectMapper.writeValueAsString(newTopic))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Nuevo Tema"))
                .andExpect(jsonPath("$.description").value("Descripción del nuevo tema"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    public void shouldReturnErrorForEmptyFields() throws Exception {
        mockMvc.perform(post("/api/forums/topics")
                        .param("userId", "1")
                        .param("userType", "participant")
                        .content("{\"title\": \"\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnErrorForEmptyDescription() throws Exception {
        mockMvc.perform(post("/api/forums/topics")
                        .param("userId", "1")
                        .param("userType", "participant")
                        .content("{\"title\": \"Nuevo Tema\", \"description\": \"\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnErrorForTitleExceedingMaxLength() throws Exception {
        String longTitle = "a".repeat(256);
        mockMvc.perform(post("/api/forums/topics")
                        .param("userId", "1")
                        .param("userType", "participant")
                        .content("{\"title\": \"" + longTitle + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAddReplyToTopic() throws Exception {
        ForumReply reply = new ForumReply();
        reply.setContent("Respuesta de prueba");
        reply.setCreatedAt(LocalDateTime.now());

        Participant participant = new Participant();
        participant.setId(1L);

        Mockito.when(participantService.findById(1L)).thenReturn(Optional.of(participant));
        Mockito.when(forumService.addReplyToTopic(Mockito.anyLong(), Mockito.any(ForumReply.class), Mockito.any(Participant.class)))
                .thenReturn(reply);

        mockMvc.perform(post("/api/forums/topics/1/replies")
                        .param("userId", "1")
                        .param("userType", "participant")
                        .content("{\"content\": \"Respuesta de prueba\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Respuesta de prueba"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    public void shouldReturnErrorForEmptyReply() throws Exception {
        mockMvc.perform(post("/api/forums/topics/1/replies")
                        .param("userId", "1")
                        .param("userType", "participant")
                        .content("{\"content\": \"\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
