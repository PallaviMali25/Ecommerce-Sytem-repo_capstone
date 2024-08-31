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

import com.eco.system.beans.ApiResponse;
import com.eco.system.beans.CartItemDto;
import com.eco.system.beans.CartItemUpdatebean;
import com.eco.system.entity.CartItem;
import com.eco.system.serviceImplementation.CartItemService;

import jakarta.validation.Valid;

@RestController // Marks the class as a REST controller for handling HTTP requests
@RequestMapping("/cart-items") // Base URL for all endpoints in this controller
@CrossOrigin(origins = "http://localhost:8081") // Enables CORS for specified origin
@Validated // Enables validation for the request data in the controller
public class CartItemController {

    @Autowired // Injects the CartItemService bean for dependency injection
    private CartItemService cartItemService;

    /**
     * GET endpoint to retrieve all cart items.
     * 
     * @return List of all cart items.
     */
    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemService.getAllCartItems();
    }

    /**
     * GET endpoint to retrieve a cart item by its ID.
     * 
     * @param cartItemId ID of the cart item to retrieve.
     * @return ResponseEntity containing the cart item and HTTP status.
     */
    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Integer cartItemId) {
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        return new ResponseEntity<>(cartItem, HttpStatus.OK); // Returns the cart item with a 200 OK status
    }

    /**
     * GET endpoint to retrieve all cart items for a specific customer.
     * 
     * @param customerId ID of the customer.
     * @return List of cart items associated with the customer.
     */
    @GetMapping("/customer/{customerId}")
    public List<CartItem> getCartItemsByCustomerId(@PathVariable Integer customerId) {
        return cartItemService.getCartItemsByCustomerId(customerId);
    }

    /**
     * POST endpoint to add a new cart item.
     * 
     * @param cartItemDto Data Transfer Object (DTO) representing the cart item to be added.
     * @return ResponseEntity containing an ApiResponse and HTTP status.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addCartItem(@Valid @RequestBody CartItemDto cartItemDto) {
        CartItem addedCartItem = cartItemService.addCartItem(cartItemDto);
        if (addedCartItem != null) {
            // If the cart item is successfully added, return a success response
            ApiResponse response = new ApiResponse("Item added in cart, product id:" + addedCartItem.getProduct().getProductId(), 200);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED); // Returns 201 Created status
        } else {
            // If the cart item is not added, return an error response
            ApiResponse response = new ApiResponse("Item not added", 400);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST); // Returns 400 Bad Request status
        }
    }

//    /**
//     * Alternate POST endpoint to add a new cart item using a CartItem entity directly.
//     * Uncomment if needed as an alternative implementation.
//     * 
//     * @param cartItem CartItem entity to be added.
//     * @return ResponseEntity containing the added cart item and HTTP status.
//     */
//    @PostMapping
//    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
//        CartItem addedCartItem = cartItemService.addCartItem(cartItem);
//        return new ResponseEntity<>(addedCartItem, HttpStatus.OK);
//    }

    /**
     * PUT endpoint to update an existing cart item.
     * 
     * @param cartItemId ID of the cart item to update.
     * @param cartItemUpdatebean DTO containing the update details for the cart item.
     * @return ResponseEntity containing an ApiResponse and HTTP status.
     */
    @PutMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Integer cartItemId, @Valid @RequestBody CartItemUpdatebean cartItemUpdatebean) {
        CartItem updatedCartItem = cartItemService.updateCartItem(cartItemId, cartItemUpdatebean);
        if (updatedCartItem != null) {
            // If the cart item is successfully updated, return a success response
            ApiResponse response = new ApiResponse("Cart is Updated with product id: " + updatedCartItem.getProduct().getProductId(), 200);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED); // Returns 201 Created status
        } else {
            // If the cart item is not updated, return an error response
            ApiResponse response = new ApiResponse("Item not updated", 400);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST); // Returns 400 Bad Request status
        }
    }

    /**
     * DELETE endpoint to delete a cart item by its ID.
     * 
     * @param cartItemId ID of the cart item to delete.
     * @return ResponseEntity containing an ApiResponse and HTTP status.
     */
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Integer cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        ApiResponse response = new ApiResponse("Cart item deleted ", 200);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK); // Returns 200 OK status
    }
}
