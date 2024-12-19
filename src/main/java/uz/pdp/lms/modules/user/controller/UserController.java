package uz.pdp.lms.modules.user.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lms.core.domain.request.user.UserUpdateRequest;
import uz.pdp.lms.core.domain.response.user.UserResponse;
import uz.pdp.lms.modules.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // Get User Methods
    @PreAuthorize("hasAnyAuthority('GET_ALL_ACTIVE_USERS')")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllActiveUsers(
            @RequestParam(name = "roleName", required = false) String roleName,
            @PageableDefault(size = 10, sort = "createdDate") Pageable pageable) {

        log.info("User {} requested getAllUsers with roleName={} and pageable={}",
                SecurityContextHolder.getContext().getAuthentication().getName(), roleName, pageable);

        Page<UserResponse> users = userService.getAllUsers(roleName, pageable, false);
        return ResponseEntity.ok(users);
    }
    @PreAuthorize("hasAnyAuthority('GET_USER_BY_ID')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Update User Methods for Authority users
    @PreAuthorize("hasAnyAuthority('UPDATE_USER_ROLES')")
    @PutMapping("/{id}/roles")
    public ResponseEntity<String> updateUserRoles(
            @PathVariable Long id,
            @RequestBody List<Long> roleIds) {
        userService.updateUserRoles(id, roleIds);
        return ResponseEntity.ok("User roles updated successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(id, userUpdateRequest);
        return ResponseEntity.ok("User updated successfully");
    }



    // Restore User Methods for ADMIN and SUPER
    @PreAuthorize("hasAuthority('RESTORE_USER')")
    @PutMapping("/{id}/restore")
    public ResponseEntity<String> restoreUser(@PathVariable Long id) {
        userService.restoreUser(id);
        return ResponseEntity.ok("User restored successfully");
    }
    @PreAuthorize("hasAnyAuthority('DELETE_USER')")
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam(name = "id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }



    // Restore Password Methods for full users
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String identifier) {
        userService.resetPasswordByIdentifier(identifier);
        return ResponseEntity.ok("Password reset email or SMS sent successfully");
    }
    @PostMapping("/confirm-reset")
    public ResponseEntity<String> confirmPasswordReset(
            @RequestParam String token,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        userService.updatePasswordWithToken(token, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }



    /*@PreAuthorize("hasAuthority('VIEW_USER_STATS')")
    @GetMapping("/stats")
    public ResponseEntity<UserStatisticsResponse> getUserStatistics() {
        UserStatisticsResponse stats = userService.getUserStatistics();
        return ResponseEntity.ok(stats);
    }*/

    /*@PreAuthorize("hasAuthority('UPDATE_USER_STATUS')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateUserStatus(@PathVariable Long id, @RequestParam boolean active) {
        userService.updateUserStatus(id, active);
        return ResponseEntity.ok("User status updated successfully");
    }*/


}
