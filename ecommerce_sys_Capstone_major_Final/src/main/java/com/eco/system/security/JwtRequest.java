package com.eco.system.security;

import java.io.Serializable;

/**
 * Class representing a request to authenticate with a username and password.
 * This is used to carry authentication data in the request.
 */
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 6239921560724451905L; // Serial version UID for serialization compatibility
    
    private String username;  // The username for authentication
    private String password;  // The password for authentication
    
    /**
     * Default constructor.
     * Initializes a new instance of JwtRequest with no username or password.
     */
    public JwtRequest() {
        // Default constructor
    }

    /**
     * Parameterized constructor.
     * Initializes a new instance of JwtRequest with the given username and password.
     * 
     * @param username the username for authentication
     * @param password the password for authentication
     */
    public JwtRequest(String username, String password) {
        this.setUsername(username);  // Set the username
        this.setPassword(password);  // Set the password
    }

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
