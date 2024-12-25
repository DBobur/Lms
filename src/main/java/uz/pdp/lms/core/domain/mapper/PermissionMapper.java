package uz.pdp.lms.core.domain.mapper;

import jakarta.validation.constraints.NotEmpty;
import uz.pdp.lms.core.domain.response.user.PermissionResponse;
import uz.pdp.lms.modules.user.entity.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public class PermissionMapper {
    public static PermissionResponse from(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }

    public static @NotEmpty(message = "Permission IDs must not be empty")
    Set<Long> getPermissionIds(Set<Permission> permissions) {
        return permissions.stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());
    }

}
