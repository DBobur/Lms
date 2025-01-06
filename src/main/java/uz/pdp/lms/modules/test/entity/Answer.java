package uz.pdp.lms.modules.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lms.core.common.BaseEntity;
import uz.pdp.lms.modules.user.entity.User;

@EqualsAndHashCode
@Entity
@Table(
        name = "answer"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Answer extends BaseEntity {

    private String answerText;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isCorrect;
}

