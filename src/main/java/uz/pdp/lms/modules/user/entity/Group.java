package uz.pdp.lms.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lms.core.common.BaseEntity;
import uz.pdp.lms.modules.attendance.entity.Schedule;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode
@Entity
@Table(
        name = "groups"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Group extends BaseEntity {
    private String groupName;

    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

}
