package com.eco.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        // Enable method security with prePost annotations
        prePostEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer {

    /**
     * Configures CORS to allow requests from any origin and methods.
     * 
     * @param registry the CorsRegistry to configure
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Configures the AuthenticationManager bean for managing authentication.
     * 
     * @param authenticationConfiguration the configuration to get the AuthenticationManager from
     * @return the AuthenticationManager
     * @throws Exception if an error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the PasswordEncoder bean to use BCrypt for password encoding.
     * 
     * @return the PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the SecurityFilterChain for HTTP security settings.
     * 
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Disable CSRF protection
            .headers(headers -> headers
                .cacheControl(cache -> cache.disable())  // Disable caching
                .frameOptions(frame -> frame.sameOrigin())  // Allow frames from the same origin
            )
            .exceptionHandling(exp -> exp.authenticationEntryPoint(jwtAuthenticationEntryPoint))  // Set custom authentication entry point
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session management
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/readiness_check", "/liveness_check", "/_ah/start").permitAll()  // Allow public access to health check endpoints
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll()  // Allow public access to Swagger UI and Actuator endpoints
                .requestMatchers("/getToken").permitAll()  // Allow public access to token endpoint
                .requestMatchers("/customers/**", "/products/**", "/cart-items/**", "/orders/**", "/api1/**", "http://localhost:8081/api1/**").authenticated()  // Require authentication for these endpoints
                .anyRequest().authenticated()  // Require authentication for all other endpoints
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before UsernamePasswordAuthenticationFilter

        return http.build();
    }
}
