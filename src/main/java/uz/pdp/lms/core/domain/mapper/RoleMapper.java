package uz.pdp.lms.core.domain.mapper;

import uz.pdp.lms.core.domain.request.user.RoleRequest;
import uz.pdp.lms.core.domain.response.user.RoleResponse;
import uz.pdp.lms.modules.user.entity.Permission;
import uz.pdp.lms.modules.user.entity.Role;

import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleResponse fromRes(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(
                        role.getPermissions().stream()
                                .map(Permission::getName)
                                .collect(Collectors.toList())
                )
                .build();
    }
    public static RoleRequest fromReq(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        return RoleRequest.builder()
                .name(role.getName())
                .permissionIds(PermissionMapper.getPermissionIds(role.getPermissions()))
                .build();
    }

}
