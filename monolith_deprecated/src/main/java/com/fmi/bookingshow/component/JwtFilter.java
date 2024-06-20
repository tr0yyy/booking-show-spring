package com.fmi.bookingshow.component;

import com.fmi.bookingshow.model.UserEntity;
import com.fmi.bookingshow.repository.UserRepository;
import com.fmi.bookingshow.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtSecurity jwtSecurity;
    private final UserRepository userRepository;

    public JwtFilter(JwtSecurity jwtSecurity, UserRepository userRepository) {
        this.jwtSecurity = jwtSecurity;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.info("JWT Filter issued");
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            log.info("Missing authorization header");
            chain.doFilter(request, response);
            return;
        }
        String token = header.split(" ")[1].trim();
        String username = jwtSecurity.validateTokenAndReturnUsername(token);
        if (username == null) {
            log.info("Missing username from bearer token");
            chain.doFilter(request, response);
            return;
        }
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.info("Missing user with username provided in bearer token");
            chain.doFilter(request, response);
            return;
        }
        log.info("Validation passed, forwarding request with user's claims");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
}
