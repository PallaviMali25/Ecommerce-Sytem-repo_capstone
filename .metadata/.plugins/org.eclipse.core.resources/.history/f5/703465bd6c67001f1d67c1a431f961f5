package com.eco.system.serviceInterface;

import java.util.List;

import com.eco.system.beans.CartItemDto;
import com.eco.system.beans.CartItemUpdatebean;
import com.eco.system.entity.CartItem;

/**
 * Service interface for managing cart items.
 * Defines the operations that can be performed on cart items.
 */
public interface CartItemServiceIntf {

    /**
     * Retrieves all cart items.
     * 
     * @return List of all CartItem entities.
     */
    List<CartItem> getAllCartItems();

    /**
     * Retrieves a cart item by its ID.
     * 
     * @param cartItemId The ID of the cart item.
     * @return The CartItem entity with the specified ID.
     */
    CartItem getCartItemById(Integer cartItemId);

    /**
     * Retrieves all cart items associated with a specific customer.
     * 
     * @param customerId The ID of the customer.
     * @return List of CartItem entities associated with the specified customer.
     */
    List<CartItem> getCartItemsByCustomerId(Integer customerId);

    /**
     * Adds a new cart item based on the provided data transfer object.
     * 
     * @param cartItemDto Data transfer object containing the details of the cart item to be added.
     * @return The newly created CartItem entity.
     */
    CartItem addCartItem(CartItemDto cartItemDto);

    /**
     * Updates an existing cart item identified by its ID with the provided updated data.
     * 
     * @param cartItemId The ID of the cart item to be updated.
     * @param updatedCartItem Data transfer object containing the updated details of the cart item.
     * @return The updated CartItem entity.
     */
    CartItem updateCartItem(Integer cartItemId, CartItemUpdatebean updatedCartItem);

    /**
     * Deletes a cart item by its ID.
     * 
     * @param cartItemId The ID of the cart item to be deleted.
     */
    void deleteCartItem(Integer cartItemId);
}
