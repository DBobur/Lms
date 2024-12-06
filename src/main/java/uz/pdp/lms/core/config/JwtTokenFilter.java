package uz.pdp.lms.core.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // 1. Authorization sarlavhasini tekshirish
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("Authorization sarlavhasi topilmadi yoki noto'g'ri format: {}", authorization);
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);

        // 2. Token validatsiyasi
        if (!jwtTokenUtil.isValid(token)) {
            log.warn("Yaroqsiz token: {}", token);
            filterChain.doFilter(request, response);
            return;
        }

        // 3. SecurityContextHolder'ni tekshirish
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4. Tokenning foydalanuvchi ma'lumotlarini olish
        String username = jwtTokenUtil.getUsername(token);
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            log.error("Foydalanuvchi topilmadi: {}", username, e);
            filterChain.doFilter(request, response);
            return;
        }

        // 5. SecurityContextHolder'ga foydalanuvchini o'rnatish
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 6. Filtrni davom ettirish
        filterChain.doFilter(request, response);
    }
}
