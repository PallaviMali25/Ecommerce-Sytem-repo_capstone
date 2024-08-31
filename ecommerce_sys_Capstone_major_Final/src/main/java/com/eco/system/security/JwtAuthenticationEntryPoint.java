package com.eco.system.security;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom entry point for handling authentication errors.
 * This component is triggered when an unauthorized request is made.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L; // Serial version UID for serialization compatibility

    /**
     * Called when an unauthenticated user attempts to access a protected resource.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @param authException the exception that was thrown during authentication
     * @throws IOException if an input or output error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Send a 401 Unauthorized response status code to the client
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
