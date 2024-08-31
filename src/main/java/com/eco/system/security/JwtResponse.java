package com.eco.system.security;

import java.io.Serializable;

/**
 * Represents the response containing the JWT token.
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 8869344122384649326L;  // Version control for serialization

    private final String jwttoken;  // JWT token to be returned

    /**
     * Constructor to initialize JwtResponse with a JWT token.
     * 
     * @param jwttoken the JWT token
     */
    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    /**
     * Gets the JWT token.
     * 
     * @return the JWT token
     */
    public String getToken() {
        return this.jwttoken;
    }
}
