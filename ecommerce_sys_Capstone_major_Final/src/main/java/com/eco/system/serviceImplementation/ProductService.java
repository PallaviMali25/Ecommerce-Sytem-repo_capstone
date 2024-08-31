package com.eco.system.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eco.system.beans.ProductDTO;
import com.eco.system.entity.Product;
import com.eco.system.exception.ProductNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.ProductRepository;
import com.eco.system.serviceInterface.ProductServiceIntf;

@Service // Indicates that this class is a Spring service component
public class ProductService implements ProductServiceIntf {

    @Autowired
    private ProductRepository productRepository; // Injects ProductRepository to handle product-related database operations

    @Autowired
    private CartItemRepository cartItemRepository; // Injects CartItemRepository to handle cart item-related database operations

    /**
     * Retrieves all products from the database.
     * 
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Fetches all products from the repository
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param productId the ID of the product to retrieve
     * @return the product with the specified ID
     * @throws ProductNotFoundException if no product is found with the given ID
     */
    public Product getProductById(Integer productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }

    /**
     * Creates a new product and saves it to the database.
     * 
     * @param product the product to create
     * @return the created product
     */
    public Product createProduct(Product product) {
        return productRepository.save(product); // Saves the product to the repository
    }

    /**
     * Updates an existing product with new details.
     * 
     * @param productId the ID of the product to update
     * @param productDTO contains the updated details of the product
     * @return the updated product
     * @throws ProductNotFoundException if no product is found with the given ID
     */
    public Product updateProduct(Integer productId, ProductDTO productDTO) throws ProductNotFoundException {
        // Retrieve the product to update
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        // Update the product details
        product.setProductName(productDTO.getProductName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());

        // Save the updated product
        return productRepository.save(product);
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param productId the ID of the product to delete
     * @throws ProductNotFoundException if no product is found with the given ID
     */
    public void deleteProduct(Integer productId) throws ProductNotFoundException {
        // Retrieve the product to delete
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        
        // Remove all cart items associated with the product to avoid foreign key constraint issues
        cartItemRepository.deleteByProductProductId(productId);

        // Delete the product from the repository
        productRepository.deleteById(productId);
    }
}
