package com.eco.api_gateway_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.CorsRegistry;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayEcommerceApplication.class, args);
	}
	
	//it will allow common port APIgateway http://localhost:8081
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/").allowedOrigins("http://localhost:8081")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true);
	}

	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ECOMMERCE-SYSTEM", r -> r.path("/api1/**")
                        .uri("lb://ECOMMERCE-SYSTEM"))
                .build();
    }
}
