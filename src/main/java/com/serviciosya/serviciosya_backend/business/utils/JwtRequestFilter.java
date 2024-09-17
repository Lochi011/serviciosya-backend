package com.serviciosya.serviciosya_backend.business.utils;


import com.serviciosya.serviciosya_backend.business.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//
//        // Skip filtering for public endpoints
//        String requestURI = request.getRequestURI();
//        if (requestURI.equals("/api/login") || requestURI.equals("/api/register") || requestURI.equals("/api/solicitud-rubro/pendientes")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//
//        // Check if the Authorization header is present and starts with "Bearer "
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            // Extract the JWT from the Authorization header
//            jwt = authorizationHeader.substring(7);
//            // Extract the username from the JWT token
//            username = jwtUtils.extractUsername(jwt);
//        }
//
//        // If the username is not null and the user is not yet authenticated
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // Load the user details from the database or other source
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//
//            // Validate the token
//            if (jwtUtils.validateToken(jwt, userDetails)) {
//                // Set up the authentication token to pass security checks
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                // Set the security context with the authenticated user
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
