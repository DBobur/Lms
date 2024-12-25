package uz.pdp.lms.modules.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pdp.lms.core.domain.mapper.RoleMapper;
import uz.pdp.lms.core.domain.request.user.RoleRequest;
import uz.pdp.lms.core.domain.response.user.RoleResponse;
import uz.pdp.lms.core.excaption.ResourceNotFoundException;
import uz.pdp.lms.core.heper.BaseHelper;
import uz.pdp.lms.modules.user.entity.Permission;
import uz.pdp.lms.modules.user.entity.Role;
import uz.pdp.lms.modules.user.repository.PermissionRepository;
import uz.pdp.lms.modules.user.repository.RoleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private static final Logger log = LoggerFactory.getLogger(RoleService.class);


    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::fromRes)
                .toList();
    }

    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));
        return RoleMapper.fromRes(role);
    }

    @Transactional
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = Role.builder()
                .name(roleRequest.getName())
                .permissions(
                        permissionRepository.findAllByIdIn(roleRequest.getPermissionIds())
                )
                .build();
        Role savedRole = roleRepository.save(role);
        return RoleMapper.fromRes(savedRole);
    }

    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));

        BaseHelper.updateIfPresent(roleRequest.getName(),role::setName);
        BaseHelper.updateIfPresent(
                roleRequest.getPermissionIds(),
                permissionIds -> {
                    Set<Permission> permissions = validatePermissions(permissionIds);
                    role.setPermissions(permissions);
                }
        );
        Role updatedRole = roleRepository.save(role);

        return RoleMapper.fromRes(updatedRole);
    }

    private Set<Permission> validatePermissions(Set<Long> permissionIds) {
        Set<Permission> permissions = permissionRepository.findAllByIdIn(permissionIds);
        Set<Long> foundIds = permissions.stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());
        Set<Long> missingIds = permissionIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException("Permissions with IDs " + missingIds + " not found");
        }

        return permissions;
    }

    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role with id " + id + " not found");
        }
        roleRepository.deleteById(id);
    }

    public Role saveIfNotExists(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
            log.info("Permission not found, creating new: {}", name);
            Role newRole = Role.builder()
                    .name(name)
                    .build();
            return roleRepository.save(newRole);
        });
    }
}
