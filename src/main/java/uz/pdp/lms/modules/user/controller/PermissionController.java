package uz.pdp.lms.modules.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lms.core.domain.request.user.PermissionRequest;
import uz.pdp.lms.core.domain.response.user.PermissionResponse;
import uz.pdp.lms.modules.user.service.PermissionService;


import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService rolePermissionService;

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {
        List<PermissionResponse> permissions = rolePermissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id) {
        PermissionResponse permission = rolePermissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }

    @PreAuthorize("hasRole('SUPER')")
    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(@Valid @RequestBody PermissionRequest permissionRequest) {
        PermissionResponse newPermission = rolePermissionService.createPermission(permissionRequest);
        return ResponseEntity.ok(newPermission);
    }

    @PreAuthorize("hasRole('SUPER')")
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> updatePermission(@PathVariable Long id, @RequestBody PermissionRequest permissionRequest) {
        PermissionResponse updatedPermission = rolePermissionService.updatePermission(id, permissionRequest);
        return ResponseEntity.ok(updatedPermission);
    }

    @PreAuthorize("hasRole('SUPER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        rolePermissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
