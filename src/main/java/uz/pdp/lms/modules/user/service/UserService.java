package uz.pdp.lms.modules.user.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lms.core.domain.mapper.UserMapper;
import uz.pdp.lms.core.domain.request.user.UserUpdateRequest;
import uz.pdp.lms.core.domain.response.user.UserResponse;
import uz.pdp.lms.core.excaption.ResourceNotFoundException;
import uz.pdp.lms.core.heper.BaseHelper;
import uz.pdp.lms.core.specification.UserSpecifications;
import uz.pdp.lms.modules.user.entity.Role;
import uz.pdp.lms.modules.user.entity.User;
import uz.pdp.lms.modules.user.repository.RoleRepository;
import uz.pdp.lms.modules.user.repository.UserRepository;
import uz.pdp.lms.modules.user.service.notification.EmailService;
import uz.pdp.lms.modules.user.service.notification.SmsService;
import uz.pdp.lms.modules.user.util.PasswordResetToken;
import uz.pdp.lms.modules.user.util.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SmsService smsService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public void updateUser(Long id, @Valid UserUpdateRequest userUpdateRequest) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        BaseHelper.updateIfPresent(userUpdateRequest.getFullName(), existingUser::setFullName);
        BaseHelper.updateIfPresent(userUpdateRequest.getUsername(), existingUser::setUsername);
        BaseHelper.updateIfPresent(userUpdateRequest.getPassword(),
                password -> existingUser.setPassword(passwordEncoder.encode(password)));
        BaseHelper.updateIfPresent(userUpdateRequest.getEmail(), existingUser::setEmail);
        BaseHelper.updateIfPresent(userUpdateRequest.getNumber(), existingUser::setNumber);

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found or already deleted"));

        user.setDeleted(true);
        userRepository.save(user);
    }

    @Transactional
    public void restoreUser(Long id) {
        User user = userRepository.findByIdAndIsDeleted(id, true)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found or is not deleted"));

        user.setDeleted(false);
        userRepository.save(user);
    }



    public Page<UserResponse> getAllUsers(String roleName, Pageable pageable, boolean active) {
        Specification<User> spec = Specification.where(UserSpecifications.hasRoleName(roleName));
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(UserMapper::from);
    }


    public UserResponse getUserById(Long id) {
        return UserMapper.from(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found")));
    }

    @Transactional
    public void updateUserRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        if (roleIds == null || roleIds.isEmpty()) {
            throw new IllegalArgumentException("Role IDs must not be empty. Please provide valid role IDs.");
        }

        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            List<Long> missingRoleIds = roleIds.stream()
                    .filter(id -> roles.stream().noneMatch(role -> role.getId().equals(id)))
                    .toList();
            throw new ResourceNotFoundException("Roles not found for IDs: " + missingRoleIds);
        }

        user.setRoles(new ArrayList<>(roles));
        userRepository.save(user);
    }

    public void resetPasswordByIdentifier(String identifier) {
        User user = userRepository.findByEmailOrNumber(identifier, identifier)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given "+identifier));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(user.getId(), token,LocalDateTime.now().plusHours(1));

        passwordResetTokenRepository.save(resetToken);

        if (identifier.contains("@")) { // Email
            // todo: emailService.sendPasswordResetEmail(user.getEmail(), token);
        } else {
            // smsService.sendPasswordResetSms(user.getPhoneNumber(), token); // not configurations
            logger.warn("Password reset SMS sending is skipped as SMS configuration is missing for: {}", user.getNumber());
        }
    }

    public void updatePasswordWithToken(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));

        User user = userRepository.findById(resetToken.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + resetToken.getUserId() + " not found"));

        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }

}
