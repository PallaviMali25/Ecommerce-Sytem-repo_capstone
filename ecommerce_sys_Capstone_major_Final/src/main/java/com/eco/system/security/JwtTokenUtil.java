package com.eco.system.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;  // Token validity set to 1 hour

    //@Value("${jwt.secret}")
    private String secret = "javatrainingschool";  // Secret key used for signing the token

    /**
     * Extracts the username from the JWT token.
     * 
     * @param token the JWT token
     * @return the username from the token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT token.
     * 
     * @param token the JWT token
     * @return the expiration date of the token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Retrieves a specific claim from the JWT token.
     * 
     * @param token the JWT token
     * @param claimsResolver a function to extract the claim from the Claims
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses the JWT token to extract all claims.
     * 
     * @param token the JWT token
     * @return the claims from the token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the JWT token is expired.
     * 
     * @param token the JWT token
     * @return true if the token is expired, otherwise false
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generates a new JWT token for the given user details.
     * 
     * @param userDetails the user details
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Creates a JWT token with claims and subject, then signs it.
     * 
     * @param claims the claims to be included in the token
     * @param subject the subject of the token
     * @return the compacted JWT token as a string
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)  // Set claims
                .setSubject(subject)  // Set subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set issued date
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))  // Set expiration date
                .signWith(SignatureAlgorithm.HS512, secret)  // Sign the token with HS512 algorithm and secret key
                .compact();  // Compact the token to a URL-safe string
    }

    /**
     * Validates the JWT token against the provided user details.
     * 
     * @param token the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, otherwise false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
