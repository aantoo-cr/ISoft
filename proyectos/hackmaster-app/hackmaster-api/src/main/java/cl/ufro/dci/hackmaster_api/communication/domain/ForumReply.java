package cl.ufro.dci.hackmaster_api.communication.domain;

import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ForumReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    @JsonBackReference(value = "topic-replies")
    private ForumTopic topic;

    @NotBlank
    @Size(max = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserReg user;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "parentReply", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "subreply-replies")
    private List<ForumReply> subReplies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    @JsonBackReference(value = "subreply-replies")
    private ForumReply parentReply;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
