package uz.pdp.lms.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lms.core.common.BaseEntity;

@EqualsAndHashCode
@Entity
@Table(
        name = "contract"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Contract extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String contractDetails;

}
