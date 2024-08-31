package com.eco.system.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter to process JWT authentication for incoming requests.
 * Extends OncePerRequestFilter to ensure it is executed once per request.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;  // Service to load user-specific data

    @Autowired
    private JwtTokenUtil jwtTokenUtil;  // Utility class to handle JWT operations

    /**
     * Filters the incoming request and performs JWT authentication.
     * 
     * @param request the incoming HTTP request
     * @param response the HTTP response
     * @param chain the filter chain to pass the request and response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");  // Get the Authorization header

        String username = null;
        String jwtToken = null;

        // Check if the header is present and starts with "Bearer "
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);  // Remove "Bearer " from the token
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);  // Extract username from the token
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to get token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token is expired");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer word");
        }

        // If username is extracted and no authentication is set in context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);  // Load user details

            // Validate the token and set authentication if valid
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());  // Create authentication token
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // Set details

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);  // Set authentication in context
            }
        }
        chain.doFilter(request, response);  // Continue with the next filter in the chain
    }
}
