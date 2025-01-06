package uz.pdp.lms.modules.test.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.lms.core.common.BaseEntity;

import java.util.List;

@EqualsAndHashCode
@Entity
@Table(
        name = "topic"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Topic extends BaseEntity {
    private String title;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Question> questions;
}
