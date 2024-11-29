package uz.pro.usm.domain.entity.user;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import uz.pro.usm.domain.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"}),
        indexes = @Index(name = "idx_role_name", columnList = "name")
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;

    @Override
    public String getAuthority() {
        return name;
    }

}


