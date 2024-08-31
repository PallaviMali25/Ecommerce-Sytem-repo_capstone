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
        //securedEnabled = true,
        //jsr250Enabled =true,
        prePostEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer{
	
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
    
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

  

    	http.csrf(csrf -> csrf.disable())
        .headers(headers -> headers
            .cacheControl(cache -> cache.disable()) // Disable cache if needed
            .frameOptions(frame -> frame.sameOrigin()) // Allow frames from the same origin
        )
        .exceptionHandling(exp -> exp.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/readiness_check", "/liveness_check", "/_ah/start").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll()
            .requestMatchers("/getToken").permitAll()
            .requestMatchers("/customers/**", "/products/**", "/cart-items/**", "/orders/**", "/api1/**","http://localhost:8081/api1/**").authenticated()
           // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").authenticated() // Require authentication for Swagger
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    
    

}}
