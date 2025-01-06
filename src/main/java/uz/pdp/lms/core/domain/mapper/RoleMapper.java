package uz.pdp.lms.core.domain.mapper;

import lombok.RequiredArgsConstructor;
import uz.pdp.lms.core.domain.request.user.RoleRequest;
import uz.pdp.lms.core.domain.response.user.RoleResponse;
import uz.pdp.lms.core.excaption.ResourceNotFoundException;
import uz.pdp.lms.modules.user.entity.Permission;
import uz.pdp.lms.modules.user.entity.Role;
import uz.pdp.lms.modules.user.repository.PermissionRepository;

import java.lang.module.ResolutionException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RoleMapper {
    private final PermissionRepository permissionRepository;
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

    public Role toRole(RoleRequest roleRequest) {
        if (roleRequest == null) {
            throw new ResolutionException("Role ma'lumotlari bo'sh bo'lishi mumkin emas!");
        }

        Role role = new Role();
        role.setName(roleRequest.getName());

        if (roleRequest.getPermissions() != null) {
            Set<Permission> permissions = roleRequest.getPermissions().stream()
                    .map(permissionName -> permissionRepository.findByName(permissionName)
                            .orElseThrow(() -> new ResourceNotFoundException("Permission '" + permissionName + "' topilmadi!"))
                    )
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }

        return role;
    }
}
