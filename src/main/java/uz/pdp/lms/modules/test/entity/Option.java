package uz.pdp.lms.modules.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lms.core.common.BaseEntity;

@EqualsAndHashCode
@Entity
@Table(
        name = "option"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Option extends BaseEntity {
    private String optionText;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}

