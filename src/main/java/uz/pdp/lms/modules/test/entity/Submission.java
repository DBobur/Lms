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
        name = "submission"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Submission extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option selectedOption;

    private boolean isCorrect;
}
