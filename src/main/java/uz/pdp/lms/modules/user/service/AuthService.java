package uz.pdp.lms.modules.user.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.lms.core.config.CustomUserdetailsService;
import uz.pdp.lms.core.config.JwtTokenUtil;
import uz.pdp.lms.core.domain.mapper.UserMapper;
import uz.pdp.lms.core.domain.request.user.LoginRequest;
import uz.pdp.lms.core.domain.request.user.UserRequest;
import uz.pdp.lms.core.domain.response.user.UserResponse;
import uz.pdp.lms.core.excaption.ResourceNotFoundException;
import uz.pdp.lms.modules.user.entity.Role;
import uz.pdp.lms.modules.user.entity.User;
import uz.pdp.lms.modules.user.repository.RoleRepository;
import uz.pdp.lms.modules.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserdetailsService service;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse save(@Valid UserRequest request) {
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default  role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .number(request.getNumber())
                .address(request.getAddress())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .roles(Set.of(defaultRole))
                .build();

        userRepository.save(user);
        return UserMapper.from(user);
    }

    public String login(@Valid LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        UserDetails userDetails = service.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        return jwtTokenUtil.generateToken(username);
    }
}
