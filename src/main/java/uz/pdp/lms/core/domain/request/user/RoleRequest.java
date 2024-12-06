package uz.pdp.lms.core.domain.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class RoleRequest {

    @NotBlank(message = "Role name must not be blank")
    private final String name;

    @NotEmpty(message = "Permission IDs must not be empty")
    private final List<Long> permissionIds;
}
