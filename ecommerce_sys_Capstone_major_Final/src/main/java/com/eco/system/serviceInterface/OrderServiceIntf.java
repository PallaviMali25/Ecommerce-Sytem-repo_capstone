package com.eco.system.serviceInterface;

import com.eco.system.beans.PlaceOrderRequest;
import com.eco.system.entity.Order;

public interface OrderServiceIntf {

    /**
     * Retrieves an order by its ID.
     * 
     * @param orderId The ID of the order.
     * @return The Order entity with the specified ID.
     */
    Order getOrderById(Integer orderId);

    /**
     * Places a new order based on the provided request details.
     * 
     * @param placeOrderRequest The request object containing details for placing the order.
     * @return The newly created Order entity.
     */
    Order placeOrder(PlaceOrderRequest placeOrderRequest);
}
