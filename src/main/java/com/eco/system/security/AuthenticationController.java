package com.eco.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication requests and generating JWT tokens.
 */
@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager; // Manages authentication operations

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Utility class for generating JWT tokens

    @Autowired
    private JwtUserDetailsService userDetailsService; // Service for loading user-specific details

    /**
     * Endpoint to authenticate a user and generate a JWT token.
     * 
     * @param authenticationRequest contains username and password for authentication
     * @return ResponseEntity containing the JWT token or an error message
     * @throws Exception 
     */
    @PostMapping("/getToken")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        try {
            // Attempt to authenticate the user with the provided username and password
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (DisabledException e) {
            // Handle case where user account is disabled
            return new ResponseEntity<>(new JwtResponse("User disabled"), HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException e) {
            // Handle case where provided credentials are invalid
            return new ResponseEntity<>(new JwtResponse("Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }

        // Load user details from the service
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        
        // Generate JWT token based on user details
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the generated token in the response
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * Method to authenticate a user with the given username and password.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @throws Exception if authentication fails due to disabled account or bad credentials
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            // Create an authentication token and authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            // Rethrow the exception to be handled by the calling method
            throw e;
        }
    }
}
