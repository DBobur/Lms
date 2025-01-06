package uz.pdp.lms.modules.test.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lms.core.common.BaseEntity;

import java.util.List;

@EqualsAndHashCode
@Entity
@Table(
        name = "question"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Question extends BaseEntity {
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;

    // Constructors, Getters, Setters
}

