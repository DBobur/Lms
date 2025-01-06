package uz.pdp.lms.core.common;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lms.modules.schedule.entity.Schedule;
import uz.pdp.lms.modules.user.entity.User;

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
public class Group extends BaseEntity{
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
