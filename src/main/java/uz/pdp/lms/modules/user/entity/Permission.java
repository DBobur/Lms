package uz.pdp.lms.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.lms.core.common.BaseEntity;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "roles")
@Entity
@Table(
        name = "permissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles;
}
