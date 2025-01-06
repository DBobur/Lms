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
                        permissionRepository.findAllByNameIn(roleRequest.getPermissions())
                )
                .build();
        Role savedRole = roleRepository.save(role);
        return RoleMapper.fromRes(savedRole);
    }

    @Transactional
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));

        // Update name if present
        BaseHelper.updateIfPresent(roleRequest.getName(), role::setName);

        // Update permissions by name if present
        BaseHelper.updateIfPresent(
                roleRequest.getPermissions(),
                permissionNames -> {
                    Set<Permission> permissions = validatePermissions(permissionNames);
                    role.setPermissions(permissions);
                }
        );

        // Save the updated role
        Role updatedRole = roleRepository.save(role);

        return RoleMapper.fromRes(updatedRole);
    }


    private Set<Permission> validatePermissions(Set<String> permissionNames) {
        // Find permissions by their names
        Set<Permission> permissions = permissionRepository.findAllByNameIn(permissionNames);

        // Get the set of names of the found permissions
        Set<String> foundNames = permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        // Find names that are missing
        Set<String> missingNames = permissionNames.stream()
                .filter(name -> !foundNames.contains(name))
                .collect(Collectors.toSet());

        if (!missingNames.isEmpty()) {
            throw new IllegalArgumentException("Permissions with names " + missingNames + " not found");
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
