package uz.pdp.lms;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.lms.core.domain.mapper.RoleMapper;
import uz.pdp.lms.modules.user.entity.Permission;
import uz.pdp.lms.modules.user.entity.Role;
import uz.pdp.lms.modules.user.entity.User;
import uz.pdp.lms.modules.user.repository.RoleRepository;
import uz.pdp.lms.modules.user.service.AuthService;
import uz.pdp.lms.modules.user.service.PermissionService;
import uz.pdp.lms.modules.user.service.RoleService;
import uz.pdp.lms.modules.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;

    @Override
    public void run(String... args) throws Exception {
        Permission readPermission = permissionService.saveIfNotExists("READ","Only Read");
        Permission writePermission = permissionService.saveIfNotExists("WRITE","Only Write");

        Role superAdminRole = roleService.saveIfNotExists("SUPER_ADMIN");
        superAdminRole.setPermissions(Set.of(readPermission, writePermission));
        roleRepository.save(superAdminRole);
        User superAdmin = userService.findByUsername("superadmin");
        if (superAdmin == null) {
            superAdmin = new User();
            superAdmin.setFullName("D Bobur");
            superAdmin.setCreatedDate(LocalDateTime.now());
            superAdmin.setUpdatedDate(LocalDateTime.now());
            superAdmin.setUsername("superadmin");
            superAdmin.setPassword(passwordEncoder.encode("superadmin"));
            superAdmin.setEmail("superadmin@example.com");
            superAdmin.setNumber("+998939020321");
            superAdmin.setDeleted(false);
            superAdmin.setRoles(Set.of(superAdminRole));
            userService.save(superAdmin);
        }
    }
}

