package com.eco.system.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eco.system.beans.CartItemDto;
import com.eco.system.beans.CartItemUpdatebean;
import com.eco.system.entity.CartItem;
import com.eco.system.entity.Customer;
import com.eco.system.entity.Product;
import com.eco.system.exception.CartItemNotFoundException;
import com.eco.system.exception.ProductNotFoundException;
import com.eco.system.repository.CartItemRepository;
import com.eco.system.repository.ProductRepository;
import com.eco.system.serviceInterface.CartItemServiceIntf;

@Service // Indicates that this class is a Spring service component
public class CartItemService implements CartItemServiceIntf {

    @Autowired
    private CartItemRepository cartItemRepository;// Injecting the CartItemRepository to interact with the database
    @Autowired
    private ProductRepository productRepository;
    /**
     * Retrieves all CartItem entities from the repository.
     * 
     * @return List<CartItem> list of all cart items
     */
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll(); // Fetches all cart items from the database
    }

    /**
     * Retrieves a CartItem by its ID.
     * 
     * @param cartItemId the ID of the cart item to retrieve
     * @return CartItem the cart item with the specified ID
     * @throws CartItemNotFoundException if no cart item is found with the given ID
     */
    public CartItem getCartItemById(Integer cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + cartItemId)); 
                // Throws exception if cart item is not found
    }

    /**
     * Retrieves a list of CartItems for a specific customer.
     * 
     * @param customerId the ID of the customer whose cart items are to be retrieved
     * @return List<CartItem> list of cart items for the specified customer
     */
    public List<CartItem> getCartItemsByCustomerId(Integer customerId) {
        return cartItemRepository.findByCustomerCustomerId(customerId); // Fetches cart items based on customer ID
    }
    
    /**
     * Adds a new CartItem using CartItemDto.
     * 
     * @param cartItemDto the DTO containing information to create a new cart item
     * @return CartItem the newly created cart item
     */
    public CartItem addCartItem(CartItemDto cartItemDto) {
        // Creates new instances of Product and Customer based on the provided IDs
        Product product = new Product(cartItemDto.getProductId());
        Customer customer = new Customer(cartItemDto.getCustomerId());
        CartItem cart = new CartItem();
        cart.setProduct(product); // Sets the product for the cart item
        cart.setCustomer(customer); // Sets the customer for the cart item
        return cartItemRepository.save(cart); // Saves the new cart item in the database
    }

    /**
     * Updates an existing CartItem.
     * 
     * @param cartItemId the ID of the cart item to be updated
     * @param cartItemUpdatebean the bean containing the updated product information
     * @return CartItem the updated cart item
     * @throws ProductNotFoundException 
     * @throws CartItemNotFoundException if no cart item is found with the given ID
     */
    public CartItem updateCartItem(Integer cartItemId, CartItemUpdatebean cartItemUpdatebean) throws ProductNotFoundException,CartItemNotFoundException {
        // Validate the existence of CartItem
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + cartItemId));
        
        // Validate the existence of Product
        Product product = productRepository.findById(cartItemUpdatebean.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + cartItemUpdatebean.getProductId()));
        
        // Update the CartItem with the new Product
        cartItem.setProduct(product);
        
        return cartItemRepository.save(cartItem);
    }


    /**
     * Deletes a CartItem by its ID.
     * 
     * @param cartItemId the ID of the cart item to be deleted
     * @throws CartItemNotFoundException if no cart item is found with the given ID
     */
    public void deleteCartItem(Integer cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with id: " + cartItemId));
        cartItemRepository.delete(cartItem); // Deletes the cart item from the database
    }
}
