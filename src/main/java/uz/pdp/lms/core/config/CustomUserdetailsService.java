package uz.pdp.lms.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.lms.modules.user.entity.User;
import uz.pdp.lms.modules.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserdetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found: " + username)
        );
    }
}
