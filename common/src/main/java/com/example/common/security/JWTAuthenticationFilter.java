package com.example.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {//To avoid Bearer? being passed
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtTokenUtil.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.isTokenValid(token)) {
                //Extract ROLE - could be a list - but in this case we have just one role per user
                String roleFound = jwtTokenUtil.extractRole(token);
                if (roleFound != null && !roleFound.isBlank()) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username,
                                                        null,
                                                            List.of(new SimpleGrantedAuthority(roleFound)));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                    SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(a ->
//                            System.out.println("Granted authority: " + a.getAuthority())
//                    );
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
