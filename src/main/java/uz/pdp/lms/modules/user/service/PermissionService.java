package uz.pdp.lms.modules.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pdp.lms.core.domain.mapper.PermissionMapper;
import uz.pdp.lms.core.domain.request.user.PermissionRequest;
import uz.pdp.lms.core.domain.response.user.PermissionResponse;
import uz.pdp.lms.core.excaption.ResourceNotFoundException;
import uz.pdp.lms.core.heper.BaseHelper;
import uz.pdp.lms.modules.user.entity.Permission;
import uz.pdp.lms.modules.user.repository.PermissionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

    private final PermissionRepository permissionRepository;

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(PermissionMapper::from)
                .collect(Collectors.toList());
    }

    public PermissionResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission with id " + id + " not found"));
        return PermissionMapper.from(permission);
    }

    @Transactional
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = Permission.builder()
                .name(permissionRequest.getName())
                .description(permissionRequest.getDescription())
                .build();
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionMapper.from(savedPermission);
    }

    @Transactional
    public PermissionResponse updatePermission(Long id, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission with id " + id + " not found"));

        BaseHelper.updateIfPresent(permissionRequest.getName(),permission::setName);
        BaseHelper.updateIfPresent(permissionRequest.getDescription(),permission::setDescription);

        Permission updatedPermission = permissionRepository.save(permission);
        return PermissionMapper.from(updatedPermission);
    }

    @Transactional
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission with id " + id + " not found");
        }
        permissionRepository.deleteById(id);
    }

    @Transactional
    public Permission saveIfNotExists(String name, String description) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> {
                    log.info("Permission not found, creating new: {}", name);
                    Permission newPermission = Permission.builder()
                            .name(name)
                            .description(description)
                            .build();
                    return permissionRepository.save(newPermission);
                });
    }

}
