package uz.pro.usm.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import uz.pro.usm.domain.entity.BaseEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
    private List<Role> roles;
}
