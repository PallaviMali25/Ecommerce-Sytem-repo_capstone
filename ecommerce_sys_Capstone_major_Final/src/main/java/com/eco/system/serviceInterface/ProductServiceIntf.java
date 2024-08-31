package com.eco.system.serviceInterface;

import java.util.List;

import com.eco.system.beans.ProductDTO;
import com.eco.system.entity.Product;
import com.eco.system.exception.ProductNotFoundException;

public interface ProductServiceIntf {

    /**
     * Retrieves a list of all products.
     * 
     * @return A list of all Product entities.
     */
    List<Product> getAllProducts();

    /**
     * Retrieves a product by its ID.
     * 
     * @param productId The ID of the product.
     * @return The Product entity with the specified ID.
     * @throws ProductNotFoundException If no product is found with the given ID.
     */
    Product getProductById(Integer productId) throws ProductNotFoundException;

    /**
     * Creates a new product.
     * 
     * @param product The Product entity to be created.
     * @return The newly created Product entity.
     */
    Product createProduct(Product product);

    /**
     * Updates an existing product based on the provided details.
     * 
     * @param productId The ID of the product to be updated.
     * @param productDTO The DTO containing updated details for the product.
     * @return The updated Product entity.
     * @throws ProductNotFoundException If no product is found with the given ID.
     */
    Product updateProduct(Integer productId, ProductDTO productDTO) throws ProductNotFoundException;

    /**
     * Deletes a product by its ID.
     * 
     * @param productId The ID of the product to be deleted.
     * @throws ProductNotFoundException If no product is found with the given ID.
     */
    void deleteProduct(Integer productId) throws ProductNotFoundException;
}
