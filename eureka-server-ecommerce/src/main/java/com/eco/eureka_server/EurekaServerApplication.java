package com.eco.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The main class for the Eureka Server application.
 * This application acts as a discovery server for registering microservices 
 * and enabling them to communicate with each other.
 */
@SpringBootApplication
@EnableEurekaServer // Annotation to enable the Eureka Server functionality.
public class EurekaServerApplication {

    /**
     * The main method that serves as the entry point of the Eureka Server application.
     *
     * @param args Command-line arguments (if any) passed to the application.
     */
    public static void main(String[] args) {
        // Launch the Eureka Server application.
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
