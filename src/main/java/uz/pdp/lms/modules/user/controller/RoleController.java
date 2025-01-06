package uz.pdp.lms.modules.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lms.core.domain.request.user.RoleRequest;
import uz.pdp.lms.core.domain.response.user.RoleResponse;
import uz.pdp.lms.modules.user.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        RoleResponse role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    //@PreAuthorize("hasRole('SUPER')")
    @PostMapping()
    public ResponseEntity<String> createRole(
           @Valid @RequestBody RoleRequest roleRequest) {

        // Rol yaratish jarayoni
        RoleResponse newRole = roleService.createRole(roleRequest);

        // Muvaffaqiyatli natija
        return ResponseEntity.ok("Rol: " + newRole);
    }



    @PreAuthorize("hasRole('SUPER')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @RequestBody RoleRequest roleRequest) {
        RoleResponse updatedRole = roleService.updateRole(id, roleRequest);
        return ResponseEntity.ok(updatedRole);
    }
    @PreAuthorize("hasRole('SUPER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

   /* @PreAuthorize("hasRole('SUPER')")
    @PutMapping("/{id}/permissions")
    public ResponseEntity<RoleResponse> updateRolePermissions(
            @PathVariable Long id,
            @RequestBody List<String> permissions) {
        RoleResponse updatedRole = roleService.updateRolePermissions(id, permissions);
        return ResponseEntity.ok(updatedRole);
    }*/

}