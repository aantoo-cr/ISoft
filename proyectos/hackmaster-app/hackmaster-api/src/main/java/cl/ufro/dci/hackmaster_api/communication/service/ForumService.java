package cl.ufro.dci.hackmaster_api.communication.service;

import cl.ufro.dci.hackmaster_api.communication.domain.ForumTopic;
import cl.ufro.dci.hackmaster_api.communication.domain.ForumReply;
import cl.ufro.dci.hackmaster_api.communication.repository.ForumRepository;
import cl.ufro.dci.hackmaster_api.communication.repository.ForumReplyRepository;
import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumReplyRepository forumReplyRepository;

    // Crear un nuevo tema en el foro
    public ForumTopic createTopic(ForumTopic topic, UserReg user) {
        topic.setUser(user);
        topic.setCreatedAt(LocalDateTime.now());
        
        // Generar slug provisional basado en el título
        String provisionalSlug = generateSlug(topic.getTitle());
        topic.setSlug(provisionalSlug);
        
        // Guardar el tema para generar el ID
        ForumTopic savedTopic = forumRepository.save(topic);
        
        // Actualizar el slug con el ID único
        String finalSlug = provisionalSlug + "-" + savedTopic.getId();
        savedTopic.setSlug(finalSlug);
        
        // Guardar nuevamente con el slug definitivo
        return forumRepository.save(savedTopic);
    }    

    // Añadir una respuesta a un tema específico
    public ForumReply addReplyToTopic(Long topicId, ForumReply reply, UserReg user) {
        ForumTopic topic = forumRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado"));

        reply.setTopic(topic);
        reply.setUser(user);
        reply.setCreatedAt(LocalDateTime.now());
        return forumReplyRepository.save(reply);
    }

    // Añadir una subrespuesta a una respuesta específica
    public ForumReply addSubReply(Long parentReplyId, ForumReply subReply, UserReg user) {
        ForumReply parentReply = forumReplyRepository.findById(parentReplyId)
                .orElseThrow(() -> new RuntimeException("Respuesta padre no encontrada"));

        subReply.setParentReply(parentReply);
        subReply.setUser(user);
        subReply.setCreatedAt(LocalDateTime.now());
        subReply.setTopic(parentReply.getTopic());
        return forumReplyRepository.save(subReply);
    }

    // Obtener todos los temas del foro
    public List<ForumTopic> getAllTopics() {
        return (List<ForumTopic>) forumRepository.findAll();
    }

    // Obtener un tema específico por ID
    public ForumTopic getTopicById(Long id) {
        return forumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado"));
    }

    // Obtener un tema específico por slug
    public ForumTopic getTopicBySlug(String slug) {
        return forumRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado"));
    }

    // Método para generar un slug a partir del título
    private String generateSlug(String title) {
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("[^\\p{ASCII}]", "") // Eliminar tildes
                .replaceAll("[^a-zA-Z0-9\\s-]", "") // Eliminar caracteres especiales
                .trim() // Eliminar espacios al inicio y final
                .replaceAll("\\s+", "-") // Reemplazar espacios por guiones
                .toLowerCase(); // Convertir a minúsculas
        return slug;
    }
}
