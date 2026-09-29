package cl.ufro.dci.hackmaster_api.communication.domain;

import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ForumTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserReg user;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "topic-replies")
    private List<ForumReply> replies = new ArrayList<>();

    private LocalDateTime createdAt;

    private String slug; // Nuevo campo para el slug

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PostPersist
    protected void onPostPersist() {
        if (this.slug == null || this.slug.isEmpty()) {
            this.slug = generateSlug();
        }
    }

    private String generateSlug() {
        String normalizedTitle = Normalizer.normalize(this.title, Normalizer.Form.NFD);
        String slug = normalizedTitle.replaceAll("[^\\p{ASCII}]", ""); // Elimina tildes
        slug = slug.replaceAll("[^a-zA-Z0-9\\s]", ""); // Elimina caracteres especiales
        slug = slug.replaceAll("\\s+", "-"); // Reemplaza espacios por guiones
        return slug.toLowerCase() + "-" + this.id; // Agrega el ID al final del slug
    }
}
