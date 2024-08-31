package com.eco.system.controller;

// Import necessary classes and libraries
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eco.system.beans.ProductDTO;
import com.eco.system.entity.Product;
import com.eco.system.exception.ProductNotFoundException;
import com.eco.system.serviceImplementation.ProductService;

import jakarta.validation.Valid;

@RestController // Indicates that this class is a REST controller
@RequestMapping("/products") // Base URL for all endpoints related to products
@CrossOrigin(origins = "http://localhost:8081") // Enables CORS for the specified origin
@Validated // Enables validation for request data in the controller
public class ProductController {

    @Autowired // Injects the ProductService bean for dependency injection
    private ProductService productService;

    /**
     * GET endpoint to retrieve all products.
     * 
     * @return List of all products.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * GET endpoint to retrieve a product by its ID.
     * 
     * @param productId ID of the product to retrieve.
     * @return ResponseEntity containing the product and HTTP status.
     * @throws ProductNotFoundException if the product is not found.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) throws ProductNotFoundException {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK); // Returns the product with a 200 OK status
    }

    /**
     * POST endpoint to create a new product.
     * 
     * @param product Product object to be created.
     * @return ResponseEntity containing the created product and HTTP status.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED); // Returns 201 Created status indicating successful product creation
    }

    /**
     * PUT endpoint to update an existing product.
     * 
     * @param productId ID of the product to update.
     * @param productDTO DTO containing updated product data.
     * @return ResponseEntity containing the updated product and HTTP status.
     * @throws ProductNotFoundException if the product is not found.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductDTO productDTO) throws ProductNotFoundException {
        Product updatedProduct = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK); // Returns 200 OK status with the updated product
    }

    /**
     * DELETE endpoint to delete a product by its ID.
     * 
     * @param productId ID of the product to delete.
     * @return ResponseEntity with HTTP status indicating the result.
     * @throws ProductNotFoundException if the product is not found.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns 204 No Content status indicating successful deletion
    }
}
