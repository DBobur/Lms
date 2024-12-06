package uz.pdp.lms.core.domain.mapper;

import uz.pdp.lms.core.domain.response.user.PermissionResponse;
import uz.pdp.lms.modules.user.entity.Permission;

public class PermissionMapper {
    public static PermissionResponse from(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
