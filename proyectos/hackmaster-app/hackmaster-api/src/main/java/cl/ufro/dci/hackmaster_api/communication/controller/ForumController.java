package cl.ufro.dci.hackmaster_api.communication.controller;

import cl.ufro.dci.hackmaster_api.communication.domain.ForumTopic;
import cl.ufro.dci.hackmaster_api.communication.domain.ForumReply;
import cl.ufro.dci.hackmaster_api.communication.service.ForumService;
import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
import cl.ufro.dci.hackmaster_api.registration.service.OrganizationService;
import cl.ufro.dci.hackmaster_api.registration.service.ParticipantService;
import cl.ufro.dci.hackmaster_api.registration.service.UserRegService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/forums")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private OrganizationService organizationService;

    // Método para obtener el servicio de usuario según el tipo de usuario
    private UserRegService<? extends UserReg> getUserService(String userType) {
        switch (userType.toLowerCase()) {
            case "participant":
                return participantService;
            case "organization":
                return organizationService;
            default:
                throw new IllegalArgumentException("Tipo de usuario no soportado: " + userType);
        }
    }

    @PostMapping(value = "/topics", consumes = "application/json")
    public ForumTopic createTopic(@Valid @RequestBody ForumTopic topic, @RequestParam Long userId, @RequestParam String userType) {
        UserRegService<? extends UserReg> userService = getUserService(userType);
        UserReg user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        topic.setUser(user);
        return forumService.createTopic(topic, user);
    }

    @PostMapping("/topics/{topicId}/replies")
    public ForumReply addReply(@PathVariable Long topicId, @Valid @RequestBody ForumReply reply, @RequestParam Long userId, @RequestParam String userType) {
        UserRegService<? extends UserReg> userService = getUserService(userType);
        UserReg user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        reply.setUser(user);
        return forumService.addReplyToTopic(topicId, reply, user);
    }

    @PostMapping("/replies/{replyId}/subreplies")
    public ForumReply addSubReply(@PathVariable Long replyId, @Valid @RequestBody ForumReply subReply, @RequestParam Long userId, @RequestParam String userType) {
        UserRegService<? extends UserReg> userService = getUserService(userType);
        UserReg user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        subReply.setUser(user);
        return forumService.addSubReply(replyId, subReply, user);
    }

    @GetMapping("/topics")
    public List<ForumTopic> getAllTopics() {
        return forumService.getAllTopics();
    }

    @GetMapping("/topics/id/{id}")
    public ForumTopic getTopicById(@PathVariable Long id) {
        return forumService.getTopicById(id);
    }

    // Obtener un tema específico por slug en lugar de título
    @GetMapping("/topics/slug/{slug}")
    public ForumTopic getTopicBySlug(@PathVariable String slug) {
        return forumService.getTopicBySlug(slug);
    }

    // Manejador de excepciones personalizado
    @ExceptionHandler({ IllegalArgumentException.class, RuntimeException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
