package uz.pdp.lms.core.domain.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@ToString
@Setter
@AllArgsConstructor
public class RoleRequest {

    @NotBlank(message = "Role name must not be blank")
    private final String name;

    @NotEmpty(message = "Permission IDs must not be empty")
    private final Set<Long> permissionIds;
}
